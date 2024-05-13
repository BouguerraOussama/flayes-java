package services.users;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.users.Reclamation;
import models.users.User;
import utils.MyDataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SessionManager;


public class ReclamationService {

        private Connection cnx;
        private Statement ste;
        private PreparedStatement pst;
        private ResultSet rs;
        private ResultSet rc;
    private static User user = new User();
    private UserService us = new UserService();
        public ReclamationService() {
            cnx = MyDataBase.getInstance().getConnection();
        }

        /*public void create(Reclamation t) {
            try {
                String req = "insert into reclamation(id_user,object,type,description,etat,date) values( " + t.getIdc() + ",' " + t.getObjet() + "','" + t.getTypeR() + "','" + t.getDescriptionR() + "','Non traité', NOW());";
                Statement st = this.cnx.createStatement();
                st.executeUpdate(req);
                System.out.println("Reclamation ajoutée");
                System.out.println(t.getDateR());
            } catch (SQLException var4) {
                System.out.println(var4.getMessage());
            }

        }
*/
        public void create(Reclamation t) {
            try {
                String req = "INSERT INTO reclamation (id_user, object, type, description, etat, date) VALUES (?, ?, ?, ?, ?, NOW())";
                PreparedStatement ps = this.cnx.prepareStatement(req);
                ps.setInt(1, SessionManager.getInstance().getUser_id()); // Setting id_user to 5
                ps.setString(2, t.getObjet());
                ps.setString(3, t.getTypeR());
                ps.setString(4, t.getDescriptionR());
                ps.setString(5, "Non traité");

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Reclamation ajoutée");
                } else {
                    System.out.println("Failed to add Reclamation");
                }
            } catch (SQLException e) {
                System.out.println("Error adding Reclamation: " + e.getMessage());
            }
        }

        public void updateR(Reclamation t) {
            try {
                String req = "update reclamation set object=?,type=?,description=? where id_rec= ?";
                PreparedStatement ps = this.cnx.prepareStatement(req);
                ps.setString(1, t.getObjet());
                ps.setString(2, t.getTypeR());
                ps.setString(3, t.getDescriptionR());
                ps.setInt(4, (int) t.getId_rec());
                ps.executeUpdate();
                System.out.println("Reclamation modifiée");
            } catch (SQLException var4) {
                System.out.println(var4.getMessage());
            }

        }

        public void deleteR(Reclamation reclamation) {
            try {
                Statement st = this.cnx.createStatement();
                int id = reclamation.getId_rec(); // Assuming getId() returns the ID of the reclamation
                String req = "DELETE FROM reclamation WHERE id_rec = " + id;
                st.executeUpdate(req);
                System.out.println("Reclamation supprimer avec succès...");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        public List<Reclamation> recuperer() {
            List<Reclamation> reclamations = new ArrayList();

            try {
                String req = "select * from reclamation order by date DESC";
                Statement st = this.cnx.createStatement();
                ResultSet rs = st.executeQuery(req);

                while (rs.next()) {
                    Reclamation p = new Reclamation();
                    p.setId_rec(rs.getInt(1));
                    p.setObjet(rs.getString("object"));
                    p.setTypeR(rs.getString("type"));
                    p.setDescriptionR(rs.getString("description"));
                    p.setDateR(rs.getString("date"));
                    reclamations.add(p);
                }
            } catch (SQLException var6) {
                System.out.println(var6.getMessage());
            }

            return reclamations;
        }

        public List<Reclamation> recupererUser(int idu) {
            List<Reclamation> reclamations = new ArrayList();
            user = us.getUserById(SessionManager.getInstance().getUser_id());
            idu= user.getUser_id();
            try {
                String req = "select * from reclamation where id_user='" + idu + "' order by date DESC";
                Statement st = this.cnx.createStatement();
                ResultSet rs = st.executeQuery(req);

                while (rs.next()) {
                    Reclamation p = new Reclamation();
                    p.setId_rec(rs.getInt(1));
                    p.setObjet(rs.getString("object"));
                    p.setTypeR(rs.getString("type"));
                    p.setDescriptionR(rs.getString("description"));
                    p.setDateR(rs.getString("date"));
                    reclamations.add(p);
                }
            } catch (SQLException var7) {
                System.out.println(var7.getMessage());
            }

            return reclamations;
        }

        public ObservableList<Reclamation> getall() {
            ObservableList<Reclamation> reclamations = FXCollections.observableArrayList();

            try {
                String req = "select * from reclamation";
                Statement st = this.cnx.createStatement();
                ResultSet rs = st.executeQuery(req);

                while (rs.next()) {
                    Reclamation p = new Reclamation();
                    p.setId_rec(rs.getInt(1));
                    p.setObjet(rs.getString("object"));
                    p.setTypeR(rs.getString("type"));
                    p.setDescriptionR(rs.getString("description"));
                    p.setDateR(rs.getString("date"));
                    p.setEtat(rs.getString("etat"));
                    reclamations.add(p);
                }
            } catch (SQLException var6) {
                System.out.println(var6.getMessage());
            }

            return reclamations;
        }

        public void traiter(Reclamation t) {
            try {
                String req = "update reclamation set etat=? where id_rec= ?";
                PreparedStatement ps = this.cnx.prepareStatement(req);
                ps.setString(1, "traité");
                ps.setInt(2, (int) t.getId_rec());
                ps.executeUpdate();
                System.out.println("Reclamation modifiée");
            } catch (SQLException var4) {
                System.out.println(var4.getMessage());
            }

        }

        public User getUserConnected(){
            User user=new User();
            try {
                String req="select * from user where status=1";
                ste = cnx.createStatement();
                rs = ste.executeQuery(req);

                if(rs.next()){
                    String path="http://127.0.0.1:8000/users/"+rs.getString("image_name");
                    ImageView img = new ImageView(new Image(path,true));
                    img.setFitHeight(50);
                    img.setFitWidth(50);
                    User u=new User (rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getString("tel"), rs.getString("password"),rs.getString("roles"),rs.getString("image_name"),rs.getInt("status"),img);
                    user=u;
                    System.out.println(user.toString());
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return user;
        }
    }

