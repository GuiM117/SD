/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kenny
 */
public class Server {
    
    private int port;
    private Users users;
    private Musicas musicas;
    private ServerSocket serverSocket;
    
    public Server (int port){
        this.port = port;
        this.users = new Users();
        this.musicas = new Musicas(10);
    }
    
    // Inicialiazar o Servidor
    public void serverOn (){
        try{
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("Server Initiating...");
            while(true){
                    Socket client = serverSocket.accept();
                    System.out.println("Connection accepted");
                    ServerWorker sw = new ServerWorker(client, users,musicas);
                    new Thread(sw).start();
            }
        }catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main ( String[] args){
        Server server = new Server(12345);
        server.serverOn();
    }
}
