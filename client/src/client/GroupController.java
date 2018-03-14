/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    private JFXButton send;
    @FXML
    private TextArea messages;
    @FXML
    private TextField new_message;

    private JFXDrawer drawer = new JFXDrawer();

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
                    //drawer.getChildren().add(acc);
                    drawer.setSidePane(sidepane);
                    //drawer.setAlignment(Pos.TOP_RIGHT);
                    drawer.setPrefSize(150, 325);
                    drawer.setDirection(DrawerDirection.LEFT);
                    Parent x = borderpane.getParent().getParent().getParent();
                    while (x.getClass() != BorderPane.class) {
                        x = x.getParent();
                    }
                    BorderPane root = (BorderPane) x;
                    //BorderPane root=(BorderPane)borderpane.getParent();
                    root.setRight(drawer);
                    if (drawer.isShown()) {
                        drawer.close();
                    } else {
                        drawer.open();
                    }
                    //drawer.open();
                }

            }

        });
        //drawer.setSidePane(sidePane);
    }

}
