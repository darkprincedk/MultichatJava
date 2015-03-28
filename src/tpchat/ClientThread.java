/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class ClientThread extends Thread implements Runnable {

    private Socket socket;
    private ServerChat server;
    private String instreamclient;
    private HashMap<Socket, String> nick;
    private boolean running;

    public ClientThread(Socket sock, ServerChat serv) {
        this.socket = sock;
        this.server = serv;
        this.nick = new HashMap<Socket, String>();
        this.instreamclient = "";
        this.running=true;
        
    }

    @Override
    public void run() {
        System.out.println("Client running ... " + socket);
        server.broadcast(socket, instreamclient);
        while (running && instreamclient!=null) {
            instreamclient = getStream(socket);
            if (instreamclient != null) {
                if (instreamclient.startsWith("/nick")) {
                    nick.put(socket, instreamclient.split(" ")[1]);
                }
                else if (instreamclient.startsWith("/close")){
                    try {
                        socket.close();
                        running=false;
                    } catch (IOException ex) {
                        Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                }
                else if (nick.get(socket) != null) {
                    server.broadcast(socket, "<" + nick.get(socket) + "> " + instreamclient);
                } else {
                    server.broadcast(socket, "<" + "???" + "> " + instreamclient);
                }
            }
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }

      

    }

    private String getStream(Socket c) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            return in.readLine();
        } catch (IOException e) {
        }
        return null;
    }

}
