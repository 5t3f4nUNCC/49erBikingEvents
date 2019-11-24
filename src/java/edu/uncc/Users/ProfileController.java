package edu.uncc.Users;

import edu.uncc.DB.UserDB;
import edu.uncc.DB.EventDB;
import edu.uncc.DB.UserEventDB;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Stefan Dybka
 */
public class ProfileController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("User") == null) {
            //Get the session
            HttpSession session = request.getSession();
            //Get Random user from the database
            User user = UserDB.getRandomUser();
            //Assign the user to the session
            session.setAttribute("User", user);
            //Create userProfile for the current user
            UserProfile profile = new UserProfile(user.getUserID(), UserEventDB.getUserProfile(user.getUserID()), EventDB.getEvents(user.getUserID()));
            //Add the userProfile to the session
            session.setAttribute("userProfile", profile);
            //Create a UserProfile instance and add the list of user events to the session
            session.setAttribute("UserEvents", profile.getEvents());
            session.setAttribute("CreatedEvents", profile.getCreatedEvents());
        }

        getServletContext().getRequestDispatcher("/savedEvents.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Get the session
        HttpSession session = request.getSession();

        String url = "/index.jsp";
        //If the Usser attribute does not exist, add a random user
        if (session.getAttribute("User") == null) {
            //Get Random user from the database
            User user = UserDB.getRandomUser();
            //Assign the user to the session
            session.setAttribute("User", user);
            //Create userProfile for the current user
            UserProfile profile = new UserProfile(user.getUserID(), UserEventDB.getUserProfile(user.getUserID()), EventDB.getEvents(user.getUserID()));
            //Add the userProfile to the session
            session.setAttribute("userProfile", profile);
            //Create a UserProfile instance and add the list of user events to the session
            session.setAttribute("UserEvents", profile.getEvents());
            session.setAttribute("CreatedEvents", profile.getCreatedEvents());
        }

        if (!isEmptyOrNull(request, "action")) {
            //If the parameter action exists...
            //If the action is signout, remove the user from the current session
            if (request.getParameter("action").equals("signout")) {
                session.removeAttribute("User");
                session.removeAttribute("UserEvents");
                url = "/index.jsp";
            } else if (!isEmptyOrNull(request, "viewEvents")) {
                //else if the parameter viewEvents exists...
                if (!isEmptyOrNull(request, "id")) {
                    String[] viewEvents = request.getParameterValues("viewEvents");
                    for (String eventID : viewEvents) {
                        if (eventID.equals(request.getParameter("id"))) {
                            //if the action is save, ...
                            switch (request.getParameter("action")) {
                                case "save":
                                    if (!isEmptyOrNull(request, "rsvp")) {
                                        switch (request.getParameter("rsvp")) {
                                            case "Yes":
                                            case "No":
                                            case "Maybe":
                                                boolean found = false;
                                                for (UserEvent e : (List<UserEvent>) session.getAttribute("UserEvents")) {
                                                    if (e.getEvent().getEventID().equals(request.getParameter("id"))) {
                                                        UserEvent tmp = new UserEvent(e.getEvent(), request.getParameter("rsvp"));
                                                        ((UserProfile) session.getAttribute("userProfile")).updateEvent(tmp);
                                                        UserEventDB.updateRSVP(tmp.getEvent().getEventID(), ((User) session.getAttribute("User")).getUserID(), tmp.getRsvp());
                                                        found = true;
                                                        break;
                                                    }
                                                }
                                                if (!found) {
                                                    UserEventDB.addRSVP(request.getParameter("id"), ((User) session.getAttribute("User")).getUserID(), request.getParameter("rsvp"));
                                                    ((UserProfile) session.getAttribute("userProfile")).addEvent(EventDB.getEvent(request.getParameter("id")), request.getParameter("rsvp"));
                                                }
                                                url = "/savedEvents.jsp";
                                                break;
                                            default:
                                                url = "/savedEvents.jsp";
                                        }
                                    }
                                    break;
                                case "updateProfile":
                                    //if the action is updateProfile, search for the event id in the user events and update the event
                                    for (UserEvent e : (List<UserEvent>) session.getAttribute("UserEvents")) {
                                        if (e.getEvent().getEventID().equals(request.getParameter("id"))) {
                                            request.setAttribute("event", e.getEvent());
                                            url = "/event.jsp";
                                            break;
                                        }
                                    }
                                    break;
                                case "delete":
                                    for (UserEvent e : (List<UserEvent>) session.getAttribute("UserEvents")) {
                                        if (e.getEvent().getEventID().equals(request.getParameter("id"))) {
                                            UserEventDB.removeRSVP(e.getEvent().getEventID(), ((User)session.getAttribute("User")).getUserID());
                                            ((List<UserEvent>) session.getAttribute("UserEvents")).remove(e);
                                            break;
                                        }
                                    }
                                    url = "/savedEvents.jsp";
                                    break;
                                default:
                                    url = "/savedEvents.jsp";
                                    break;
                            }
                        }
                    }
                }
            } else {
                url = "/savedEvents.jsp";
            }
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    private boolean isEmptyOrNull(HttpServletRequest request, String param) {
        return request.getParameter(param) == null || request.getParameter(param).trim().equals("");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
