package models.offers;

import java.util.Date;

public class Offer {

    private int id ,funding_id,project_id,user_id,status,reciever_id;
    private Date date_created;
    private String title, description;

    public Offer(int id, int funding_id, Date date_created, String title, String description ,int project_id) {
        this.id = id;
        this.funding_id = funding_id;
        this.date_created = date_created;
        this.title = title;
        this.description = description;
        this.project_id=project_id;
    }

    public Offer(int funding_id, int project_id, int user_id, int reciever_id,  String title, String description) {
        this.funding_id = funding_id;
        this.project_id = project_id;
        this.user_id = user_id;
        this.reciever_id = reciever_id;
        this.title = title;
        this.description = description;
    }

    public Offer(int funding_id, Date date_created, String title, String description ) {
        this.funding_id = funding_id;
        this.date_created = date_created;
        this.title = title;
        this.description = description;
    }

    public Offer(int funding_id,String title, String description, int project_id) {
        this.funding_id=funding_id;
        this.title = title;
        this.description = description;
        this.project_id=project_id;
    }

    public Offer() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getFunding_id() {
        return funding_id;
    }

    public void setFunding_id(int funding_id) {
        this.funding_id = funding_id;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getReciever_id() {
        return reciever_id;
    }

    public void setReciever_id(int reciever_id) {
        this.reciever_id = reciever_id;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", funding_id=" + funding_id +
                ", project_id=" + project_id +
                ", user_id=" + user_id +
                ", status=" + status +
                ", date_created=" + date_created +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
