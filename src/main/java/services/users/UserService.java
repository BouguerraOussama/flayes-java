/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users;


import com.google.gson.Gson;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.users.User;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.MyDataBase;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author win10LIGHT
 */
public class UserService implements IService<User> {



    private Connection conn;
    private Connection connection;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    private ResultSet rc;




    public UserService() {
        conn = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void insert(User u) {
        String req = "insert into user (name,email,tel,password,roles,image_name,status) values ('" + u.getUsername() + "','" + u.getEmail() + "','" + u.getTel() + "','" + u.getPassword() + "','" + u.getRole() + "','" + u.getImage_name() + "','" + u.getStatus() + "')";
        try {
            ste = conn.createStatement();
            ste.executeUpdate(req);

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public String toJsonn(String role) {
        User user = new User()  ;
        Gson g = new Gson();
        role = g.toJson(user);
        System.out.println(role);
        return null;

    }

    public void insertUserPst(User u) {
        String req = "insert into user (name,email,tel,password,roles,image_name,status) values (?,?,?,?,?,?,?)";
        String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());

        try {

            pst = conn.prepareStatement(req);
            pst.setString(1, u.getUsername());
            pst.setString(2, u.getEmail());
            pst.setString(3, u.getTel());
            pst.setString(4, hashedPassword);
            pst.setString(5, u.getRole());
            pst.setString(6, u.getImage_name());
            pst.setInt(7, 0) ;
            pst.executeUpdate();

            System.out.println("ajoute"+u.getImage_name());

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    @Override
    public void delete(int id) {
        String req = "DELETE FROM user WHERE id=?";
        try {
            pst = conn.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }
    }


    public void updateRole(User u,String role) {
        try {

            String req = "update user set roles=? where id=?";
            pst = conn.prepareStatement(req);
            pst.setString(1, role);
            pst.setInt(2, u.getUser_id());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }
    }


    public void updatePassword(User u,String password) {
        try {

            String req = "update user set password=? where id=?";
            pst = conn.prepareStatement(req);
            pst.setString(1, password);
            pst.setInt(2, u.getUser_id());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }
    }



    public void updateProfil(User u){
        System.out.println("UserService update compte : "+u.toString2());
        try {
            String req = "update user set name=?,tel=?,password=?,image_name=? where id=?";
            pst = conn.prepareStatement(req);
            pst.setString(1,u.getUsername());
            pst.setString(2, u.getTel());
            pst.setString(3, u.getPassword());
            pst.setString(4, u.getImage_name());
            pst.setInt(5, u.getUser_id());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    @Override
    public List<User> read() {
        String req = "select * from user order by name ASC";
        List<User> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"),rs.getString("tel"), rs.getString("password"),  rs.getString("roles"), rs.getString("image_name"), rs.getInt("status")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }



    @Override
    public User readById(int id) {
        String req = "select * from user where  id="+id;
        User u = new User();

        try {

            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                String path="C:\\Users\\user\\Desktop\\Flayes-Flayes-offers - Copie\\public\\uploads\\images\\" +rs.getString("image_name");
                ImageView img = new ImageView(new Image(path,true));
                img.setFitHeight(50);
                img.setFitWidth(50);
                u.setUser_id(rs.getInt("id"));
                u.setUsername(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));

                u.setRole(rs.getString("roles"));
                u.setImage_name(rs.getString("image_name"));


            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }


    public User login(String email, String password) throws SQLException {
        String req = "select * from user where email='"+email+"' and password= '"+password+"'";
        try{
            ste =conn.createStatement();
            rs= ste.executeQuery(req);

            if(rs.next()){
                System.out.println("fel rs t5al");
                String query = "update user set status = 1 where id=?";
                String path="C:\\Users\\user\\Desktop\\Flayes-Flayes-offers - Copie\\public\\uploads\\images\\"+rs.getString("image_name");
                ImageView img = new ImageView(new Image(path,true));
                img.setFitHeight(50);
                img.setFitWidth(50);

                User u = new User (rs.getInt("id"), rs.getString("name"), rs.getString("email"),rs.getString("tel"), rs.getString("password"), rs.getString("roles"), rs.getString("image_name"), rs.getInt("status"),img);
                try{
                    pst = conn.prepareStatement(query);
                    pst.setInt(1, u.getUser_id());
                    pst.executeUpdate();

                }catch(SQLException ex){
                    Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("fel rs l user = "+u.toString());
                return u;
            }
            else {
                System.out.println("fel else mta3 login service");
                return null;
            }
        }catch(SQLException ex){
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }





    public void logOut(User u) throws SQLException{
        System.out.println("logoutService"+u.toString());
        String req = "update user set status=0 where id=?";
        try{
            pst = conn.prepareStatement(req);
            pst.setInt(1, u.getUser_id());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }

    }





    public List<User> SearchByName(String name){
        String req="SELECT * FROM user WHERE name LIKE '%"+name+"%'";
        List<User> list=new ArrayList<>();
        try {
            ste=conn.createStatement();
            rs= ste.executeQuery(req);
            while(rs.next()){
                String path="C:\\Users\\user\\Desktop\\Flayes-Flayes-offers - Copie\\public\\uploads\\images\\"+rs.getString("image_name");
                ImageView img = new ImageView(new Image(path,true));
                img.setFitHeight(50);
                img.setFitWidth(50);
                list.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"),rs.getString("tel"), rs.getString("password"),rs.getString("roles"), rs.getString("image_name"), rs.getInt("status"),img));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }


    public List<User> readWithImageView() {
        String req = "SELECT * FROM user";
        List<User> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);

            while (rs.next()) {
                String imagePath = "C:\\Users\\user\\Desktop\\Flayes-Flayes-offers - Copie\\public\\uploads\\images\\";
                String imageFileName = rs.getString("image_name"); // Fetch image filename from the result set
                String completeFilePath = imagePath + imageFileName;
                File file = new File(completeFilePath);
                Image image = new Image(file.toURI().toString());

                ImageView imageView = new ImageView(image); // Create a new ImageView for each image
                imageView.setFitHeight(50.0);
                imageView.setFitWidth(50.0);
                imageView.setPreserveRatio(true);
                imageView.setPickOnBounds(true);

                list.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("tel"), rs.getString("password"), rs.getString("roles"), rs.getString("image_name"), rs.getInt("status"), imageView));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public User readWithImage(int id) {
        String req = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String imagePath = " C:\\Users\\user\\Desktop\\Flayes-Flayes-offers - Copie\\public\\uploads\\images\\" + rs.getString("image_name");


                Image image = new Image(imagePath, true);
                ImageView imageView = new ImageView(image);
                // You can set fitHeight and fitWidth here if needed
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("tel"), rs.getString("password"), rs.getString("roles"), rs.getString("image_name"), rs.getInt("status"), imageView);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // Return null if no user is found
    }

    public void envoyerMail(String recepient) throws Exception
    {
        String email ="bennourines00@gmail.com";             //sender email
        String password ="thisislife***";

        System.out.println("Preparing to send email");

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols","TLSv1.2");




        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(email,password);
            }
        });
        Message message = prepareMessage(session, email, recepient);
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static Message prepareMessage(Session session, String email, String recepient){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recepient));
            message.setSubject("Bienvenue chez Fomrula1, Suivez-nous dans notre");
            String htmlCode ="<html lang=\"en\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                    "<head>\n" +
                    "<title></title>\n" +
                    "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n" +
                    "<meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\"/>\n" +
                    "<!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]-->\n" +
                    "<style>\n" +
                    "		* {\n" +
                    "			box-sizing: border-box;\n" +
                    "		}\n" +
                    "\n" +
                    "		body {\n" +
                    "			margin: 0;\n" +
                    "			padding: 0;\n" +
                    "		}\n" +
                    "\n" +
                    "		a[x-apple-data-detectors] {\n" +
                    "			color: inherit !important;\n" +
                    "			text-decoration: inherit !important;\n" +
                    "		}\n" +
                    "\n" +
                    "		#MessageViewBody a {\n" +
                    "			color: inherit;\n" +
                    "			text-decoration: none;\n" +
                    "		}\n" +
                    "\n" +
                    "		p {\n" +
                    "			line-height: inherit\n" +
                    "		}\n" +
                    "\n" +
                    "		@media (max-width:520px) {\n" +
                    "			.icons-inner {\n" +
                    "				text-align: center;\n" +
                    "			}\n" +
                    "\n" +
                    "			.icons-inner td {\n" +
                    "				margin: 0 auto;\n" +
                    "			}\n" +
                    "\n" +
                    "			.row-content {\n" +
                    "				width: 100% !important;\n" +
                    "			}\n" +
                    "\n" +
                    "			.column .border {\n" +
                    "				display: none;\n" +
                    "			}\n" +
                    "\n" +
                    "			.stack .column {\n" +
                    "				width: 100%;\n" +
                    "				display: block;\n" +
                    "			}\n" +
                    "		}\n" +
                    "	</style>\n" +
                    "</head>\n" +
                    "<body style=\"background-color: #FFFFFF; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 500px;\" width=\"500\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"heading_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td style=\"width:100%;text-align:center;\">\n" +
                    "<h1 style=\"margin: 0; color: #555555; font-size: 23px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; line-height: 120%; text-align: center; direction: ltr; font-weight: 700; letter-spacing: normal; margin-top: 0; margin-bottom: 0;\">Bienvenue chez Flayes&Flayes</h1>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<div style=\"font-family: sans-serif\">\n" +
                    "<div style=\"font-size: 14px; mso-line-height-alt: 16.8px; color: #555555; line-height: 1.2; font-family: Arial, Helvetica Neue, Helvetica, sans-serif;\">\n" +
                    "<p style=\"margin: 0; font-size: 14px;\">Votre compte est bien enregistré,</p>\n" +
                    "<p style=\"margin: 0; font-size: 14px;\">Merci de vous être inscrit $username.</p>\n" +
                    "</div>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-2\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 500px;\" width=\"500\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"icons_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: middle; color: #9d9d9d; font-family: inherit; font-size: 15px; padding-bottom: 5px; padding-top: 5px; text-align: center;\">\n" +
                    "<table cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: middle; text-align: center;\">\n" +
                    "<!--[if vml]><table align=\"left\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><![endif]-->\n" +
                    "<!--[if !vml]><!-->\n" +
                    "<table cellpadding=\"0\" cellspacing=\"0\" class=\"icons-inner\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block; margin-right: -4px; padding-left: 0px; padding-right: 0px;\">\n" +
                    "<!--<![endif]-->\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: middle; text-align: center; padding-top: 5px; padding-bottom: 5px; padding-left: 5px; padding-right: 6px;\"><a href=\"https://www.designedwithbee.com/\" style=\"text-decoration: none;\" target=\"_blank\"><img align=\"center\" alt=\"Designed with BEE\" class=\"icon\" height=\"32\" src=\"images/bee.png\" style=\"display: block; height: auto; margin: 0 auto; border: 0;\" width=\"34\"/></a></td>\n" +
                    "<td style=\"font-family: Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 15px; color: #9d9d9d; vertical-align: middle; letter-spacing: undefined; text-align: center;\"><a href=\"https://www.designedwithbee.com/\" style=\"color: #9d9d9d; text-decoration: none;\" target=\"_blank\">Designed with BEE</a></td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table><!-- End -->\n" +
                    "</body>\n" +
                    "</html>";
            message.setContent(htmlCode,"text/html");
            return message;
        }catch (Exception ex){
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }



    public  void excel() throws FileNotFoundException, IOException {

        String req = "select * from user" ;

        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);

            XSSFWorkbook workbook=new XSSFWorkbook();
            XSSFSheet sheet=workbook.createSheet("Users");

            XSSFRow row=sheet.createRow(0);
            row.createCell(0).setCellValue("ID");
            row.createCell(1).setCellValue("NAME");
            row.createCell(2).setCellValue("EMAIL");
            row.createCell(3).setCellValue("ROLES");


            int r=1;
            while(rs.next())
            {
                int user_id=rs.getInt("ID");
                String username=rs.getString("NAME");
                String email=rs.getString("EMAIL");
                String role=rs.getString("Roles");


                row=sheet.createRow(r++);

                row.createCell(0).setCellValue(user_id);
                row.createCell(1).setCellValue(username);
                row.createCell(2).setCellValue(email);
                row.createCell(3).setCellValue(role);


            }


            FileOutputStream fos=new FileOutputStream("C:C:\\Users\\user\\Desktop\\Nouveau dossier\\Nouveau dossier\\Flayes-Flayes-\\src\\main\\resources\\images\\Users.xlsx");
            workbook.write(fos);

            workbook.close();
            fos.close();


            System.out.println("Done!!!");
        }catch(SQLException ex){
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(User t) {
        System.out.println("ssssssssssss");
    }






//    private static class Userservice {
//
//        public Userservice() {
//        }
//    }

    //get user connected
    /*public User getUserConnected(){
        User user=new User();
        try {
            String req="select * from user where status=1";
            ste = conn.createStatement();
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

     */
    public User getUserById(int userId) {
        User user = null;
        try {
            String req = "SELECT * FROM user WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(req);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String path = "file:///" + "C://Users/user/Desktop/Flayes-Flayes-offers - Copie/public/uploads/images/" + resultSet.getString("image_name");
                ImageView img = new ImageView(new Image(path, true));
                img.setFitHeight(50);
                img.setFitWidth(50);
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("tel"),
                        resultSet.getString("password"),
                        resultSet.getString("roles"),
                        resultSet.getString("image_name"),
                        resultSet.getInt("status"),
                        img
                );
                System.out.println(user.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public List<User> readOrganisateur() {
        String req = "select * from user";
        System.out.println("req sql = " + req);

        List<User> list = new ArrayList<>();

        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                if(rs.getString("roles").equals("[\"ROLE_ORGANISATEUR\"]")){
                    list.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("roles")));
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean emailExists(String email) {
        String SQL = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, consider logging this exception or rethrowing it.
        }
        return false;
    }



    public String findResetTokenByEmail(String email) {
        String resetToken = null;
        String query = "SELECT reset_token FROM user WHERE email = ?";

        try (Connection connection = MyDataBase.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the email parameter
            preparedStatement.setString(1, email);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    resetToken = resultSet.getString("reset_token");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resetToken;
    }

    public boolean addToken(String email, String token) {
        // SQL query to update the reset_token column for the given email
        String updateQuery = "UPDATE user SET reset_token = ? WHERE email = ?";

        try (Connection connection = MyDataBase.getInstance().getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            // Set parameters for the update query
            updateStatement.setString(1, token);
            updateStatement.setString(2, email);

            // Execute the update query
            int rowsUpdated = updateStatement.executeUpdate();

            // Check if the update was successful
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return false if something went wrong
        return false;
    }

    public void mail(String receiverMail, String code) {
        String host = "iben46655@gmail.com";
        final String user = "iben46655@gmail.com";
        final String password = "hvgetegqlqdnzola";

        String to = receiverMail;

        Properties props = new Properties();
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        String subject = "Password Reset Code";
        String content = "<h1>Password Reset Code:</h1><p>Your password reset code is: " + code + "</p>";

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(content, "text/html");

            Transport.send(message);

            System.out.println("Message sent successfully via email.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void resetPassword(String email , String newPassword) {
        String SQL = "UPDATE user SET password = ? WHERE email = ?";

        try (Connection conn = MyDataBase.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("No password updated.");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
