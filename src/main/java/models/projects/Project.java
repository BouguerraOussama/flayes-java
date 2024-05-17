package models.projects;

public class Project {
    private int id;
    private String name;
    private String image;
    private String description;
    private String targetAudience;
    private String demandInMarket;
    private String developmentTimeline;
    private double budgetFundingRequirements;
    private String riskAnalysis;
    private String marketStrategy;
    private String exitStrategy;
    private String teamBackground;
    private String tags;
    private String categoryName;
    private String uniqueSellingPoints;
    private String dailyPriceOfAssets;
    private String investorsEquity;
    private int categoryId;

    // Constructor without id for creating a new Project
    public Project(String name, String image, String description, int categoryId) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.categoryId = categoryId;
    }
    public Project(String name, String image, String description, String targetAudience, String demandInMarket,
                   String developmentTimeline, double budgetFundingRequirements, String riskAnalysis,
                   String marketStrategy, String exitStrategy, String teamBackground, String tags,
                   String uniqueSellingPoints, String dailyPriceOfAssets, String investorsEquity, int categoryId) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.targetAudience = targetAudience;
        this.demandInMarket = demandInMarket;
        this.developmentTimeline = developmentTimeline;
        this.budgetFundingRequirements = budgetFundingRequirements;
        this.riskAnalysis = riskAnalysis;
        this.marketStrategy = marketStrategy;
        this.exitStrategy = exitStrategy;
        this.teamBackground = teamBackground;
        this.tags = tags;
        this.uniqueSellingPoints = uniqueSellingPoints;
        this.dailyPriceOfAssets = dailyPriceOfAssets;
        this.investorsEquity = investorsEquity;
        this.categoryId = categoryId;
    }
    public Project(int id,String name, String image, String description, String targetAudience, String demandInMarket,
                   String developmentTimeline, double budgetFundingRequirements, String riskAnalysis,
                   String marketStrategy, String exitStrategy, String teamBackground, String tags,
                   String uniqueSellingPoints, String dailyPriceOfAssets, String investorsEquity, int categoryId) {
        this.id=id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.targetAudience = targetAudience;
        this.demandInMarket = demandInMarket;
        this.developmentTimeline = developmentTimeline;
        this.budgetFundingRequirements = budgetFundingRequirements;
        this.riskAnalysis = riskAnalysis;
        this.marketStrategy = marketStrategy;
        this.exitStrategy = exitStrategy;
        this.teamBackground = teamBackground;
        this.tags = tags;
        this.uniqueSellingPoints = uniqueSellingPoints;
        this.dailyPriceOfAssets = dailyPriceOfAssets;
        this.investorsEquity = investorsEquity;
        this.categoryId = categoryId;
    }


    // Constructor with only name for fetching Project by name
    public Project(String name) {
        this.name = name;
    }

    // Default constructor
    public Project() {

    }
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getDemandInMarket() {
        return demandInMarket;
    }

    public void setDemandInMarket(String demandInMarket) {
        this.demandInMarket = demandInMarket;
    }

    public String getDevelopmentTimeline() {
        return developmentTimeline;
    }

    public void setDevelopmentTimeline(String developmentTimeline) {
        this.developmentTimeline = developmentTimeline;
    }

    public double getBudgetFundingRequirements() {
        return budgetFundingRequirements;
    }

    public void setBudgetFundingRequirements(double budgetFundingRequirements) {
        this.budgetFundingRequirements = budgetFundingRequirements;
    }


    public String getRiskAnalysis() {
        return riskAnalysis;
    }

    public void setRiskAnalysis(String riskAnalysis) {
        this.riskAnalysis = riskAnalysis;
    }

    public String getMarketStrategy() {
        return marketStrategy;
    }

    public void setMarketStrategy(String marketStrategy) {
        this.marketStrategy = marketStrategy;
    }

    public String getExitStrategy() {
        return exitStrategy;
    }

    public void setExitStrategy(String exitStrategy) {
        this.exitStrategy = exitStrategy;
    }

    public String getTeamBackground() {
        return teamBackground;
    }

    public void setTeamBackground(String teamBackground) {
        this.teamBackground = teamBackground;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUniqueSellingPoints() {
        return uniqueSellingPoints;
    }

    public void setUniqueSellingPoints(String uniqueSellingPoints) {
        this.uniqueSellingPoints = uniqueSellingPoints;
    }

    public String getDailyPriceOfAssets() {
        return dailyPriceOfAssets;
    }

    public void setDailyPriceOfAssets(String dailyPriceOfAssets) {
        this.dailyPriceOfAssets = dailyPriceOfAssets;
    }

    public String getInvestorsEquity() {
        return investorsEquity;
    }

    public void setInvestorsEquity(String investorsEquity) {
        this.investorsEquity = investorsEquity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", targetAudience='" + targetAudience + '\'' +
                ", demandInMarket='" + demandInMarket + '\'' +
                ", developmentTimeline='" + developmentTimeline + '\'' +
                ", budgetFundingRequirements=" + budgetFundingRequirements +
                ", riskAnalysis='" + riskAnalysis + '\'' +
                ", marketStrategy='" + marketStrategy + '\'' +
                ", exitStrategy='" + exitStrategy + '\'' +
                ", teamBackground='" + teamBackground + '\'' +
                ", tags='" + tags + '\'' +
                ", uniqueSellingPoints='" + uniqueSellingPoints + '\'' +
                ", dailyPriceOfAssets='" + dailyPriceOfAssets + '\'' +
                ", investorsEquity='" + investorsEquity + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}