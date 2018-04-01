/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rohan
 */
public class CreategroupController implements Initializable {

    @FXML
    private JFXTextField groupname;
    @FXML
    private JFXButton create;
    @FXML
    private Label grouperror;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        create.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (groupname.getText().length() != 0) {
                    Gson gson = new Gson();
                    message m = new message();
                    m.from = 0;
                    m.type = 5;
                    m.group_type = "Public";
                    m.groupid = groupname.getText();
                    m.userid1=Client.userid;
                    String s=gson.toJson(m);
                    Send se=new Send(s);
                    se.start();
                    Client.group_created=Client.couldnt_create=false;
                    Client.requested_group_create=true;
                    while(Client.group_created==false&&Client.couldnt_create==false){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(CreategroupController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                    if(Client.couldnt_create){
                        grouperror.setText("could not create");
                    }
                    else{
                        Client.grouplist.add(groupname.getText());
                        Client.group_info.put(groupname.getText(),","+ Client.userid);
                         Stage st=(Stage) grouperror.getScene().getWindow();
                        st.close();
                    }
                    

                }
            }

        });
    }
    

}
