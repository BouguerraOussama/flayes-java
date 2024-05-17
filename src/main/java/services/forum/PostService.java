package services.forum;

import models.forum.Post;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostService implements IService<Post>{
    private Connection connection;

    public PostService(){connection = MyDataBase.getInstance().getConnection();}
    @Override
    public void create(Post post) throws SQLException {
        String sql = "INSERT INTO Post (room_id, author, content, img_url , number_of_comments) " +
                "VALUES ('" + post.getRoom_id() + "', '" + post.getAuthor() + "', '" +
                post.getContent() + "', '" + post.getImg_url() + "', '" + post.getNumber_of_comments() + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void update(Post post) throws SQLException {
        String sql = "UPDATE Post SET room_id = ?, author = ?, content = ?, img_url = ?, number_of_comments = ?  where post_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, post.getRoom_id());
        ps.setString(2, post.getAuthor());
        ps.setString(3,post.getContent());
        ps.setString(4,post.getImg_url());
        ps.setInt(5, post.getNumber_of_comments());
        ps.setInt(6, post.getPost_id());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Post WHERE post_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    @Override
    public List<Post> read() throws SQLException {
        String sql = "SELECT * FROM Post";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Post> posts = new ArrayList<>();
        while (rs.next()){
            Post p = new Post();
            p.setPost_id(rs.getInt("post_id"));
            p.setRoom_id(rs.getInt("room_id"));
            p.setAuthor(rs.getString("author"));
            p.setContent(rs.getString("content"));
            p.setImg_url(rs.getString("img_url"));
            p.setNumber_of_comments(rs.getInt("number_of_comments"));

            posts.add(p);
        }
        return posts;
    }

    public List<Post> readByRoomId(int room_id) throws SQLException {
        String sql = "SELECT * FROM Post WHERE room_id = ?";

        List<Post> posts = new ArrayList<>();

        // Check if the room_id exists in the Room table
        boolean roomExists = false;
        String roomCheckSql = "SELECT COUNT(*) AS count FROM Room WHERE room_id = ?";
        try (PreparedStatement roomCheckStatement = connection.prepareStatement(roomCheckSql)) {
            roomCheckStatement.setInt(1, room_id);
            try (ResultSet roomCheckRs = roomCheckStatement.executeQuery()) {
                if (roomCheckRs.next()) {
                    int count = roomCheckRs.getInt("count");
                    if (count > 0) {
                        roomExists = true;
                    }
                }
            }
        }

        if (!roomExists) {
            throw new IllegalArgumentException("Room with ID " + room_id + " does not exist.");
        }

        // Execute query to fetch posts
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, room_id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Post p = new Post();
                    p.setPost_id(rs.getInt("post_id"));
                    p.setRoom_id(rs.getInt("room_id"));
                    p.setAuthor(rs.getString("author"));
                    p.setContent(rs.getString("content"));
                    p.setImg_url(rs.getString("img_url"));
                    p.setNumber_of_comments(rs.getInt("number_of_comments"));
                    posts.add(p);
                }
            }
        }
        return posts;
    }

    public Post readOne(int id) throws SQLException {
        String sql = "select * from post where post_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            Post p = new Post();
            p.setPost_id(rs.getInt("post_id"));
            p.setRoom_id(rs.getInt("room_id"));
            p.setAuthor(rs.getString("author"));
            p.setContent(rs.getString("content"));
            p.setImg_url(rs.getString("img_url"));
            p.setNumber_of_comments(rs.getInt("number_of_comments"));
            return p;
        } else {
            return null;
        }

    }

}
