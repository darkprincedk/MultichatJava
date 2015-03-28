/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpchat;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author kevin
 */
public class ServerChat {
    private InetAddress iaddress;
    private int port;
    HashMap <Socket,OutputStream> mess;
    
    public ServerChat(InetAddress address, int p) {
this.iaddress = address;
this.port = p;
this.mess = new HashMap<>();

}
    public void start() throws IOException{
    ServerSocket server = new ServerSocket(port,10,iaddress);
    System.out.println("Server running ... "+port+" @ "+iaddress.getHostAddress());
while (true) {
Socket client = server.accept();
client.getOutputStream().write("Welcome :D \n".getBytes());
mess.put(client, client.getOutputStream());
//ClientHandler ch = new ClientHandler(client, this);
//ch.start();
}
    
    }
    
    
    
    
    
}
