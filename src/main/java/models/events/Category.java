package models.events;

public class Category {

    int Idcat;
    //int Idevent;
    String target;
    String name;
    String type;


    public Category() {

    }

    public Category(String text, String text1, String text2) {
        this.name=text;
        this.type=text1;
        this.target=text2;
    }

    public int getIdcat() {
        return Idcat;
    }

    public void setIdcat(int idcat) {
        Idcat = idcat;
    }



    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
