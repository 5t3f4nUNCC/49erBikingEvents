package edu.uncc.Users;

import edu.uncc.Events.Event;
import java.io.Serializable;

/**
 *
 * @author Stefan Dybka
 */
public class UserEvent implements Serializable{
    private Event event;
    private String rsvp;
    
    public UserEvent() {}

    public UserEvent(Event event, String rsvp) {
        this.event = event;
        this.rsvp = rsvp;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getRsvp() {
        return rsvp;
    }

    public void setRsvp(String rsvp) {
        this.rsvp = rsvp;
    }
    
    
}
