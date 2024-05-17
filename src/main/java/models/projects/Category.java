package models.projects;

public class Category {
    private int id;
    private String categoryName;
    private String subfield;
    private String typeOfFunding;
    private String profitabilityIndex;

    // Constructor without id for creating a new Category
    public Category(String categoryName, String subfield, String typeOfFunding, String profitabilityIndex) {
        this.categoryName = categoryName;
        this.subfield = subfield;
        this.typeOfFunding = typeOfFunding;
        this.profitabilityIndex = profitabilityIndex;
    }

    // Constructor with id for fetching Category from database
    public Category(int id, String categoryName, String subfield, String typeOfFunding, String profitabilityIndex) {
        this.id = id;
        this.categoryName = categoryName;
        this.subfield = subfield;
        this.typeOfFunding = typeOfFunding;
        this.profitabilityIndex = profitabilityIndex;
    }

    // Constructor with only categoryName for fetching Category by name
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    // Default constructor
    public Category() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubfield() {
        return subfield;
    }

    public void setSubfield(String subfield) {
        this.subfield = subfield;
    }

    public String getTypeOfFunding() {
        return typeOfFunding;
    }

    public void setTypeOfFunding(String typeOfFunding) {
        this.typeOfFunding = typeOfFunding;
    }

    public String getProfitabilityIndex() {
        return profitabilityIndex;
    }

    public void setProfitabilityIndex(String profitabilityIndex) {
        this.profitabilityIndex = profitabilityIndex;
    }

    // toString method for debugginga
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", subfield='" + subfield + '\'' +
                ", typeOfFunding='" + typeOfFunding + '\'' +
                ", profitabilityIndex='" + profitabilityIndex + '\'' +
                '}';
    }
}