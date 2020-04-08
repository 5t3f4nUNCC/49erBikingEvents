package edu.uncc.DB;

import edu.uncc.Events.Event;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefan Dybka
 */
public class EventDB {

    private static final ConnectionPool POOL = ConnectionPool.getInstance();
    private static final DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Gets all the events in the db
     *
     * @return Array of events
     */
    public static List<Event> getEvents() {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Event> events = null;

        String query = "SELECT * FROM EventsList";

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            events = new ArrayList<>();

            while (rs.next()) {
                events.add(new Event(Integer.toString(rs.getInt("EventID")), rs.getString("Title"), rs.getString("RideType"), rs.getString("Message"), rs.getString("RideDescription"), LocalDateTime.parse(rs.getString("RideDate"), date), rs.getString("Address"), rs.getString("City"), rs.getString("State"), rs.getString("Country"), rs.getString("Zip"), rs.getInt("CountGoing"), rs.getString("UserID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }

        return events;
    }

    /**
     * Gets all the events a user has created
     *
     * @param userID
     * @return list of events the user created
     */
    public static List<Event> getEvents(String userID) {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Event> events = null;

        String query = "SELECT * FROM EventsList "
                + "WHERE UserID = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            events = new ArrayList<>();

            while (rs.next()) {
                events.add(new Event(Integer.toString(rs.getInt("EventID")), rs.getString("Title"), rs.getString("RideType"), rs.getString("Message"), rs.getString("RideDescription"), LocalDateTime.parse(rs.getString("RideDate"), date), rs.getString("Address"), rs.getString("City"), rs.getString("State"), rs.getString("Country"), rs.getString("Zip"), rs.getInt("CountGoing"), rs.getString("UserID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }

        return events;
    }

    /**
     * Get an event based on an eventID
     *
     * @param eventID the event to get
     * @return the event if found
     */
    public static Event getEvent(String eventID) {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Event event = null;

        String query = "SELECT * FROM EventsList "
                + "WHERE EventID = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, eventID);
            rs = ps.executeQuery();

            if (rs.next()) {
                event = new Event(Integer.toString(rs.getInt("EventID")), rs.getString("Title"), rs.getString("RideType"), rs.getString("Message"), rs.getString("RideDescription"), LocalDateTime.parse(rs.getString("RideDate"), date), rs.getString("Address"), rs.getString("City"), rs.getString("State"), rs.getString("Country"), rs.getString("Zip"), rs.getInt("CountGoing"), rs.getString("UserID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }

        return event;
    }

    /**
     * Adds a new event to the db
     *
     * @param event to add to the db
     * @param userID
     */
    public static void addEvent(Event event, String userID) {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO EventsList(Title, RideType, Message, RideDescription, RideDate, Address, City, State, Country, Zip, CountGoing, UserID)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getType());
            ps.setString(3, event.getMessage());
            ps.setString(4, event.getDescription());
            ps.setString(5, event.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            ps.setString(6, event.getAddress());
            ps.setString(7, event.getCity());
            ps.setString(8, event.getState());
            ps.setString(9, event.getCountry());
            ps.setString(10, event.getZip());
            ps.setString(11, Integer.toString(event.getCountGoing()));
            ps.setString(12, userID);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }
    }

    public static void removeEvent(String eventID, String userID) {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;

        try {
            // Delete the event from the event list
            String query = "Delete FROM EventsList "
                    + "WHERE EventID = ? AND UserID = ?";
            
            ps = con.prepareStatement(query);
            ps.setString(1, eventID);
            ps.setString(2, userID);
            ps.executeUpdate();

            // remove event from all pages that rsvp'd to it
            query = "Delete FROM UserEvents "
                    + "WHERE EventID = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, eventID);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }
    }

    public static String getLastID() {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;
        ResultSet rs;
        String EventID = null;

        String query = "SELECT COUNT(EventID) FROM EventsList";

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                EventID = rs.getString("COUNT(EventID)");
            }

        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }

        return EventID;
    }

    public static void updateEvent(Event event) {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE EventsList SET "
                + "Title = ?, RideType = ?, Message = ?, RideDescription = ?, RideDate = ?, Address = ?, City = ?, State = ?, Country = ?, Zip = ?"
                + "WHERE EventID = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getType());
            ps.setString(3, event.getMessage());
            ps.setString(4, event.getDescription());
            ps.setString(5, event.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            ps.setString(6, event.getAddress());
            ps.setString(7, event.getCity());
            ps.setString(8, event.getState());
            ps.setString(9, event.getCountry());
            ps.setString(10, event.getZip());
            ps.setString(11, event.getEventID());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }
    }

}
