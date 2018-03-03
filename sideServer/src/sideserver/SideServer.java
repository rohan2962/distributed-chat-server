/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sideserver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.stream.Stream;
import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Blob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;

/**
 *
 * @author rohan
 */
public class SideServer {

    /**
     * @param args the command line arguments
     */
    static volatile String mess = "";
    static volatile boolean send = false;
    static volatile Scanner scan = new Scanner(System.in);
    static DatagramSocket ds, ds1;
    static InetAddress ip;
    static byte[] buf = new byte[65520];
    static DatagramPacket dp = new DatagramPacket(buf, 65520);
    static connect c;

    public static void main(String[] args) throws ClassNotFoundException, SQLException, SocketException, UnknownHostException, FileNotFoundException {
        // TODO code application logic here
        ds = new DatagramSocket(3000);
        ds1 = new DatagramSocket(5000);
        ip = InetAddress.getByName("172.26.46.244");
        c = new connect();
        PreparedStatement stmt;
        stmt = c.con.prepareStatement("select * from user_details");
        ResultSet rs;
        rs = stmt.executeQuery();
        int i = 1;
        while (rs.next()) {
            rs.absolute(i);
            i++;
            String name = rs.getString(1);
            String password = rs.getString(2);
            int gender = rs.getInt(4);
            System.out.println(name + " " + password + " " + gender);

        }

        /*String name = scan.next();
        String message = scan.next();
        Gson gson = new Gson();
        message m = new message(),m1;
        m.userid1 = name;
        m.message = message;
        
        String jsonString = gson.toJson(m);
        System.out.println(jsonString);
        m1 = gson.fromJson(jsonString, message.class);
        System.out.println(m1.userid1+" "+m1.message);*/
        Read t1 = new Read();
        t1.start();
        Send t2 = new Send();
        t2.start();

    }

}

class message {

    int from;
    int type;
    String userid1, userid2, groupid, message, pwd;
    String pic;
    int gender;
    boolean ans;

    message() {

    }
}

class Read extends Thread {

    public void run() {

        while (true) {
            try {
                SideServer.ds.receive(SideServer.dp);
                SideServer.mess = new String(SideServer.dp.getData(), 0, SideServer.dp.getLength());
                if (SideServer.mess.length() != 0) {
                    //System.out.println(db_connect.message);
                    SideServer.send = true;
                    //System.out.println(db_connect.send);
                }
            } catch (IOException ex) {
                Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}

class Send extends Thread {

    public void run() {
        PreparedStatement stmt;
        ResultSet rs;
        while (true) {

            if (SideServer.send) {
                try {

                    String mess1 = SideServer.mess;
                    String mm = "";
                    SideServer.mess = "";
                    message m1, m2 = new message();
                    Gson gson = new Gson();
                    System.out.println(mess1);
                    m1 = gson.fromJson(mess1, message.class);
                    System.out.println(m1.type);

                    switch (m1.type) {
                        case 1: {

                            stmt = SideServer.c.con.prepareStatement("select userid from user_details where userid = ? ");
                            stmt.setString(1, m1.userid1);
                            rs = stmt.executeQuery();
                            if (rs.next()) {
                                m2.from = 1;
                                m2.ans = false;

                            } else {

                                stmt = SideServer.c.con.prepareStatement("insert into user_details values(?,?,?,?)");
                                stmt.setString(1, m1.userid1);
                                stmt.setString(2, m1.pwd);

                                stmt.setString(3, m1.pic);
                                stmt.setInt(4, m1.gender);
                                stmt.executeUpdate();
                                m2.from = 1;
                                m2.ans = true;
                                /*BufferedImage image = null;
                                byte[] imageByte;

                                BASE64Decoder decoder = new BASE64Decoder();
                                imageByte = decoder.decodeBuffer(m1.pic);
                                ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                                image = ImageIO.read(bis);
                                bis.close();

                                File outputfile = new File("image.jpg");
                                ImageIO.write(image, "jpg", outputfile);
                                 */

                            }
                            break;
                        }
                        case 2: {
                            stmt = SideServer.c.con.prepareStatement("select userid,gender,pic from user_details where userid = ? and pwd = ?");
                            stmt.setString(1, m1.userid1);
                            stmt.setString(2, m1.pwd);
                            rs = stmt.executeQuery();
                            if (rs.next()) {
                                rs.absolute(1);
                                m2.gender=rs.getInt(2);
                                m2.pic=rs.getString(3);
                                m2.from = 1;
                                m2.ans = true;
                                m2.userid1=m1.userid1;
                                //System.out.println(m2.gender);
                                //System.out.println(m2.pic);
                                System.out.println("success");

                            } else {

                                System.out.println("failure");
                                m2.from = 1;
                                
                                
                                m2.ans = false;
                            }
                            break;
                        }
                        case 3: {
                            break;
                        }
                        case 4: {
                            break;
                        }
                        case 5: {
                            break;
                        }
                        case 6: {
                            break;
                        }
                        case 7: {
                            break;
                        }
                        case 8: {
                            break;
                        }

                    }
                    mm = gson.toJson(m2);
                    DatagramPacket dp = new DatagramPacket(mm.getBytes(), mm.length(), SideServer.ip, 3000);
                    SideServer.ds1.send(dp);
                } catch (IOException ex) {
                    Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
                }
                SideServer.send = false;
            }
        }

    }
}
