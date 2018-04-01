/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
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
import javafx.util.Duration;

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
    public ScrollPane sidepane;
    public Accordion acc;
    private JFXDrawer drawer = new JFXDrawer();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int i, flag = 0;
        groupname.setText(Client.groupid);
        String s = groupname.getText();
        if (Client.mp.containsKey(s)) {
            s = Client.mp.get(s);
        }
        for (i = 0; i < Client.grouplist.size(); i++) {
            if (s.equals(Client.grouplist.get(i))) {
                flag = 1;
                break;
            }
        }
        System.out.println(flag);
        if (flag == 0) {
            groupinfo.setText("Join Group");
        } else {

            if (Client.group_mess.containsKey(groupname.getText())) {
                messages.setText(Client.group_mess.get(groupname.getText()));
            } else {
                message m = new message();
                m.from = 0;
                m.type = 8;
                m.userid1 = Client.userid;
                if (Client.mp.containsKey(groupname.getText())) {
                    m.groupid = Client.mp.get(groupname.getText());
                } else {
                    m.groupid = groupname.getText();
                }
                Gson gson = new Gson();
                Send send = new Send(gson.toJson(m));
                send.start();
            }
        }
        ScheduledService<Void> u = new ScheduledService<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() {
                        Platform.runLater(() -> {
                            updatemess();
                        });

                        return null;

                    }
                };

            }
        };
        u.setPeriod(Duration.millis(500));
        u.start();
        // TODO
        send.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (groupinfo.getText().equals("Group Info")&&new_message.getText().length() != 0) {
                    message me = new message();
                    me.from = 0;
                    me.type = 3;
                    me.userid1 = Client.userid;
                    me.message = Client.userid + ": " + new_message.getText() + "\n";
                    if (Client.mp.containsKey(groupname.getText())) {
                        me.groupid = Client.mp.get(groupname.getText());
                    } else {
                        me.groupid = groupname.getText();
                    }
                    Gson gson = new Gson();
                    Send send = new Send(gson.toJson(me));
                    send.start();
                    new_message.setText("");
                }
            }

        });

        //drawer.close();
        groupinfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (groupinfo.getText().equals("Join Group")) {
                    message m = new message();
                    m.from = 0;
                    m.type = 6;
                    m.group_type = "Public";
                    if (Client.mp.containsKey(groupname.getText())) {
                        m.groupid = Client.mp.get(groupname.getText());
                    } else {
                        m.groupid = groupname.getText();
                    }
                    m.userid1 = Client.userid;
                    Gson gson = new Gson();
                    Send s = new Send(gson.toJson(m));
                    s.start();
                    m = new message();
                    m.from = 0;
                    m.type = 7;
                    m.group_type = "Public";
                    if (Client.mp.containsKey(groupname.getText())) {
                        m.groupid = Client.mp.get(groupname.getText());
                    } else {
                        m.groupid = groupname.getText();
                    }
                    m.userid1 = Client.userid;
                    gson = new Gson();
                    s = new Send(gson.toJson(m));
                    s.start();
                    groupinfo.setText("Group Info");
                } else {
                    if (Client.group_info.containsKey(groupname.getText())) {

                        sidepane = new ScrollPane();
                        sidepane.setPrefSize(100, 400);
                        acc = new Accordion();
                        acc.setPrefSize(90, 390);
                        ScheduledService<Void> u = new ScheduledService<Void>() {
                            protected Task<Void> createTask() {
                                return new Task<Void>() {
                                    protected Void call() {
                                        Platform.runLater(() -> {
                                            updateinfo();
                                        });

                                        return null;

                                    }
                                };

                            }
                        };

                        u.setPeriod(Duration.millis(2000));
                        u.start();
                        //drawer.getChildren().add(acc);
                        drawer.setSidePane(sidepane);
                        //drawer.setAlignment(Pos.TOP_RIGHT);
                        drawer.setPrefSize(100, 400);

                        borderpane.setRight(drawer);
                        if (drawer.isShown()) {
                            drawer.close();
                        } else {
                            drawer.open();
                        }
                    } else {
                        message m = new message();
                        m.from = 0;
                        m.type = 7;
                        m.groupid = groupname.getText();
                        m.userid1 = Client.userid;
                        Gson gson = new Gson();
                        Send s = new Send(gson.toJson(m));
                        s.start();
                    }
                    //drawer.open();
                }

            }

        });
        //drawer.setSidePane(sidePane);
    }

    void updatemess() {
        if (Client.group_mess.containsKey(groupname.getText()) && !messages.getText().equals(Client.group_mess.containsKey(groupname.getText()))) {

            messages.setText(Client.group_mess.get(groupname.getText()));
        }

    }

    void updateinfo() {
        String grp = groupname.getText();
        if (Client.group_info.containsKey(grp)) {

            StringTokenizer st = new StringTokenizer(Client.group_info.get(grp).substring(1), ",");
            ArrayList<String> arr = new ArrayList();
            while (st.hasMoreTokens()) {
                arr.add(st.nextToken());
            }
            TitledPane[] tp = new TitledPane[arr.size()];
            for (int i = 0; i < arr.size(); i++) {
                tp[i] = new TitledPane();
                tp[i].setText(arr.get(i));
            }
            acc.getPanes().clear();
            acc.getPanes().addAll(tp);

            sidepane.setContent(acc);
        }
    }

}
