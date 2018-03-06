/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
 
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 *
 * @author manigeeth
 */
public class Send extends Thread{
    public Send(String name){
        super(name);
    }
    @Override
    public void run(){
        
        String mm=Thread.currentThread().getName();
        try{
        Client.ds1=new DatagramSocket();
        Client.ip = InetAddress.getByName("172.26.46.244");
        DatagramPacket dp= new DatagramPacket(mm.getBytes(), mm.length(), Client.ip, 5000);
        Client.ds1.send(dp);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}

class message implements Cloneable {

    int from;
    int type;
    String userid1, userid2, groupid, message, pwd, group_type;
    String pic;
    int gender;
    boolean ans;
   int port;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    message() {
        this.port=Client.port;
        this.from=0;

    }

}
