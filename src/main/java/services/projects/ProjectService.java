package services.projects;

import models.projects.Project;
import services.offers.IService;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectService implements IService<Project> {
    private Connection connection;
    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
    public ProjectService(){connection = MyDataBase.getInstance().getConnection();}

    @Override
    public int create(Project project) throws SQLException {

        String query = "insert into project(name,description,type,status,added_date,end_date,user_status,user_id) values (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setString(3, project.getType());
            ps.setInt(4, project.getAdmin_status());
            ps.setTimestamp(5, currentTimestamp);
            ps.setTimestamp(6, Timestamp.valueOf(currentTimestamp.toLocalDateTime().plusDays(10)));
            ps.setInt(7, project.getUser_status());
            ps.setInt(8, project.getUser_id());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void update(Project project) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Project> read() throws SQLException {
        String sql = "select * from project";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Project> projects = new ArrayList<>();
        while (rs.next()){
            Project p = new Project();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setUser_id(rs.getInt("user_id"));
            p.setDescription(rs.getString("description"));
            p.setType(rs.getString("type"));
            p.setAdmin_status(rs.getInt("status"));
            p.setAdded_date(rs.getDate("added_date"));
            p.setEnd_date(rs.getDate("end_date"));
            p.setUser_status(rs.getInt("user_status"));
            projects.add(p);
        }
        return projects;
    }
    public List<Project> readProjectsIdidntMake(int user_id) throws SQLException {
        String sql = "SELECT * FROM project WHERE user_id != ?";
        List<Project> projects = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user_id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Project p = new Project();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setUser_id(rs.getInt("user_id"));
                    p.setDescription(rs.getString("description"));
                    p.setType(rs.getString("type"));
                    p.setAdmin_status(rs.getInt("status"));
                    p.setAdded_date(rs.getDate("added_date"));
                    p.setEnd_date(rs.getDate("end_date"));
                    p.setUser_status(rs.getInt("user_status"));
                    projects.add(p);
                }
            }
        }

        return projects;
    }
}
