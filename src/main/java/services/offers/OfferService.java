package services.offers;

import models.offers.Offer;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OfferService implements IService<Offer> {

    private Connection connection;

    public OfferService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    public int getFk(String type) throws SQLException {
        String query = "SELECT id FROM funding WHERE type=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, type);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                // Handle the case where no matching row is found
                return -1; // or throw an exception, return a default value, etc.
            }
        }

    }

    @Override
    public int create(Offer offer) throws SQLException {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        String sql = "insert into offer (title, description, status, date_created,funding_id,project_id,user_id,reciever_id) values (?,?, ?, ?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, offer.getTitle());
            ps.setString(2, offer.getDescription());
            ps.setInt(3, 0);
            ps.setTimestamp(4, currentTimestamp);
            ps.setInt(5, offer.getFunding_id());
            ps.setInt(6, offer.getProject_id());
            ps.setInt(7, offer.getUser_id());
            ps.setInt(8, offer.getReciever_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            // Handle any exceptions here
            e.printStackTrace();
        }
        return 1;
    }


    @Override
    public void update(Offer offer) throws SQLException {
        String sql = "UPDATE offer SET    title = ?, description = ?, status = 0 WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, offer.getTitle());
            statement.setString(2, offer.getDescription());
            statement.setInt(3, offer.getId());

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM offer WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    @Override
    public List<Offer> read() throws SQLException {
        String sql = "select * from offer";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Offer> offers = new ArrayList<>();
        while (rs.next()) {
            Offer offer = new Offer();
            offer.setId(rs.getInt("id"));
            offer.setTitle(rs.getString("title"));
            offer.setDescription(rs.getString("description"));
            offer.setDate_created(rs.getDate("date_created"));
            offer.setFunding_id(rs.getInt("funding_id"));
            offer.setProject_id(rs.getInt("project_id"));
            offer.setUser_id(rs.getInt("user_id"));
            offers.add(offer);
        }
        return offers;
    }

    public List<Offer> readOffersImade(int user_id) throws SQLException {
        String query = "SELECT * FROM offer WHERE user_id = ?";
        List<Offer> offers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Offer offer = new Offer();
                    offer.setId(rs.getInt("id"));
                    offer.setTitle(rs.getString("title"));
                    offer.setDescription(rs.getString("description"));
                    offer.setStatus(rs.getInt("status"));
                    offer.setDate_created(rs.getDate("date_created"));
                    offer.setFunding_id(rs.getInt("funding_id"));
                    offer.setProject_id(rs.getInt("project_id"));
                    offer.setUser_id(rs.getInt("user_id"));
                    offer.setReciever_id(rs.getInt("reciever_id"));
                    offers.add(offer);
                }
            }
        }
        return offers;
    }

    public List<Offer> readOffersIgot(int user_id) throws SQLException {
        String query = "SELECT * FROM offer WHERE reciever_id = ? and  status > 1";
        List<Offer> offers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Offer offer = new Offer();
                    offer.setId(rs.getInt("id"));
                    offer.setTitle(rs.getString("title"));
                    offer.setDescription(rs.getString("description"));
                    offer.setStatus(rs.getInt("status"));
                    offer.setDate_created(rs.getDate("date_created"));
                    offer.setFunding_id(rs.getInt("funding_id"));
                    offer.setProject_id(rs.getInt("project_id"));
                    offer.setUser_id(rs.getInt("user_id"));
                    offer.setReciever_id(rs.getInt("reciever_id"));
                    offers.add(offer);
                }
            }
        }
        return offers;
    }


}
