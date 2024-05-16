package models.offers;

public class Category {
    private int id;
    private String type;
    private float attribute1, attribute2, attribute3, score;
    private String textAttribute;

    public Category(String type, float attribute1, float attribute2, float attribute3, String textAttribute) {
        this.type = type;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
        this.textAttribute = textAttribute;
        this.score = calculateScore();
    }

    public Category(String type, float attribute1, float attribute2, float attribute3) {
        this.type = type;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
    }

    public Category() {

    }

    private float calculateScore() {
        switch (this.type) {
            case "Equity":

                break;
        }
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setTextAttribute(String textAttribute) {
        this.textAttribute = textAttribute;
    }

    public String getTextAttribute() {
        return textAttribute;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(float attribute1) {
        this.attribute1 = attribute1;
    }

    public float getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(float attribute2) {
        this.attribute2 = attribute2;
    }

    public float getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(float attribute3) {
        this.attribute3 = attribute3;
    }


    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", attribute1=" + attribute1 +
                ", attribute2=" + attribute2 +
                ", attribute3=" + attribute3 +
                ", score=" + score +
                ", textAttribute='" + textAttribute + '\'' +
                '}';
    }
}
