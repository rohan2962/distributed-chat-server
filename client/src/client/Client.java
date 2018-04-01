/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import static client.SignupController.resize;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXTabPane;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;

/**
 *
 * @author manigeeth
 */
public class Client extends Application {

    public static String userid, password, slot_user, groupid;
    public static int gender, slot_gender;
    public static boolean loggedin = false;
    public static String pic, slot_pic;
    //public static ArrayList<String> grouplist = new ArrayList<>();
    public static volatile ArrayList<String> grouplist = new ArrayList<>();
    public static volatile HashMap<String, String> mp = new HashMap<>();
    public static volatile HashMap<String, String> revmp = new HashMap<>();
    public static volatile HashMap<String, String> group_mess = new HashMap<>();
    public static volatile HashMap<String, String> group_info = new HashMap<>();
    public static ObservableList<String> groups = FXCollections.observableArrayList();
    public static DatagramSocket ds, ds1;
    public static InetAddress ip;
    public static byte[] buf = new byte[65520];
    public static DatagramPacket dp = new DatagramPacket(buf, 65520);
    public static volatile boolean message_received = false;
    public static volatile boolean group_created = false;
    public static volatile boolean couldnt_create = false;
    public static volatile boolean requested_group_create = false;
    public static volatile message recv_m;
    public static int port = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {

        //root.getChildren().add(btn);
        /*grouplist.add("group1");
        grouplist.add("group2");
        grouplist.add("group3");
        slot_user="rohan";
        slot_gender=1;*/
        File file = new File("/home/rohan/Desktop/Earth.jpg");

        BufferedImage originalImage = ImageIO.read(file);
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        originalImage = resize(originalImage, 75, 75);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        pic = new String(Base64.getEncoder().encode(imageInByte), "UTF-8");
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        BorderPane bp = new BorderPane(root);
        Scene scene = new Scene(bp, 600, 400);
        Read t1 = new Read();
        t1.start();
        primaryStage.setTitle("Chat Server");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}

class Read extends Thread {

    public void run() {
        try {
            DatagramSocket ds = new DatagramSocket();
            Client.port = ds.getLocalPort();
            System.out.println(Client.port);
            byte[] buf = new byte[65520];
            DatagramPacket dp = new DatagramPacket(buf, 65520);
            while (true) {
                ds.receive(dp);
                InetAddress ipp = dp.getAddress();
                String str = new String(dp.getData(), 0, dp.getLength());
                message m = new message();
                Gson gson = new Gson();
                m = gson.fromJson(str, message.class);
                Client.message_received = true;
                Client.recv_m = (message) m.clone();
                AddGroup();
                System.out.println(str);
            }
        } catch (SocketException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void AddGroup() {
        try {
            if (Client.message_received == true && Client.recv_m.type == 5) {
                String s = Client.recv_m.groupid;
                Client.message_received = false;
                if (Client.requested_group_create && Client.recv_m.group_type.equals("Public") && Client.recv_m.userid1.equals(Client.userid)) {
                    if (Client.recv_m.ans) {
                        Client.group_created = true;
                    } else {
                        Client.couldnt_create = true;
                    }
                }
                String s1 = s;
                if (s.contains("<->")) {
                    s = s.replaceAll("<->", "");
                    if (s.contains(Client.userid)) {
                        s = s.replaceAll(Client.userid, "");
                        Client.mp.put(s, s1);
                        Client.revmp.put(s1, s);
                    }
                }
                Client.groups.add(s);
            } else if (Client.message_received == true && (Client.recv_m.type == 3 || Client.recv_m.type == 8)) {
                String s = Client.recv_m.groupid;
                if (Client.revmp.containsKey(s)) {
                    s = Client.revmp.get(s);
                }
                String mess;
                if (Client.group_mess.containsKey(s)) {
                    mess = Client.group_mess.get(s);
                } else {
                    mess = "";
                }
                mess += Client.recv_m.message;
                Client.group_mess.put(s, mess);
                Client.message_received = false;

            } else if (Client.message_received == true && Client.recv_m.type == 6 ) {
                String s = Client.recv_m.groupid;
                
                String mess;
                if (Client.group_mess.containsKey(s)) {
                    mess = Client.group_mess.get(s);
                } else {
                    mess = "";
                }
                mess += Client.recv_m.message;
                Client.group_mess.put(s, mess);
                String s1=Client.recv_m.userid1;
                if (Client.group_info.containsKey(s)) {
                    mess = Client.group_mess.get(s)+",";
                } else {
                    mess = "";
                }
                mess += s1;
                Client.group_mess.put(s, mess);
                Client.message_received = false;

            }
            else if (Client.message_received == true && Client.recv_m.type == 7 ) {
                String s = Client.recv_m.groupid;
                
                if(Client.revmp.containsKey(s)){
                    s=Client.revmp.get(s);
                }
                Client.group_info.put(s, Client.recv_m.message);
                Client.message_received = false;

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
