package models.forum;

import java.util.ArrayList;
import java.util.List;
public class Room {

    //    public enum Category { FINANCE,AGRICULTURE,BUSINESS,MANUFACTURING,EDUCATION }
    private int room_id;
    private String category;
    private String sub_category;
    private String description;

    private List<Post> posts = null;

    public Room(){}

    public Room(String category , String sub_category , String description){
        this.category = category;
        this.sub_category = sub_category;
        this.description = description;
        this.posts = new ArrayList<>();
    }
    public Room(int id , String category , String sub_category , String description){
        this.room_id = id;
        this.category = category;
        this.sub_category = sub_category;
        this.description = description;
        this.posts = new ArrayList<>();
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSub_category(){
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addPosts(Post p){
        posts.add(p);
    }
}

