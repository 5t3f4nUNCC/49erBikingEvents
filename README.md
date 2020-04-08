# 49erBikingEvents

Welcome to 49er Biking Events! This is a project I developed over the Fall 2019 semester for Network-Based Application Development.
Developed using Netbeans and Tomcat Apache with Java as the back-end language. The project details are below. Install instructions can be found at install.md and further details on the layout and use of each of the web pages can be found at manual.mb

1.	Project overview:

49er Mountain Biking Events is a webpage to help organize people in the local area of the University of North Carolina at Charlotte who like biking. Events range from road trips around campus to downhill travel adventures. Users can create events which will be posted on the website for others to rsvp to. Targeted at UNCC students, providing them a way to ask others on group bike rides and see others that are interested, but could also be expanded to residents surrounding the UNCC area.


2.  Page Design:

Header:
-	Presents a common header for all pages.
-	General users
-	No data fields
-	No validation
-	Shows login and signup links when a user is not logged in and Create a new event, my events, and sign out when signed in. 49er Mountain Biking is always displayed which links to the home page

Footer:
-	Presents a common footer for all pages
-	General users
-	No data fields
-	No validation
-	Displays the 49er Mountain Biking copyright and links to Contact us and About us

Navigation:
-	Presents a common navigation bar for all pages
-	General users
-	Welcomes the user using their first name
-	No validation
-	Link to the events page is always shown

About us:
-	Gives a description of the goal of the website, types of mountain biking, and types of mountain bikes
-	New users
-	No data fields present
-	Header, navigation, and footer

Contact us:
-	Presents a dummy form for users to “contact” me
-	General users
-	First and last name, email, question. On a normal contact me form these would be used to structure an automated email to be sent to a contact email for later reading
-	Nothing is done currently with their questions so there is no validation
-	Header, nav, footer

Events:
-	Shows a list of road, trail, and downhill events to users
-	General users
-	Pulls up the title, description, count going, and months/weeks/days till the event
-	Changes the months, week, or days left to show the correct terminology based on how much time is left before the event
-	Header, nav, footer, and links to each event page for more details

Event:
-	Presents more details for a specific event
-	General users
-	Shows the date and time of the vent, the location/address, how many are going, a description, and a message if the event has one. Also has a form for users to rsvp “Yes”, “Maybe”, or “No” to the event
-	Set values for yes, maybe, no. Verifies which event is being rsvp’d to
-	Header, nav, footer, rsvp’ing brings you to “my events” or the log in page if a user is not logged in

Home:
-	Serves as a home page for the website to welcome users
-	General users
-	No data fields
-	No validation
-	Header, nav, and footer

Log in:
-	Serves as a means for users with accounts to log in
-	Existing users
-	Username and password
-	Hashes and salts password. Checks username and password as safe text and makes sure they aren’t empty. If the password or username is incorrect, an alert is shown
-	Header, nav, footer, and signing in brings you to my events page

Sign up:
-	Serves as a means for new users to create an account with us
-	New users
-	First and last name, email, username, password
-	Hashes and salts password. Checks all data fields as safe text and makes sure they aren’t empty. Checks that email hasn’t previously been used for another existing account.
-	Header, nav, footer

Saved Events:
-	Shows logged in users the events they’ve saved (rsvp’d too) and events they have created
-	Logged in users
-	Edit and remove RSVPs, edit and remove created events
-	Makes sure the user owns the event before they can edit or delete it
-	Header, nav, footer. Edit an rsvp brings the user back to the event’s page. Editing an owned event links to the editEvent page.

Edit Event:
-	Serves as a way to edit an event’s details after its creation
-	Logged in users
-	Title, ride type, message, description, address, city, country, state, zip, date and time of event
-	All fields are checked to be safe text and not be empty. City, state, country, and zip CAN be empty if the address is used as a name of a location instead of a street address. Message is also optional. Checks that the user owns the event they are trying to edit
-	Header, nav, footer. Update event links back to the user’s profile

Create Event:
-	Allows logged in users to create new events to be posted on the website
-	Logged in users
-	Title, ride type, message, description, address, city, country, state, zip, date and time of event
-	All fields are checked to be safe text and not be empty. City, state, country, and zip CAN be empty if the address is used as a name of a location instead of a street address. Message is also optional. Checks that the user owns the event they are trying to edit
-	Header, nav, footer. Create event links to the user’s profile
