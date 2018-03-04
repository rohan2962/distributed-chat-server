/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.google.gson.Gson;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Base64;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author vips
 */
public class Client {

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        class message {

            int from;
            int type;
            String userid1, userid2, groupid, message, pwd;
            String pic;
            int gender;
            boolean ans;

            message() throws FileNotFoundException, IOException {
                this.from = 0;
                //this.type=main_server.random.nextInt()%7;
                this.type = 2;
                this.userid1 = "v";
                this.userid2 = "ron";
                this.groupid = "friends";
                this.message = "hi";
                this.gender = 1;
                this.ans = false;
                this.pwd = "new";
                File file = new File("/home/vips/Desktop/zebra.jpg");

                BufferedImage originalImage = ImageIO.read(file);
                FileInputStream fileInputStreamReader = new FileInputStream(file);
                originalImage = Client.resize(originalImage, 400, 400);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(originalImage, "jpg", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();

                this.pic = new String(Base64.getEncoder().encode(imageInByte), "UTF-8");

            }
        }
        while(true){

        Scanner s = new Scanner(System.in);
        System.out.println("Enter Type");

        message m = new message();
        m.type = s.nextInt();

        switch (m.type) {
            case 1: {
                System.out.println("Enter user id");
                m.userid1 = s.next();
                System.out.println("Password");
                m.pwd = s.next();
                System.out.println("Gender");
                m.gender = s.nextInt();

                break;
            }
            case 2: {
                System.out.println("Enter user id");
                m.userid1 = s.next();
                System.out.println("Password");
                m.pwd = s.next();

                break;
            }
            case 3: {
                System.out.println("Enter user id");
                m.userid1 = s.next();
                System.out.println("Group  id");
                m.groupid = s.next();
                System.out.println("Message");
                m.message = s.next();

                break;
            }
            case 4: {
                System.out.println("Enter user id");
                m.userid1 = s.next();
                System.out.println("Enter user id 2");
                m.userid2 = s.next();

                break;
            }
            case 5: {
                System.out.println("Enter user id");
                m.userid1 = s.next();
                System.out.println("Group name");
                m.groupid = s.next();

                break;
            }
            case 6: {
                System.out.println("Enter user id");
                m.userid1 = s.next();
                System.out.println("Group name");
                m.groupid = s.next();
                break;
            }
            case 7: {
                System.out.println("Enter user id");
                m.userid1 = s.next();
                System.out.println("Group name");
                m.groupid = s.next();
                break;
            }
            case 8: {
                System.out.println("Enter user id");
                m.userid1 = s.next();
                
                break;
            }

        }
        Gson gson = new Gson();
        String str = gson.toJson(m);

        DatagramSocket ds = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("localhost");

        DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), ip, 5000);
        ds.send(dp);
        ds.close();

    }
    }

}
