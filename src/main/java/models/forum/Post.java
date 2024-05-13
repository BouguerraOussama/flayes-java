package models.forum;

public class Post {
    private int post_id;
    private int room_id;
    private String author;
    private String content;
    private String img_url;
    private int number_of_comments;
    private int NumLikes;
    private int NumDislikes;

    public Post(){}

    public Post(int room_id , String author , String content , String url , int nc,int NumLikes,int NumDislikes){
        this.room_id = room_id;
        this.author = author;
        this.content = content;
        this.img_url = url;
        this.number_of_comments = nc;
        this.NumLikes = NumLikes;
        this.NumDislikes = NumDislikes;
    }
    public Post(int id , int room_id , String author , String content , String url , int nc,int NumLikes,int NumDislikes){
        this.post_id = id;
        this.room_id = room_id;
        this.author = author;
        this.content = content;
        this.number_of_comments = nc;
        this.img_url = url;
        this.NumLikes = NumLikes;
        this.NumDislikes = NumDislikes;
    }

    public Post(int room_id , String author , String content , String url , int nc){
        this.room_id = room_id;
        this.author = author;
        this.content = content;
        this.number_of_comments = nc;
        this.img_url = url;
    }

    public Post(int id , int room_id , String author , String content , String url,int nc){
        this.post_id = id;
        this.room_id = room_id;
        this.author = author;
        this.content = content;
        this.number_of_comments = nc;
        this.img_url = url;
    }



    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumber_of_comments() {
        return number_of_comments;
    }

    public void setNumber_of_comments(int number_of_comments) {
        this.number_of_comments = number_of_comments;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String url) {
        this.img_url = url;
    }

    public void setNumLikes(int NumLikes){
        this.NumLikes = NumLikes;
    }
    public int getNumLikes() {
        return NumLikes;
    }

    public void setNumDislikes(int NumDislikes){
        this.NumDislikes = NumDislikes;
    }
    public int getNumDislikes() {
        return NumDislikes;
    }
}
