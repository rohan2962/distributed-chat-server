/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author manigeeth
 */
public class Client extends Application {
    
    public static String userid,password;
    public static int gender;
    public static boolean loggedin = false;
    public static String pic;
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        
        //root.getChildren().add(btn);
        
        Parent root=FXMLLoader.load(getClass().getResource("signup.fxml"));
        Scene scene = new Scene(root, 600, 400);
        
        
        primaryStage.setTitle("Chat Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
