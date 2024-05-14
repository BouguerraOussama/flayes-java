package models.forum;

public class Reacts {
    private int react_id,post_id, user_id;
    boolean Isliked;

    public Reacts(int idReact, int idReactedPost,int idAuthor, boolean islike) {
        react_id = idReact;
        post_id = idReactedPost;
        user_id = idAuthor;
        Isliked = islike;
    }

    public Reacts(int idReactedPost,int idAuthor, boolean islike) {
        post_id = idReactedPost;
        user_id = idAuthor;
        Isliked = islike;
    }


    @Override
    public String toString() {
        return "Reacts{" +
                "IdReact=" + react_id +
                ", IdReactedPost=" + post_id +
                ", IdAuthor=" + user_id +
                ", Islike=" + Isliked +
                '}';
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int idAuthor) {
        user_id = idAuthor;
    }

    public int getReact_id() {
        return react_id;
    }

    public void setReact_id(int idReact) {
        react_id = idReact;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int idReactedPost) {
        post_id = idReactedPost;
    }

    public boolean Islike() {
        return Isliked;
    }

    public void setIslike(boolean islike) {
        Isliked = islike;
    }
}
