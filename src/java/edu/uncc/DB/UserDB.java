package edu.uncc.DB;

import edu.uncc.Users.PassUtil;
import edu.uncc.Users.User;
import java.security.NoSuchAlgorithmException;
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
public class UserDB {

    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    public static void addUser(User user, String pass) {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO Users(FirstName, LastName, Username, Pass, Salt, Email)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
            
            String salt = PassUtil.getSalt();
            
            ps = con.prepareStatement(query);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, PassUtil.hash(pass + salt));
            ps.setString(5, salt);
            ps.setString(6, user.getEmail());
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }
    }
    
    public static List<User> getUsers() {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> users = null;

        String query = "SELECT * FROM Users";

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            users = new ArrayList<>();

            while (rs.next()) {
                users.add(new User(rs.getString("UserID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Email")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }

        return users;
    }

    public static User getUser(String userID) {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        String query = "SELECT * FROM Users "
                + "WHERE UserID = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, userID);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }

        return user;
    }
    
    public static Boolean checkEmail(String email) {
        Connection con = POOL.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Users "
                + "WHERE Email = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(con);
        }

        return false;
    }

    public static User getRandomUser() {
        ArrayList<User> users = (ArrayList<User>) getUsers();
        int random = (int) (Math.random() * users.size());
        return users.get(random);
    }

    public static User validate(String username, String pass) {
        Connection conn = POOL.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        String hashedPass;
        try {
            hashedPass = PassUtil.hash(pass + getSalt(username));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        try {

            String query = "SELECT * FROM Users "
                    + "WHERE Username = ? AND Pass = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, hashedPass);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(rs.getString("UserID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Email"));
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(conn);
        }
        return user;
    }

    private static String getSalt(String username) {
        Connection conn = POOL.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String salt = null;
        
         try {

            String query = "SELECT Salt FROM Users "
                    + "WHERE Username = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                salt = rs.getString("Salt");
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            POOL.freeConnection(conn);
        }
        return salt;
    }
}
