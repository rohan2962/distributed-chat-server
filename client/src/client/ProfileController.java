/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author rohan
 */
public class ProfileController implements Initializable {

    @FXML
    private ImageView profile_pic;
    @FXML
    private Label username;
    @FXML
    private Label gender;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        username.setText(Client.slot_user);
        System.out.println(username.getText());
        username.setVisible(true);
        gender.setVisible(true);
        if(Client.slot_gender==1){
            gender.setText("Male");
        }
        else{
            gender.setText("Female");
        }
        
    }    
    
}
