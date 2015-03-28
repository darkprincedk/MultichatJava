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
    HashMap<Socket, OutputStream> map;

    public ServerChat(InetAddress address, int p) {
        this.iaddress = address;
        this.port = p;
        this.map = new HashMap<>();

    }

    public void start() throws IOException {
        ServerSocket server = new ServerSocket(port, 10, iaddress);
        System.out.println("Server running ... " + port + " @ " + iaddress.getHostAddress());
        while (true) {
            Socket client = server.accept();
            client.getOutputStream().write("!!!!Server Chat!!!! \n!!!OPTION!!!\n/nick <yournickname> to set your nickname\n/close to close the stream\n".getBytes());
            map.put(client, client.getOutputStream());
            ClientThread clThread = new ClientThread(client, this);
            clThread.start();
        }

    }

    public synchronized void broadcast(Socket s, String msg) throws IOException {
        for (Map.Entry<Socket, OutputStream> entry : map.entrySet()) {
            if (!entry.getKey().equals(s)) {
                
                   
                        entry.getValue().write((msg + "\n").getBytes());
                    
               
            }
        }
    }

}
