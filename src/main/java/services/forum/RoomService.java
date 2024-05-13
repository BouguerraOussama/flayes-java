package services.forum;

import models.forum.Room;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomService implements IService<Room>{

    private Connection connection;
    private final PostService ps = new PostService();
    public RoomService(){ connection = MyDataBase.getInstance().getConnection(); }
    @Override
    public void create(Room room) throws SQLException {
        String sql = "INSERT INTO Room (category, sub_category, description) " +
                "VALUES ('" + room.getCategory() + "', '" + room.getSub_category() + "', '" +
                room.getDescription() + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void update(Room room) throws SQLException {
        String sql = "UPDATE Room SET category = ?, sub_category = ?, description = ? where room_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, room.getCategory());
        ps.setString(2, room.getSub_category());
        ps.setString(3,room.getDescription());
        ps.setInt(4, room.getRoom_id());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Room WHERE room_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
//            if (ps.readByRoomId(id) != null){
//                ps.deleteByRoomId(id);
//            }
            // Execute the SQL statement
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    @Override
    public List<Room> read() throws SQLException {
        String sql = "SELECT * FROM Room";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Room> rooms = new ArrayList<>();
        while (rs.next()){
            Room r = new Room();
            r.setRoom_id(rs.getInt("room_id"));
            r.setCategory(rs.getString("category"));
            r.setSub_category(rs.getString("sub_category"));
            r.setDescription(rs.getString("description"));

            rooms.add(r);
        }
        return rooms;
    }
}
