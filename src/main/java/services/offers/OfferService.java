package services.offers;

import models.offers.Offer;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OfferService implements IService<Offer> {
    private int fk;
    private Connection connection;
    public OfferService(){
        connection = MyDataBase.getInstance().getConnection();
    }
    public  int  getFk(String type) throws SQLException {
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
    public int create(Offer offer)  {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        String sql = "insert into offer (title, description, date_created,funding_id,project_id,status) values (?, ?, ?, ?,?,?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, offer.getTitle());
            ps.setString(2, offer.getDescription());
            ps.setTimestamp(3, currentTimestamp);
            ps.setInt(4,offer.getFunding_id());
            ps.setInt(5, offer.getProject_id());
            ps.setInt(6, 0);
            ps.executeUpdate();
        }catch (SQLException e) {
            // Handle any exceptions here
            e.printStackTrace();
        }
        return 1;
    }



    @Override
    public void update(Offer o) throws SQLException {

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
        String sql = "select * from offer where user_id = 1";
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

}
