/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpchat;

import java.net.Socket;

/**
 *
 * @author kevin
 */
public class ClientThread extends Thread implements Runnable {

    private Socket socket;
    private ServerChat server;

    
    public ClientThread (Socket sock, ServerChat serv) {
    this.socket = sock;
    this.server = serv;
   
}
    
}
