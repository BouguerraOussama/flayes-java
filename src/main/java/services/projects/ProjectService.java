package services.projects;

import models.projects.Project;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    public Project createProject(Project project) throws SQLException {
        String sql = "INSERT INTO projects (name, image, description, target_audience, demand_in_market, development_timeline, " +
                "budget_funding_requirements, risk_analysis, market_strategy, exit_strategy, team_background, tags, " +
                "unique_selling_points, daily_price_of_assets, investors_equity, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, project.getName());
            pstmt.setString(2, project.getImage());
            pstmt.setString(3, project.getDescription());
            pstmt.setString(4, project.getTargetAudience());
            pstmt.setString(5, project.getDemandInMarket());
            pstmt.setString(6, project.getDevelopmentTimeline());
            pstmt.setDouble(7, project.getBudgetFundingRequirements());
            pstmt.setString(8, project.getRiskAnalysis());
            pstmt.setString(9, project.getMarketStrategy());
            pstmt.setString(10, project.getExitStrategy());
            pstmt.setString(11, project.getTeamBackground());
            pstmt.setString(12, project.getTags());
            pstmt.setString(13, project.getUniqueSellingPoints());
            pstmt.setString(14, project.getDailyPriceOfAssets());
            pstmt.setString(15, project.getInvestorsEquity());
            pstmt.setInt(16, project.getCategoryId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        project.setId(rs.getInt(1));
                    }
                }
            }
        }
        return project;
    }

    public Project getProjectById(int id) throws SQLException {
        String sql = "SELECT * FROM projects WHERE id = ?";
        Project project = null;

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setImage(rs.getString("image"));
                project.setDescription(rs.getString("description"));
                project.setTargetAudience(rs.getString("target_audience"));
                project.setDemandInMarket(rs.getString("demand_in_market"));
                project.setDevelopmentTimeline(rs.getString("development_timeline"));
                project.setBudgetFundingRequirements(rs.getDouble("budget_funding_requirements"));
                project.setRiskAnalysis(rs.getString("risk_analysis"));
                project.setMarketStrategy(rs.getString("market_strategy"));
                project.setExitStrategy(rs.getString("exit_strategy"));
                project.setTeamBackground(rs.getString("team_background"));
                project.setTags(rs.getString("tags"));
                project.setUniqueSellingPoints(rs.getString("unique_selling_points"));
                project.setDailyPriceOfAssets(rs.getString("daily_price_of_assets"));
                project.setInvestorsEquity(rs.getString("investors_equity"));
                project.setCategoryId(rs.getInt("category_id"));
            }
        }
        return project;
    }

    public List<Project> getAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects";

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setImage(rs.getString("image"));
                project.setDescription(rs.getString("description"));
                project.setTargetAudience(rs.getString("target_audience"));
                project.setDemandInMarket(rs.getString("demand_in_market"));
                project.setDevelopmentTimeline(rs.getString("development_timeline"));
                project.setBudgetFundingRequirements(rs.getDouble("budget_funding_requirements"));
                project.setRiskAnalysis(rs.getString("risk_analysis"));
                project.setMarketStrategy(rs.getString("market_strategy"));
                project.setExitStrategy(rs.getString("exit_strategy"));
                project.setTeamBackground(rs.getString("team_background"));
                project.setTags(rs.getString("tags"));
                project.setUniqueSellingPoints(rs.getString("unique_selling_points"));
                project.setDailyPriceOfAssets(rs.getString("daily_price_of_assets"));
                project.setInvestorsEquity(rs.getString("investors_equity"));
                project.setCategoryId(rs.getInt("category_id"));
                projects.add(project);
            }
        }
        return projects;
    }

    public boolean updateProject(Project project) throws SQLException {
        String sql = "UPDATE projects SET name = ?, image = ?, description = ?, target_audience = ?, demand_in_market = ?, " +
                "development_timeline = ?, budget_funding_requirements = ?, risk_analysis = ?, market_strategy = ?, " +
                "exit_strategy = ?, team_background = ?, tags = ?, unique_selling_points = ?, daily_price_of_assets = ?, " +
                "investors_equity = ?, category_id = ? WHERE id = ?";

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, project.getName());
            pstmt.setString(2, project.getImage());
            pstmt.setString(3, project.getDescription());
            pstmt.setString(4, project.getTargetAudience());
            pstmt.setString(5, project.getDemandInMarket());
            pstmt.setString(6, project.getDevelopmentTimeline());
            pstmt.setDouble(7, project.getBudgetFundingRequirements());
            pstmt.setString(8, project.getRiskAnalysis());
            pstmt.setString(9, project.getMarketStrategy());
            pstmt.setString(10, project.getExitStrategy());
            pstmt.setString(11, project.getTeamBackground());
            pstmt.setString(12, project.getTags());
            pstmt.setString(13, project.getUniqueSellingPoints());
            pstmt.setString(14, project.getDailyPriceOfAssets());
            pstmt.setString(15, project.getInvestorsEquity());
            pstmt.setInt(16, project.getCategoryId());
            pstmt.setInt(17, project.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean deleteProject(int id) throws SQLException {
        String sql = "DELETE FROM projects WHERE id = ?";

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public Project getProjectByName(String name) throws SQLException {
        String sql = "SELECT * FROM projects WHERE name = ?";
        Project project = null;

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setImage(rs.getString("image"));
                project.setDescription(rs.getString("description"));
                project.setTargetAudience(rs.getString("target_audience"));
                project.setDemandInMarket(rs.getString("demand_in_market"));
                project.setDevelopmentTimeline(rs.getString("development_timeline"));
                project.setBudgetFundingRequirements(rs.getDouble("budget_funding_requirements"));
                project.setRiskAnalysis(rs.getString("risk_analysis"));
                project.setMarketStrategy(rs.getString("market_strategy"));
                project.setExitStrategy(rs.getString("exit_strategy"));
                project.setTeamBackground(rs.getString("team_background"));
                project.setTags(rs.getString("tags"));
                project.setUniqueSellingPoints(rs.getString("unique_selling_points"));
                project.setDailyPriceOfAssets(rs.getString("daily_price_of_assets"));
                project.setInvestorsEquity(rs.getString("investors_equity"));
                project.setCategoryId(rs.getInt("category_id"));
            }
        }
        return project;
    }

    public boolean updateProjectByName(String name, Project updatedProject) throws SQLException {
        String sql = "UPDATE projects SET name = ?, image = ?, description = ?, target_audience = ?, demand_in_market = ?, " +
                "development_timeline = ?, budget_funding_requirements = ?, risk_analysis = ?, market_strategy = ?, " +
                "exit_strategy = ?, team_background = ?, tags = ?, unique_selling_points = ?, daily_price_of_assets = ?, " +
                "investors_equity = ?, category_id = ? WHERE name = ?";

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, updatedProject.getName());
            pstmt.setString(2, updatedProject.getImage());
            pstmt.setString(3, updatedProject.getDescription());
            pstmt.setString(4, updatedProject.getTargetAudience());
            pstmt.setString(5, updatedProject.getDemandInMarket());
            pstmt.setString(6, updatedProject.getDevelopmentTimeline());
            pstmt.setDouble(7, updatedProject.getBudgetFundingRequirements());
            pstmt.setString(8, updatedProject.getRiskAnalysis());
            pstmt.setString(9, updatedProject.getMarketStrategy());
            pstmt.setString(10, updatedProject.getExitStrategy());
            pstmt.setString(11, updatedProject.getTeamBackground());
            pstmt.setString(12, updatedProject.getTags());
            pstmt.setString(13, updatedProject.getUniqueSellingPoints());
            pstmt.setString(14, updatedProject.getDailyPriceOfAssets());
            pstmt.setString(15, updatedProject.getInvestorsEquity());
            pstmt.setInt(16, updatedProject.getCategoryId());
            pstmt.setString(17, name);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public List<Project> getProjectsWithCategoryNames() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT p.name, c.category_name FROM projects p INNER JOIN categories c ON p.category_id = c.id";

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Project project = new Project();
                project.setName(rs.getString("name"));
                project.setCategoryName(rs.getString("category_name"));
                projects.add(project);
            }
        }
        return projects;
    }

    public void deleteProjectsByCategory(int categoryId) throws SQLException {
        String query = "DELETE FROM projects WHERE category_id = ?";
        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, categoryId);
            statement.executeUpdate();
        }
    }

    public boolean hasDependentProjects(int categoryId) throws SQLException {
        String query = "SELECT COUNT(*) FROM projects WHERE category_id = ?";
        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }

    public boolean deleteProjectByName(String name) throws SQLException {
        String sql = "DELETE FROM projects WHERE name = ?";

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public static List<Project> getProjectsByCategoryName(String categoryName) throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.image, p.description, p.target_audience, p.demand_in_market, p.development_timeline, p.budget_funding_requirements, p.risk_analysis, p.market_strategy, p.exit_strategy, p.team_background, p.tags, p.unique_selling_points, p.daily_price_of_assets, p.investors_equity, p.category_id FROM projects p JOIN categories c ON p.category_id = c.id WHERE c.category_name = ?";

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, categoryName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Project project = new Project();
                // Populate project fields from the result set
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setImage(resultSet.getString("image"));
                project.setDescription(resultSet.getString("description"));
                project.setTargetAudience(resultSet.getString("target_audience"));
                project.setDemandInMarket(resultSet.getString("demand_in_market"));
                project.setDevelopmentTimeline(resultSet.getString("development_timeline"));
                project.setBudgetFundingRequirements(resultSet.getDouble("budget_funding_requirements"));
                project.setRiskAnalysis(resultSet.getString("risk_analysis"));
                project.setMarketStrategy(resultSet.getString("market_strategy"));
                project.setExitStrategy(resultSet.getString("exit_strategy"));
                project.setTeamBackground(resultSet.getString("team_background"));
                project.setTags(resultSet.getString("tags"));
                project.setUniqueSellingPoints(resultSet.getString("unique_selling_points"));
                project.setDailyPriceOfAssets(resultSet.getString("daily_price_of_assets"));
                project.setInvestorsEquity(resultSet.getString("investors_equity"));
                project.setCategoryId(resultSet.getInt("category_id"));

                projects.add(project);
            }
        }
        return projects;
    }

    public void create(Project project) {
    }
}