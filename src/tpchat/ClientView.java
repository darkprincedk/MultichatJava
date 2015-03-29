/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpchat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author kevin
 */
public class ClientView extends Application {

    private InetAddress iaddress;
    private int port;
    private SocketChannel sock;

    ClientView(InetAddress address, int p) {
        this.iaddress = address;
        this.port = p;
    }

    @Override
    public void start(Stage stage) throws Exception {

        sock = SocketChannel.open(new InetSocketAddress(iaddress, port));

        Scene scene = new Scene(new Group());
        stage.setTitle("Application Chat");

        TextField mess = new TextField();

        Button sendb = new Button("Send");
        TextArea window = new TextArea("Welcome!");
        Label lab = new Label("Your message: ");
        window.setEditable(false);
        window.setPrefSize(600, 350);
        HBox hbox = new HBox();
        VBox vbox = new VBox();
        mess.setPrefSize(400, 35);
        hbox.getChildren().addAll(lab, mess, sendb);
        hbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(window, hbox);

        scene = new Scene(vbox, 600, 400);

        stage.setScene(scene);
        stage.show();
        sendb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    sock.socket().getOutputStream().write(mess.getText().getBytes());
                } catch (IOException ex) {
                    Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                    window.setText(window.getText() +"\n" + mess.getText() );
                    mess.clear();
            }
        });
        
        
        
        
    }
}
