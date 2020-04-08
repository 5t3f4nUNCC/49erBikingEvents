/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uncc.DB;

import edu.uncc.Users.UserEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefan Dybka
 */
public class UserEventDB {
    private static final ConnectionPool POOL = ConnectionPool.getInstance();
    
    /**
     * Gets all the events and rsvp values associated with the given user
     * @param userID userID
     * @return list of userEvents associated with the given user
     */
    public static List<UserEvent> getUserProfile(String userID) {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserEvent> userEventList = new ArrayList<>();

        String query = "SELECT * FROM UserEvents "
                + "WHERE UserID = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                UserEvent ue = new UserEvent();
                ue.setEvent(EventDB.getEvent(rs.getString("EventID")));
                ue.setRsvp(rs.getString("RSVP"));
                userEventList.add(ue);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }

        return userEventList;
    }
    
    /**
     * Associates an event with a user based on eventID and userID and updates the RSVP value in the DB
     * @param eventID eventID to associate with the user
     * @param userID userID for the user to associate with
     * @param rsvp rsvp value to update in the DB
     */
    public static void addRSVP(String eventID, String userID, String rsvp) {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO UserEvents(EventID, UserID, RSVP) "
                + "VALUES (?, ?, ?)";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, eventID);
            ps.setString(2, userID);
            ps.setString(3, rsvp);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }
    }
    
    /**
     * Updates the rsvp field for a user with userID for an event
     * @param eventID event ID to locate the event to edit
     * @param userID user ID who is rsvp'ing
     * @param rsvp the rsvp value to update
     */
    public static void updateRSVP(String eventID, String userID, String rsvp) {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE UserEvents "
                + "SET RSVP = ? "
                + "WHERE UserID = ? AND EventID = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, rsvp);
            ps.setString(2, userID);
            ps.setString(3, eventID);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }
    }
    
    public static void removeRSVP(String eventID, String userID) {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;

        String query = "Delete FROM UserEvents "
                + "WHERE UserID = ? AND EventID = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, userID);
            ps.setString(2, eventID);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }
    }
}
