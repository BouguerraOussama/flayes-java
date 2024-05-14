package controllers.forum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.forum.Post;
import models.forum.Reacts;
import services.forum.PostService;
import services.forum.ReactsService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AddPostCardController {

    @FXML
    private Text postTxt;

    @FXML
    private ImageView img;

    @FXML
    private Text authorTF;

    @FXML
    private Text numbreOfComments;

    @FXML
    private Button onDelete;

    @FXML
    private Button onModify;

    @FXML
    private Button LikePost;

    @FXML
    private Button DislikePost;

    @FXML
    private Text numberOfLikes;

    @FXML
    private Text numberOfDislikes;
    private Post post;
    private AddPostController controller;
    private int room_id;

    PostService ps = new PostService();
    ReactsService prs = new ReactsService();

     @FXML
     void onViewComments(ActionEvent event) {}

    public void setRoomId(int room_id) {
        this.room_id = room_id;
    }

    @FXML
    public void initialize(int rid){
        setRoomId(rid);
    }
    public void setData(Post post) throws SQLException {
        this.post = post;
        refreshPost(post);
    }
    public void setUpController(AddPostController alc) {
        this.controller = alc;
    }

    public void setPost() throws SQLException {
        refreshPost(ps.readOne(post.getPost_id()));
    }

    public void refreshPost(Post post) throws SQLException {
        postTxt.setText(post.getContent());
        String path = post.getImg_url();
        ImportPicture(path,img);
        authorTF.setText(post.getAuthor());
        numberOfDislikes.setText(String.valueOf(ps.readOne(post.getPost_id()).getNumDislikes()));
        numberOfLikes.setText(String.valueOf(ps.readOne(post.getPost_id()).getNumLikes()));
    }
    void ImportPicture(String path , ImageView view){
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        view.setImage(image);
    }


    public void onDelete(ActionEvent event) throws SQLException {
            try{
                ps.delete(post.getPost_id());
            }catch(SQLException ex){
                throw new SQLException();
            }

    }

    public void onModify(ActionEvent event) throws SQLException {
        try {
            // Create a new stage for the "Update Room" window
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/forum/UpdatePost.fxml"));
            Stage updateStage = new Stage();
            updateStage.initModality(Modality.APPLICATION_MODAL);
            Parent root = fxmlLoader.load();
            UpdatePostController controller = fxmlLoader.getController();
            controller.initialize(post.getPost_id());
            controller.initData(post);
            updateStage.setScene(new Scene(root));
            updateStage.setTitle("Update Room");
            updateStage.show();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        setPost();
    }
    public void react(boolean isLiked) throws SQLException {
        Reacts existingReact = prs.getReactByUserAndPost(1, post.getPost_id());
        if (existingReact != null) {
            if (isLiked) {
                prs.updateLikesNumber(post.getPost_id(), true);
            } else {
                prs.updateDislikesNumber(post.getPost_id(), true);
            }
            prs.updateReact(post.getPost_id(), isLiked);
            prs.removeReact(existingReact.getUser_id(), existingReact.getReact_id());
        } else {
            Reacts react = new Reacts(post.getPost_id(), 1, isLiked);
            prs.createReact(react);
            if (isLiked) {
                prs.updateLikesNumber(post.getPost_id(), false);
            } else {
                prs.updateDislikesNumber(post.getPost_id(), false);
            }
            prs.updateReact(post.getPost_id(), isLiked);
        }
        setPost();
    }

    public void LikePost(ActionEvent event) throws IOException, SQLException {
         boolean isLiked = true;
        react(isLiked);
        System.out.println(post.getNumLikes());
    }

    public void DislikePost(ActionEvent event) throws IOException, SQLException {
        boolean isLiked = false;
        react(isLiked);
    }
}
