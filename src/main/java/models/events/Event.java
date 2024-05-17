package models.events;

public class Event {
    private int Idevent;
    private int idcat; // Added idcat attribute
    private String name;
    private String date;
    private String description;
    private String image;
    private String location;
private String qrcode;

    public Event(String name, String date, String description, String location, String image , int idcat, String qrcode) {
        this.idcat = idcat;
        this.name = name;
        this.date = date;
        this.description = description;
        this.image = image;
        this.location = location;
        this.qrcode = qrcode;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Event(int id, String eventName, String eventDate, String eventDescription, String eventImage, String eventLocation, int eventCategoryId) {
    }

    public int getIdevent() {
        return Idevent;
    }

    public void setIdevent(int idevent) {
        Idevent = idevent;
    }

    public int getIdcat() {
        return idcat;
    }

    public void setIdcat(int idcat) {
        this.idcat = idcat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Event{" +
                "Idevent=" + Idevent +
                ", idcat=" + idcat + // Corrected to include idcat
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    // Constructors
    public Event() {
        // Default constructor
    }

    public Event(String name, String date, String description, String location, String image, int idcat) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.location = location;
        this.image = image;
        this.idcat = idcat; // Assign idcat parameter to idcat attribute
    }
}
