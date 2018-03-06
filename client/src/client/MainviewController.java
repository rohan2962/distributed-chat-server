/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;

/**
 * FXML Controller class
 *
 * @author rohan
 */
public class MainviewController implements Initializable {

    @FXML
    private Accordion grouplist;
    @FXML
    private BorderPane groupdata;
    @FXML
    private ImageView profile_pic;
    @FXML
    private Label username;
    @FXML
    private Label gender;
    ObservableList<String> groups = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Image im;
            BufferedImage image = null;
            byte[] imageByte;
            
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(Client.pic);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
            
            File outputfile = new File("image.jpg");
            ImageIO.write(image, "jpg", outputfile);
            im = new Image(outputfile.toURI().toString());
            profile_pic.setImage(im);
            username.setText(Client.slot_user);
            System.out.println(username.getText());
            username.setVisible(true);
            gender.setVisible(true);
            if (Client.slot_gender == 1) {
                gender.setText("Male");
            } else {
                gender.setText("Female");
            }
            groups.addAll(Client.grouplist);
            TitledPane[] tp = new TitledPane[groups.size()];
            for (int i = 0; i < groups.size(); i++) {
                tp[i] = new TitledPane();
                //tp[i].setAnimated(false);
                //tp[i].setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                //tp[i].lookup("arrow").setVisible(false);
                tp[i].setPrefSize(200, 75);
                tp[i].setText(groups.get(i));
                
            }
            grouplist.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
                @Override
                public void changed(ObservableValue<? extends TitledPane> observable, TitledPane oldValue, TitledPane newValue) {
                    if (newValue != null) {
                        try {
                            Client.groupid = grouplist.getExpandedPane().getText();
                            System.out.println(Client.groupid);
                            Node root=FXMLLoader.load(getClass().getResource("group.fxml"));
                            groupdata.setCenter(root);
                        } catch (IOException ex) {
                            Logger.getLogger(MainviewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            grouplist.getPanes().addAll(tp);
            
        } catch (IOException ex) {
            Logger.getLogger(MainviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
