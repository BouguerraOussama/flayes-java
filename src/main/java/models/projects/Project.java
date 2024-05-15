package models.projects;

import java.util.Date;

public class Project {
    private int id , user_id;
    private String name, description,type;
    //admin status {0,1,2} = {admin not reviewed (on hold), accepted by admin, denied by admin}
    //user status {0,1} = {no match , ended}
    private int admin_status , user_status;
    private Date added_date,end_date;

    public Project(String name, String description, String type, int admin_status, int user_status) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.admin_status = admin_status;
        this.user_status = user_status;
    }

    public Project() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getAdmin_status() {
        return admin_status;
    }

    public int getUser_status() {
        return user_status;
    }

    public Date getAdded_date() {
        return added_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAdmin_status(int admin_status) {
        this.admin_status = admin_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public void setAdded_date(Date added_date) {
        this.added_date = added_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
