/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpchat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 *
 * @author kevin
 */
public class Tpchat extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        InetAddress add = InetAddress.getByName("127.0.0.1");
        int port = 5000;
        String help = "Utilisation : java -jar target/multichat -0.0.1-SNAPSHOT.jar [OPTION]...\n"
                + "-a, --address=ADDR spécifier l'adresse IP\n"
                + "-d, --debug affiche les messages d'erreur\n"
                + "-h, --help afficher l'aide et quitter\n"
                + "-m, --multicast démarrer le client en mode multicast\n"
                + "-n, --nio configurer le serveur en mode NIO\n"
                + "-p, --port=PORT spécifier le port\n"
                + "-s, --server démarrer le server\n";

        boolean multi = false;
        boolean nio = false;
        boolean server = false;

        //System.out.println(help);
        LongOpt[] longopts = new LongOpt[7];
        StringBuffer sb = new StringBuffer();
        longopts[0] = new LongOpt(" addresse ", LongOpt.REQUIRED_ARGUMENT, sb, 'a');
        longopts[1] = new LongOpt("debug", LongOpt.NO_ARGUMENT, null, 'd');
        longopts[2] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');
        longopts[3] = new LongOpt("multicast", LongOpt.NO_ARGUMENT, null, 'm');
        longopts[4] = new LongOpt("nio", LongOpt.NO_ARGUMENT, null, 'n');
        longopts[5] = new LongOpt("port", LongOpt.NO_ARGUMENT, null, 'p');
        longopts[6] = new LongOpt("server", LongOpt.NO_ARGUMENT, null, 's');
        Getopt g = new Getopt("Multichat", args, "a:dhmnp:s", longopts);
        int c;
        while ((c = g.getopt()) != -1) {

            switch (c) {
                case 'a':
                    add = InetAddress.getByName(g.getOptarg());
                    break;
                case 'd':
                    System.out.println("debug pas encore implementé\n");
                    break;
                case 'h':
                    System.out.println(help);
                    System.exit(0);
                    break;
                case 'm':
                    multi = true;
                    //System.out.println("multicast pas encore implementé\n");
                    break;
                case 'n':
                    nio = true;

                    break;
                case 'p':
                    port = Integer.parseInt(g.getOptarg());
                    break;
                case 's':
                    server = true;
                    break;
                default:
                    System.out.println("Invalid Option");
                    System.out.println(help);
                    System.exit(0);
                    break;
            }
        }
        //d'abord test de l'option serveur
        if (server) {
            if (nio) {
                ServerNIO snio;
                snio = new ServerNIO(add, port);
                snio.start();
            } else {
                ServerChat sv;
                sv = new ServerChat(add, port);
                sv.start();

            }

        }
        else{
        if(multi){
        System.out.println("multicast pas encore implementé\n");
        
        }
        else{
        System.out.println("interface client pas encore implémenté\n");
        
        }
            
        
        
        }

        /*ServerChat sv;
         sv = new ServerChat(add ,5000);
         sv.start();
         ServerNIO snio;
         snio = new ServerNIO(add,5456);
         System.out.println("coucou");
         snio.start();*/
    }
}
