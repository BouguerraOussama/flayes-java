package services.forum;

import models.forum.Reacts;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReactsService implements IService<Reacts>{
    private final Connection connection;
        public ReactsService() {
            connection = MyDataBase.getInstance().getConnection();
        }
    public void createReact(Reacts reacts) throws SQLException {
        String sql = "INSERT INTO post_react (user_id, post_id, isliked) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, reacts.getUser_id());
        statement.setInt(2, reacts.getPost_id());
        statement.setBoolean(3, reacts.Islike());
        statement.executeUpdate();
    }

    public void updateReact(int react_id, boolean isliked) throws SQLException {
        String sql = "UPDATE post_react SET isliked = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setBoolean(1, isliked);
        ps.setInt(2, react_id);
        ps.executeUpdate();
    }
    public void removeReact(int user_id, int post_id) throws SQLException {
        String sql = "DELETE FROM post_react WHERE user_id = ? AND post_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, user_id);
        statement.setInt(2, post_id);
        statement.executeUpdate();
    }

    public Reacts getReactByUserAndPost(int user_id, int post_id) throws SQLException {
        Reacts react = null;
        String query = "SELECT * FROM post_react WHERE user_id = ? AND post_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, user_id);
        statement.setInt(2, post_id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("react_id");
            boolean isliked = resultSet.getBoolean("isliked");
            react = new Reacts(id, post_id, user_id, isliked);
        }
        return react;
    }


    private int getCurrentLikes(int postId) throws SQLException {
        String sql = "SELECT num_likes FROM post WHERE post_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, postId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("num_likes");
        }
        return 0;
    }

    private int getCurrentDislikes(int postId) throws SQLException {
        String sql = "SELECT num_dislikes FROM post WHERE post_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, postId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("num_dislikes");
        }
        return 0;
    }


    public void updateDislikesNumber(int postId, boolean isClicked) throws SQLException {
        String sql = "UPDATE post SET num_dislikes = ? WHERE post_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        int currentDislikes = getCurrentDislikes(postId);
        if (isClicked) {
            ps.setInt(1, currentDislikes - 1);
        } else {
            ps.setInt(1, currentDislikes + 1);
        }
        ps.setInt(2, postId);
        ps.executeUpdate();
    }

    public void updateLikesNumber(int postId, boolean isClicked) throws SQLException {
        String sql = "UPDATE post SET num_likes = ? WHERE post_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        int currentlikes = getCurrentLikes(postId);
        if (isClicked) {
            ps.setInt(1, currentlikes - 1);
        } else {
            ps.setInt(1, currentlikes + 1);
        }
        ps.setInt(2, postId);
        ps.setInt(2, postId);
        ps.executeUpdate();
    }


    @Override
    public void create(Reacts reacts) throws SQLException {

    }

    @Override
    public void update(Reacts reacts) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Reacts> read() throws SQLException {
        return null;
    }

}
