package edu.uncc.Users;

import edu.uncc.DB.UserDB;
import edu.uncc.DB.EventDB;
import edu.uncc.DB.UserEventDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

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
            request.setAttribute("headerError", "You must be signed in to complete that action");
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/savedEvents.jsp").forward(request, response);
        }
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

        if (!isEmptyOrNull(request, "action") && (session.getAttribute("User") != null || request.getParameter("action").equals("signIn")|| request.getParameter("action").equals("signUp"))) {
            //If the parameter action exists...
            //If the action is signout, remove the user from the current session
            if (request.getParameter("action").equals("signout")) {
                session.removeAttribute("User");
                session.removeAttribute("UserEvents");
                url = "/index.jsp";
            } else if (request.getParameter("action").equals("signIn")) {
                if (session.getAttribute("User") != null) {
                    url = "/savedEvents.jsp";
                } else if (!isEmptyOrNull(request, "username") && !isEmptyOrNull(request, "password")) {

                    try {
                        String username = ESAPI.validator().getValidInput("Username", request.getParameter("username"), "SafeString", 200, false);
                        String password = ESAPI.validator().getValidInput("Password", request.getParameter("password"), "HTTPParameterValue", 200, false);

                        User user;
                        if ((user = UserDB.validate(username, password)) != null) {
                            //Assign the user to the session
                            session.setAttribute("User", user);
                            //Create userProfile for the current user
                            UserProfile profile = new UserProfile(user.getUserID(), UserEventDB.getUserProfile(user.getUserID()), EventDB.getEvents(user.getUserID()));
                            //Add the userProfile to the session
                            session.setAttribute("userProfile", profile);
                            //Create a UserProfile instance and add the list of user events to the session
                            session.setAttribute("UserEvents", profile.getEvents());
                            session.setAttribute("CreatedEvents", profile.getCreatedEvents());

                            url = "/profile";

                        } else {
                            request.setAttribute("headerError", "The entered username or password is invalid. Please try again.");
                            url = "/login.jsp";
                        }
                    } catch (ValidationException | IntrusionException ex) {
                        Logger.getLogger(ProfileController.class.getName()).log(Level.WARNING, null, ex);
                        url = "/login.jsp";
                    }
                } else {
                    url = "/login.jsp";
                }
            } else if (request.getParameter("action").equals("signUp")) {
                if (session.getAttribute("User") != null) {
                    url = "/savedEvents.jsp";
                } else if (!isEmptyOrNull(request, "username") && !isEmptyOrNull(request, "password")) {
                    try {
                        //prep error message
                        boolean error = false;
                        boolean pError = false;
                        boolean eError = false;
                        String errorMessage = "Please fill in the missing values: ";
                        String pErrorMessage = "Password must be at least 8 characters long. ";
                        String eErrorMessage = "Email is already in use. ";

                        //validate form inputs
                        if (request.getParameter("firstname") == null || request.getParameter("firstname").trim().isEmpty()) {
                            error = true;
                            errorMessage += "first name, ";
                        }

                        if (request.getParameter("lastname") == null || request.getParameter("lastname").trim().isEmpty()) {
                            error = true;
                            errorMessage += "last name, ";
                        }

                        if (request.getParameter("email") == null || request.getParameter("email").trim().isEmpty()) {
                            error = true;
                            errorMessage += "email, ";
                        } else if (UserDB.checkEmail(ESAPI.validator().getValidInput("Email", request.getParameter("email"), "Email", 200, false))) {
                            eError = true;
                        }

                        if (request.getParameter("username") == null || request.getParameter("username").trim().isEmpty()) {
                            error = true;
                            errorMessage += "username, ";
                        }

                        if (request.getParameter("password") == null || request.getParameter("password").trim().isEmpty()) {
                            error = true;
                            errorMessage += "password, ";
                        } else if (request.getParameter("password").length() < 8) {
                            pError = true;
                        }

                        if (error || pError || eError) {//if error occured, go back to form
                            String finalmessage = "";
                            if (error)
                                finalmessage += errorMessage.substring(0, errorMessage.length() - 2);
                            if (pError)
                                finalmessage += pErrorMessage;
                            if (eError)
                                finalmessage += eErrorMessage;
                            request.setAttribute("headerError",  finalmessage);
                            url = "/signUp.jsp";
                        } else {//else convert form input into an user object
                            User user = new User();
                            user.setFirstName(ESAPI.validator().getValidInput("First Name", request.getParameter("firstname"), "SafeString", 200, false));
                            user.setLastName(ESAPI.validator().getValidInput("Last Name", request.getParameter("lastname"), "SafeString", 200, false));
                            user.setEmail(ESAPI.validator().getValidInput("Email", request.getParameter("email"), "Email", 200, false));
                            user.setUsername(ESAPI.validator().getValidInput("Username", request.getParameter("username"), "SafeString", 200, false));
                            String pass = ESAPI.validator().getValidInput("Password", request.getParameter("password"), "HTTPParameterValue", 200, false);
                            //add the user to the database
                            UserDB.addUser(user, pass);
                            // redirect to the login page
                            request.setAttribute("headermessage", "Success, please login to continue.");
                            url = "/login.jsp";
                        }
                    } catch (ValidationException | IntrusionException ex) {
                        Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
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
                                            UserEventDB.removeRSVP(e.getEvent().getEventID(), ((User) session.getAttribute("User")).getUserID());
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
        } else {
            request.setAttribute("headerError", "You must be signed in to complete that action");
            url = "/login.jsp";
        }

        getServletContext()
                .getRequestDispatcher(url).forward(request, response);
        if (request.getAttribute("headerError") != null) {
            request.removeAttribute("headerError");
        }

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
