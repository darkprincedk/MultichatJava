/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpchat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class AbstractMultichatServer implements MultichatServer {

    private InetAddress iaddress;
    private int port;
    private ServerSocketChannel ssc;
    HashMap <SocketChannel,OutputStream> outsocket;
    HashMap <SocketChannel,String> nickname;

    public AbstractMultichatServer(InetAddress iaddress, int p) {
        this.iaddress = iaddress;
        this.port = p;
        outsocket = new HashMap<>();
        nickname = new HashMap<>();
    }

    @Override
    public void start() throws IOException {

        ssc = ServerSocketChannel.open();
        Selector selector = Selector.open();
        System.out.println("address" + port);
        ssc.bind(new InetSocketAddress(iaddress.getHostAddress(), port));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            
            //System.out.println("on accepte");
            int readyChannels = selector.select();
            //System.out.println("ReadyChannels:" + readyChannels);
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                //System.out.println("on accepte2");
                SelectionKey key = keyIterator.next();
                
                if(!key.isValid())
                {
                key.cancel();
                key.channel().close();
                }
                
                if (key.isAcceptable()) {
                    SocketChannel socketChannel = ssc.accept();

                    socketChannel.configureBlocking(false);

                    String address = (new StringBuilder(socketChannel.socket().getInetAddress().toString())).append(":").append(socketChannel.socket().getPort()).toString();
                    System.out.println("New connection accepted from " + address);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    outsocket.put(socketChannel,socketChannel.socket().getOutputStream());
                    nickname.put(socketChannel, "???:");
                    
                }
                ByteBuffer bbuf = ByteBuffer.allocate(8192);
                if (key.isReadable() ) {
                  
                    ((SocketChannel) key.channel()).read(bbuf);
                    
                    Charset charset = Charset.defaultCharset();
                    bbuf.flip();
                    CharBuffer cbuf = charset.decode(bbuf);
                    String message = cbuf.toString();
                    if(message.startsWith("/nick"))
                    {
                    nickname.put((SocketChannel) key.channel(), message.split(" ")[1].split("\\n")[0]+":");
                    System.out.println(nickname);
                    bbuf.compact();
                    }
                    else
                    {
                    
                    System.out.println(cbuf);
                    broadcast((SocketChannel) key.channel(), message);
                    bbuf.compact();
                    }
                }
                
                
               
                keyIterator.remove();
                

            }

        }
        
        

    }
    public synchronized void broadcast(SocketChannel s, String msg) {
        outsocket.entrySet().stream().filter((entry) -> (!entry.getKey().equals(s))).forEach((entry) -> {
            try {
                if(!msg.isEmpty())
                    
                    entry.getKey().write( ByteBuffer.wrap((nickname.get(s)+msg).getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }       });
}
    
}
