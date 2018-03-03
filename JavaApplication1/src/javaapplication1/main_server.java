/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import com.google.gson.Gson;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.random;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Blob;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;

/**
 *
 * @author vips
 */
public class main_server {

    Scanner scan = new Scanner(System.in);
    String map[] = new String[2];
    public static volatile message ob;
    public static volatile Random random = new Random();

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    main_server() {
        //ob=new message();
        map[0] = "172.26.44.218";
        map[1] = "localhost";
        Read t1 = new Read();
        t1.start();
        System.out.println("ff");
        Send t2 = new Send();
        t2.start();

    }

    class message {

        int from;
        int type;
        String userid1, userid2, groupid, message,pwd;
        String pic;
        int gender;
        boolean ans;

        message() throws FileNotFoundException, IOException {
            this.from = 0;
            //this.type=main_server.random.nextInt()%7;
            this.type = 1;
            this.userid1 = "v";
            this.userid2 = "ron";
            this.groupid = "friends";
            this.message = "hi";
            this.gender = 1;
            this.ans = false;
            this.pwd = "new";
            File file = new File("/home/vips/Desktop/zebra.jpg");

            BufferedImage originalImage = ImageIO.read(file);
            FileInputStream fileInputStreamReader =new FileInputStream(file);
            originalImage=main_server.resize(originalImage, 400, 400);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            
            this.pic = new String(Base64.getEncoder().encode(imageInByte), "UTF-8");
            /*this.piclen=this.pic.length();
            
            BufferedImage image = null;
                               byte[] imageByte;

                               BASE64Decoder decoder = new BASE64Decoder();
                               imageByte = decoder.decodeBuffer(this.pic);
                               ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                               image = ImageIO.read(bis);
                               bis.close();

// write the image to a file
                               File outputfile = new File("image.jpg");
                               ImageIO.write(image, "jpg", outputfile);*/

        }
    }

    public class Read extends Thread {

        public void run() {

            while (true) {
                db_connect.message = scan.next();
                if (db_connect.message.length() != 0) {
                    try {
                        main_server.ob = new message();

                        System.out.println("type      " + main_server.ob.type);
                        db_connect.send = true;
                        //System.out.println(db_connect.send);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(main_server.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(main_server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }

    public class Send extends Thread {

        public void run() {

            while (true) {
                if (db_connect.message.length() != 0) {
                }
                if (db_connect.send) {
                    System.out.println(db_connect.send);
                    int num = 31;
                    int hash = 0;
                    for (int i = 0; i < db_connect.message.length(); i++) {
                        hash = hash + num * db_connect.message.charAt(i);
                    }
                    hash = hash % 2;

                    try {

                        DatagramSocket ds = new DatagramSocket();
                        Gson gson = new Gson();
                        System.out.println(main_server.ob.pic.length());
                        String picc=main_server.ob.pic;
                        //main_server.ob.pic="";
                        String str = gson.toJson(main_server.ob);
                        System.out.println(str);

                        //m.from=
                        InetAddress ip = InetAddress.getByName(map[hash]);

                        DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), ip, 3000);
                        ds.send(dp);
                        ds.close();
                        /*for(int i=0;i<picc.length();i++)
                        {
                            String s=picc.substring(i, i+500);
                        }*/
                        System.out.println(hash + " " + db_connect.message);
                    } catch (SocketException ex) {
                        Logger.getLogger(main_server.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(main_server.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(main_server.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    db_connect.send = false;
                    db_connect.message = "";
                }
            }

        }
    }

}
