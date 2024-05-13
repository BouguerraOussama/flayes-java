package services.events;

import models.events.Event;
import services.users.IService;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventService  {

    private Connection connection;

    public EventService(){
        connection = MyDataBase.getInstance().getConnection();
    }

    public void create(Event event) throws SQLException {
        String sql = "INSERT INTO event (name, date, description, location, image, idcat, qrcode) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, event.getName());
        ps.setString(2, event.getDate());
        ps.setString(3, event.getDescription());
        ps.setString(4, event.getLocation());
        ps.setString(5, event.getImage());
        ps.setInt(6, event.getIdcat());
        ps.setString(7, event.getQrcode()); // Set qrcode
        ps.executeUpdate();
        ps.close();
    }


    public void update(Event event) throws SQLException {
        String sql = "UPDATE event SET name = ?, date = ?, description = ?, location = ?, image = ?, idcat = ?, qrcode = ? WHERE Idevent = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, event.getName());
            ps.setString(2, event.getDate());
            ps.setString(3, event.getDescription());
            ps.setString(4, event.getLocation());
            ps.setString(5, event.getImage());
            ps.setInt(6, event.getIdcat());
            ps.setString(7, event.getQrcode()); // Set qrcode
            ps.setInt(8, event.getIdevent()); // Set Idevent
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception or handle it appropriately
            throw ex; // Re-throw the exception to propagate it to the caller
        }
    }
    public List<Event> getEventsByTicketType() throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.* FROM event e INNER JOIN category c ON e.idcat = c.Idcat WHERE c.type = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "TICKET");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setIdevent(rs.getInt("Idevent"));
                    event.setName(rs.getString("name"));
                    // Populate other properties as needed
                    events.add(event);
                }
            }
        }
        return events;
    }







    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM event WHERE Idevent = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public Event search(int id) throws SQLException {
        // Initialize the Event object outside the try block
        Event event = null;

        try {
            String sql = "SELECT * FROM event WHERE Idevent = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            // Check if the ResultSet has at least one row
            if (resultSet.next()) {
                // Create a new Event object and populate its attributes
                event = new Event();
                event.setIdevent(resultSet.getInt("Idevent"));
                event.setName(resultSet.getString("name"));
                event.setDate(resultSet.getString("date"));
                event.setDescription(resultSet.getString("description"));
                event.setLocation(resultSet.getString("location"));
                event.setImage(resultSet.getString("image"));
                event.setIdcat(resultSet.getInt("idcat"));
            }

            // Close the ResultSet and PreparedStatement in a finally block to ensure proper resource management
        } catch (SQLException ex) {
            // Handle any SQL exceptions
            ex.printStackTrace(); // Log the exception or handle it appropriately
            throw ex; // Re-throw the exception to propagate it to the caller
        }
        return event;
    }



    public List<Event> read() throws SQLException {
        String sql = "SELECT * FROM event";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Event> events = new ArrayList<>();
        while (rs.next()) {
            Event event = new Event();
            event.setIdevent(rs.getInt("Idevent"));
            event.setName(rs.getString("name"));
            event.setDate(rs.getString("date"));
            event.setDescription(rs.getString("description"));
            event.setLocation(rs.getString("location"));
            event.setImage(rs.getString("image"));
            event.setIdcat(rs.getInt("idcat")); // Set idcat
            event.setQrcode(rs.getString("qrcode")); // Set qrcode
            events.add(event);
        }
        rs.close();
        statement.close();
        return events;
    }

}
