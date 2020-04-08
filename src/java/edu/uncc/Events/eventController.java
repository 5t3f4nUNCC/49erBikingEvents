package edu.uncc.Events;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.uncc.DB.EventDB;
import edu.uncc.DB.UserEventDB;
import edu.uncc.Users.User;
import edu.uncc.Users.UserProfile;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author dmcco
 */
public class eventController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet eventController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet eventController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        //if veiwing the events.jsp page, get all events and forward them on the request
        if (request.getParameter("id").equals("all")) {
            request.setAttribute("events", EventDB.getEvents());
            getServletContext().getRequestDispatcher("/events.jsp").forward(request, response);
        } else if (request.getParameter("id").isEmpty() || request.getParameter("id") == null) { //if request invalid, show events.jsp
            request.setAttribute("events", EventDB.getEvents());
            getServletContext().getRequestDispatcher("/events.jsp").forward(request, response);
        } else {//if veiwing a specific event....
            //Search the db for the event
            Event tmp = EventDB.getEvent(request.getParameter("id"));
            if (tmp != null) {//if the event was found add it to the request and show event.jsp
                request.setAttribute("event", tmp);

                getServletContext().getRequestDispatcher("/event.jsp").forward(request, response);
            } else { //else show all events
                request.setAttribute("events", EventDB.getEvents());
                getServletContext().getRequestDispatcher("/events.jsp").forward(request, response);
            }
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

        //check if task exists
        if (request.getParameter("task") != null && request.getSession().getAttribute("User") != null) {
            switch (request.getParameter("task")) {
                case "edit":
                    try {
                        Event event = EventDB.getEvent(ESAPI.validator().getValidInput("Event ID", request.getParameter("id"), "SafeString", 200, false));
                        if (event.getUserID().equals(((User) request.getSession().getAttribute("User")).getUserID())) {//else convert form input into an event object
                            request.setAttribute("event", event);
                            //go to edit event page
                            getServletContext().getRequestDispatcher("/editEvent.jsp").forward(request, response);
                        } else {
                            request.setAttribute("headerError", "You do not have permission to edit this event.");
                            getServletContext().getRequestDispatcher("/savedEvents.jsp").include(request, response);
                        }
                    } catch (ValidationException | IntrusionException ex) {
                        Logger.getLogger(eventController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "update":
                    Event event = null;
                    try {
                        //prep error message
                        boolean error = false;
                        String errorMessage = "Please fill in the missing values: ";

                        //validate form inputs
                        if (request.getParameter("title") == null || request.getParameter("title").trim().isEmpty()) {
                            error = true;
                            errorMessage += "title, ";
                        }

                        if (request.getParameter("type") == null || request.getParameter("type").trim().isEmpty()) {
                            error = true;
                            errorMessage += "type, ";
                        }

                        if (request.getParameter("description") == null || request.getParameter("description").trim().isEmpty()) {
                            error = true;
                            errorMessage += "description, ";
                        }

                        if (request.getParameter("address") == null || request.getParameter("address").trim().isEmpty()) {
                            error = true;
                            errorMessage += "address, ";
                        }

                        if (request.getParameter("date") == null || request.getParameter("date").trim().isEmpty()) {
                            error = true;
                            errorMessage += "date, ";
                        }

                        //city, zip, state, and country have the option to be empty if they are all empty. All must be filled out if one is
                        if (!(request.getParameter("city") == null || request.getParameter("city").trim().isEmpty())) {
                            if (request.getParameter("city") == null || request.getParameter("city").trim().isEmpty()) {
                                error = true;
                                errorMessage += "city, ";
                            }
                            if (request.getParameter("zip") == null || request.getParameter("zip").trim().isEmpty()) {
                                error = true;
                                errorMessage += "zip, ";
                            }
                            if (request.getParameter("state") == null || request.getParameter("state").trim().isEmpty()) {
                                error = true;
                                errorMessage += "state, ";
                            }
                            if (request.getParameter("country") == null || request.getParameter("country").trim().isEmpty()) {
                                error = true;
                                errorMessage += "country, ";
                            }
                        }

                        event = EventDB.getEvent(ESAPI.validator().getValidInput("Event ID", request.getParameter("id"), "SafeString", 200, false));

                        if (error) {//if error occured, go back to form
                            request.setAttribute("headerError", errorMessage.substring(0, errorMessage.length() - 2));
                            getServletContext().getRequestDispatcher("/editEvent.jsp").include(request, response);
                        } else if (event.getUserID().equals(((User) request.getSession().getAttribute("User")).getUserID())) {//else convert form input into an event object
                            Event updatedEvent = new Event();
                            updatedEvent.setEventID(event.getEventID());
                            updatedEvent.setTitle(ESAPI.validator().getValidInput("Event Title", request.getParameter("title"), "SafeString", 200, false));
                            updatedEvent.setMessage(ESAPI.validator().getValidInput("Event Message", request.getParameter("message"), "SafeString", 200, false));
                            updatedEvent.setType(ESAPI.validator().getValidInput("Event Type", request.getParameter("type"), "SafeString", 200, false));
                            updatedEvent.setAddress(ESAPI.validator().getValidInput("Event Address", request.getParameter("address"), "SafeString", 200, false));
                            if (!request.getParameter("city").isEmpty()) {
                                updatedEvent.setCity(ESAPI.validator().getValidInput("City", request.getParameter("city"), "SafeString", 200, false));
                                updatedEvent.setState(ESAPI.validator().getValidInput("State", request.getParameter("state"), "SafeString", 200, false));
                                updatedEvent.setCountry(ESAPI.validator().getValidInput("Country", request.getParameter("country"), "SafeString", 200, false));
                                updatedEvent.setZip(ESAPI.validator().getValidInput("Zip", request.getParameter("zip"), "SafeString", 200, false));
                            }

                            updatedEvent.setDescription(ESAPI.validator().getValidInput("Event Description", request.getParameter("description"), "SafeString", 200, false));
                            updatedEvent.setDate(LocalDateTime.parse(ESAPI.validator().getValidInput("Event Date", request.getParameter("date"), "HTTPHeaderValue", 19, false), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
                            //add the event to the database
                            EventDB.updateEvent(updatedEvent);
                            //Update the user created events list
                            UserProfile profile = (UserProfile) request.getSession().getAttribute("userProfile");
                            profile.setCreatedEvents(EventDB.getEvents(profile.getUserID()));
                            request.getSession().setAttribute("CreatedEvents", profile.getCreatedEvents());
                            //go to users profile page
                            getServletContext().getRequestDispatcher("/savedEvents.jsp").forward(request, response);
                        } else {
                            request.setAttribute("headerError", "You do not have permission to edit this event.");
                            getServletContext().getRequestDispatcher("/savedEvents.jsp").forward(request, response);
                        }
                    } catch (ValidationException | IntrusionException ex) {
                        request.setAttribute("headerError", "Validation Error. Make sure to not use special characters except in the email field.");
                        request.setAttribute("event", event);
                        getServletContext().getRequestDispatcher("/editEvent.jsp").forward(request, response);
                        Logger.getLogger(eventController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "delete":
                    try {
                        event = EventDB.getEvent(ESAPI.validator().getValidInput("Event ID", request.getParameter("id"), "SafeString", 200, false));
                        if (event.getUserID().equals(((User) request.getSession().getAttribute("User")).getUserID())) {//if the event belongs to the current user
                            EventDB.removeEvent(event.getEventID(), ((User) request.getSession().getAttribute("User")).getUserID());
                            //Update the user created events list
                            UserProfile profile = (UserProfile) request.getSession().getAttribute("userProfile");
                            profile.setEvents(UserEventDB.getUserProfile(((User) request.getSession().getAttribute("User")).getUserID()));
                            profile.setCreatedEvents(EventDB.getEvents(profile.getUserID()));
                            request.getSession().setAttribute("CreatedEvents", profile.getCreatedEvents());
                            request.getSession().setAttribute("UserEvents", profile.getEvents());
                            //Add message
                            request.setAttribute("headermessage", "Event successfully deleted.");
                            //go to users profile page
                            getServletContext().getRequestDispatcher("/savedEvents.jsp").forward(request, response);
                        } else {
                            request.setAttribute("headerError", "You do not have permission to edit this event.");
                            getServletContext().getRequestDispatcher("/savedEvents.jsp").include(request, response);
                        }
                    } catch (ValidationException | IntrusionException ex) {
                        Logger.getLogger(eventController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "create":
                    try {
                        //prep error message
                        boolean error = false;
                        String errorMessage = "Please fill in the missing values: ";

                        //validate form inputs
                        if (request.getParameter("title") == null || request.getParameter("title").trim().isEmpty()) {
                            error = true;
                            errorMessage += "title, ";
                        }

                        if (request.getParameter("type") == null || request.getParameter("type").trim().isEmpty()) {
                            error = true;
                            errorMessage += "type, ";
                        }

                        if (request.getParameter("description") == null || request.getParameter("description").trim().isEmpty()) {
                            error = true;
                            errorMessage += "description, ";
                        }

                        if (request.getParameter("address") == null || request.getParameter("address").trim().isEmpty()) {
                            error = true;
                            errorMessage += "address, ";
                        }

                        if (request.getParameter("date") == null || request.getParameter("date").trim().isEmpty()) {
                            error = true;
                            errorMessage += "date, ";
                        }

                        //city, zip, state, and country have the option to be empty if they are all empty. All must be filled out if one is
                        if (!(request.getParameter("city") == null || request.getParameter("city").trim().isEmpty())) {
                            if (request.getParameter("city") == null || request.getParameter("city").trim().isEmpty()) {
                                error = true;
                                errorMessage += "city, ";
                            }
                            if (request.getParameter("zip") == null || request.getParameter("zip").trim().isEmpty()) {
                                error = true;
                                errorMessage += "zip, ";
                            }
                            if (request.getParameter("state") == null || request.getParameter("state").trim().isEmpty()) {
                                error = true;
                                errorMessage += "state, ";
                            }
                            if (request.getParameter("country") == null || request.getParameter("country").trim().isEmpty()) {
                                error = true;
                                errorMessage += "country, ";
                            }
                        }

                        if (error) {//if error occured, go back to form
                            request.setAttribute("headerError", errorMessage.substring(0, errorMessage.length() - 2));
                            getServletContext().getRequestDispatcher("/newEvent.jsp").include(request, response);
                        } else {//else convert form input into an event object
                            Event newEvent = new Event();
                            newEvent.setTitle(ESAPI.validator().getValidInput("Event Title", request.getParameter("title"), "SafeString", 200, false));
                            newEvent.setType(ESAPI.validator().getValidInput("Event Type", request.getParameter("type"), "SafeString", 200, false));
                            newEvent.setAddress(ESAPI.validator().getValidInput("Event Address", request.getParameter("address"), "SafeString", 200, false));
                            if (!request.getParameter("city").isEmpty()) {
                                newEvent.setCity(ESAPI.validator().getValidInput("City", request.getParameter("city"), "SafeString", 200, false));
                                newEvent.setState(ESAPI.validator().getValidInput("State", request.getParameter("state"), "SafeString", 200, false));
                                newEvent.setCountry(ESAPI.validator().getValidInput("Country", request.getParameter("country"), "SafeString", 200, false));
                                newEvent.setZip(ESAPI.validator().getValidInput("Zip", request.getParameter("zip"), "SafeString", 200, false));
                            }

                            newEvent.setDescription(ESAPI.validator().getValidInput("Event Description", request.getParameter("description"), "SafeString", 200, false));
                            newEvent.setDate(LocalDateTime.parse(ESAPI.validator().getValidInput("Event Date", request.getParameter("date"), "HTTPHeaderValue", 19, false), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
                            //add the event to the database
                            EventDB.addEvent(newEvent, ((User) request.getSession().getAttribute("User")).getUserID());
                            //Update the user created events list
                            UserProfile profile = (UserProfile) request.getSession().getAttribute("userProfile");
                            profile.setCreatedEvents(EventDB.getEvents(profile.getUserID()));
                            request.getSession().setAttribute("CreatedEvents", profile.getCreatedEvents());
                            //add the event object to the request
                            request.setAttribute("newEvent", newEvent);
                            //go to users profile page
                            getServletContext().getRequestDispatcher("/savedEvents.jsp").forward(request, response);
                        }
                    } catch (ValidationException | IntrusionException ex) {
                        Logger.getLogger(eventController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    break;
            }
        } else {
            request.setAttribute("headerError", "You must be signed in to complete that action");
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
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
