/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpchat;

import java.net.InetAddress;

/**
 *
 * @author kevin
 */
public class ServerNIO extends AbstractMultichatServer {
    private InetAddress iaddress;
    private int port;
   
    public ServerNIO(InetAddress address, int p) {
        super(address,p);
        
    }
   
}
