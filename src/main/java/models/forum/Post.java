package models.forum;

public class Post {
    private int post_id;
    private int room_id;
    private String author;
    private String content;
    private String img_url;
    private int NumLikes;
    private int NumDislikes;
    private int user_id;

    public Post(){}

    public Post(int room_id , String author , String content , String url ,int NumLikes,int NumDislikes , int user_id){
        this.room_id = room_id;
        this.author = author;
        this.content = content;
        this.img_url = url;
        this.user_id = user_id;
        this.NumLikes = NumLikes;
        this.NumDislikes = NumDislikes;
    }
    public Post(int id , int room_id , String author , String content , String url , int NumLikes,int NumDislikes, int user_id){
        this.post_id = id;
        this.room_id = room_id;
        this.author = author;
        this.content = content;
        this.user_id = user_id;
        this.img_url = url;
        this.NumLikes = NumLikes;
        this.NumDislikes = NumDislikes;
    }

    public Post(int room_id , String author , String content , String url , int user_id){
        this.room_id = room_id;
        this.author = author;
        this.content = content;
        this.user_id = user_id;
        this.img_url = url;
    }

    public Post(int id , int room_id , String author , String content , String url,int user_id){
        this.post_id = id;
        this.room_id = room_id;
        this.author = author;
        this.content = content;
        this.user_id = user_id;
        this.img_url = url;
    }

    public Post(int room_id , String author , String content , String url){
        this.room_id = room_id;
        this.author = author;
        this.content = content;
        this.img_url = url;
    }

    public Post(int id , int room_id , String author , String content , String url){
        this.post_id = id;
        this.room_id = room_id;
        this.author = author;
        this.content = content;
        this.img_url = url;
    }


    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", room_id=" + room_id +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", img_url='" + img_url + '\'' +
                ", NumLikes=" + NumLikes +
                ", NumDislikes=" + NumDislikes +
                ", user_id=" + user_id +
                '}';
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
