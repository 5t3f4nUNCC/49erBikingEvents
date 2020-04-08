package edu.uncc.Users;

import edu.uncc.Events.Event;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stefan Dybka
 */
public class UserProfile implements Serializable{
    private String UserID;
    List<UserEvent> userEvents;
    List<Event> createdEvents;
    
    public UserProfile () {}

    public UserProfile(String UserID)
    {
        this.UserID = UserID;
        this.userEvents = new ArrayList<>();
        this.createdEvents = new ArrayList<>();
    }
    
    public UserProfile(String UserID, List<UserEvent> userEvents, List<Event> createdEvents) {
        this.UserID = UserID;
        this.userEvents = userEvents;
        this.createdEvents = createdEvents;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public List<UserEvent> getEvents() {
        return userEvents;
    }

    public void setEvents(List<UserEvent> userEvents) {
        this.userEvents = userEvents;
    }

    public List<Event> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(List<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public void addEvent(Event e, String rsvp) {
        for (UserEvent tmp : userEvents) {
            if (tmp.getEvent() == e)
            {
                updateEvent(new UserEvent(e, rsvp));
                return;
            }
        }
        
        userEvents.add(new UserEvent(e, rsvp));
    }
    
    public void removeEvent(Event e) {
        for (UserEvent tmp : userEvents) {
            if (tmp.getEvent() == e)
            {
                userEvents.remove(tmp);
                return;
            }
        }
    }
    
    public void updateEvent(UserEvent ue) {
        userEvents.stream().filter((tmp) -> (tmp.getEvent().getEventID().equals(ue.getEvent().getEventID()))).forEachOrdered((tmp) -> {
            tmp.setRsvp(ue.getRsvp());
        });
    }
    
    public void emptyProfile() {
        this.UserID = "";
        this.userEvents = new ArrayList<>();
        this.createdEvents = new ArrayList<>();
    }
}
