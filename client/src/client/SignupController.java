/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author manigeeth
 */
public class SignupController implements Initializable {

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXComboBox<Label> gender;
    @FXML
    private JFXTextField pic_location;
    @FXML
    private JFXButton browse_pic;
    @FXML
    private JFXButton signup_submit;
    @FXML
    private Label signup_error;
    @FXML
    private JFXTextField login_username;
    @FXML
    private JFXPasswordField login_password;
    @FXML
    private JFXButton login_submit;
    @FXML
    private Label login_error;
    FileChooser ch = new FileChooser();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gender.getItems().add(new Label("Male"));
        gender.getItems().add(new Label("Female"));
        signup_error.setText("");
        login_error.setText("");
        signup_submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                signup_error.setText("");
                Client.userid = username.getText();
                Client.password = password.getText();
                if(username.getText().equals("")){
                    signup_error.setText("Enter username");
                }
                else if(password.getText().equals("")){
                    signup_error.setText("Enter password");
                }
                else if(gender.getValue()==null){
                    signup_error.setText("Enter gender");
                }
                else{
                    if (gender.getValue().getText().equals("Male")) {
                    Client.gender = 1;

                } else {
                    Client.gender = 0;
                }
                    System.out.println("signup");
                }
                ///////send this data 
            }

        });
        browse_pic.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Stage primaryStage = (Stage) signup_submit.getScene().getWindow();
                    File file = ch.showOpenDialog(primaryStage);
                    pic_location.setText(file.getName());
                    BufferedImage originalImage = ImageIO.read(file);
                    FileInputStream fileInputStreamReader = new FileInputStream(file);
                    originalImage = resize(originalImage, 400, 400);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(originalImage, "jpg", baos);
                    baos.flush();
                    byte[] imageInByte = baos.toByteArray();
                    baos.close();
                    
                    Client.pic = new String(Base64.getEncoder().encode(imageInByte), "UTF-8");
                    //System.out.println(Client.pic);
                } catch (Exception ex) {
                    //Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        login_submit.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                login_error.setText("");
                Client.userid=login_username.getText();
                Client.password=login_password.getText();
                ///send this data
                if(login_username.getText().equals("")){
                    login_error.setText("Enter username");
                }
                else if(login_password.getText().equals("")){
                    login_error.setText("Enter password");
                }
                else{
                    System.out.println("login");
                }
            }
            
        });
    }

}
