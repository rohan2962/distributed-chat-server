/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author rohan
 */
public class GroupController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private Label groupname;
    @FXML
    private JFXButton groupinfo;
    @FXML
    private TextArea messages;
    @FXML
    private TextField new_message;
    @FXML
    private JFXDrawer drawer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        groupname.setText(Client.groupid);
        //drawer.close();
        groupinfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (groupinfo.getText().equals("Join Group")) {

                } else {
                    ScrollPane sidepane = new ScrollPane();
                    Accordion acc = new Accordion();
                    TitledPane tp = new TitledPane();
                    tp.setText("hi");
                    acc.getPanes().add(tp);
                    sidepane.setContent(acc);
                    drawer.setSidePane(sidepane);
                    drawer.setPrefSize(150, 400);
                    BorderPane root=(BorderPane)borderpane.getParent();
                    root.setRight(drawer);
                    drawer.open();
                }

            }

        });
        //drawer.setSidePane(sidePane);
    }

}
