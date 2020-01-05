/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.Menus.linha;

/**
 *
 * @author Kenny
 */
public class ServerWorker implements Runnable {
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Users users;
    private Musicas musicas;
    
    public ServerWorker(Socket clientSocket, Users users, Musicas musicas){
        try {
            this.clientSocket = clientSocket;
            this.users = users;
            this.musicas = musicas;
            this.inputStream = clientSocket.getInputStream();
            this.outputStream = clientSocket.getOutputStream();
            this.output = new PrintWriter(this.outputStream);
            this.input = new BufferedReader (new InputStreamReader(this.inputStream));
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    // Fecha sockets
    public void close(){
        try {
            this.clientSocket.shutdownInput();
            this.clientSocket.shutdownOutput();
            this.clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Processo de Login do lado do Servidor, através de worker dedicado
    public void login(){
        String usernameInput = "";
        String passwordInput = "";
        String succesS = "";
        boolean success = false;
        try{
            while(success == false){
                usernameInput = this.input.readLine();
                System.out.println("USERNAME received: "+usernameInput);
                passwordInput = this.input.readLine();
                System.out.println("PASSWORD received: "+passwordInput);
                User user = new User(usernameInput,passwordInput);
                success = this.users.login(user);
                if(success == true){
                    succesS = "t";
                }else{
                    succesS = "f";
                }
                this.output.println(succesS);
                this.output.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void registo(){
        String usernameInput = "";
        String passwordInput = "";
        String succesS;
        boolean success = false;
        try {
            while(success == false){
                usernameInput = input.readLine();
                System.out.println("USERNAME received: "+usernameInput);
                passwordInput = input.readLine();
                System.out.println("PASSWORD received: "+passwordInput);
                User user = new User(usernameInput,passwordInput);
                success = this.users.registo(user);
                if(success == true){
                    succesS = "t";
                }else{
                    succesS = "f";
                }
                this.output.println(succesS);
                this.output.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void upload(){
        String next = "";
        String titulo = "";
        String interprete = "";
        String etiqueta = "";
        int tamanhoBytes = 0;
        int id = 0;
        byte [] conteudo;
        ArrayList<String> etiquetas = new ArrayList<String>();
        int ano = 0;
        try{
            while(!(next.equals("n"))){
                titulo = input.readLine();
                System.out.println("TITULO received: "+titulo);
                interprete = input.readLine();
                System.out.println("INTERPRETE received: "+interprete);
                ano = Integer.parseInt(input.readLine());
                System.out.println("ANO received: "+ano);
                tamanhoBytes = Integer.parseInt(input.readLine());
                //conteudo = this.inputStream.readNBytes(tamanhoBytes);
                /**
                 *  Codigo TIF
                 */
                conteudo = new byte[tamanhoBytes];
                int c;
                int i=0;
                while((c=this.inputStream.read(conteudo))!=-1)
                    System.out.println("Bytes lidos"+c);
                //
                System.out.println("CONTEUDO received: "+ conteudo.length);
                while(!(etiqueta.equals("done"))){
                    etiqueta = this.input.readLine();
                    if(!(etiqueta.equals("done"))){
                       etiquetas.add(etiqueta); 
                    }
                    System.out.println("ETIQUETA received: "+etiqueta);
                }
                linha();
                Musica musica = new Musica(titulo,interprete,ano,conteudo,etiquetas);
                id = musicas.inserirMusica(musica);
                this.output.println(Integer.toString(id));
                this.output.flush();
                next = input.readLine();
                conteudo = null;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void search(){
        String etiqueta ="";
        int i = 0;
        String success = "c";
        ArrayList<Musica> musicasPorEtiqueta;
        try{
            while(!(success.equals("t"))){
                etiqueta = this.input.readLine();
                System.out.println("ETIQUETA received: "+etiqueta);
                musicasPorEtiqueta = this.musicas.porEtiqueta(etiqueta);
                if(musicasPorEtiqueta == null){
                    this.output.println(success);
                    this.output.flush();
                }else{
                    success = "t";
                    this.output.println(success);
                    this.output.flush();
                    while(i<musicasPorEtiqueta.size()){
                        this.output.println(musicasPorEtiqueta.get(i).toString());
                        this.output.flush();
                        i++;
                    }
                    this.output.println("f");
                    this.output.flush();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void download(){
        System.out.println(":Download:");
    }

    public void menu(){
        String clientInput = "";
        try{
            while(!(clientInput.equals("4"))){
                clientInput = input.readLine();
                System.out.println("Input recebido " +clientInput);
                switch(clientInput){
                    case "1":
                        System.out.println(": publicar musica :");
                        upload();
                        break;
                    case "2":
                        System.out.println(": procurar musica :");
                        search();
                        break;
                    case "3":
                        download();
                        break;
                    case "4":
                        System.out.println("Exit");
                        break;
                    default:
                        System.out.println("Escolha invalida");
                        break;
                }
            }
        }catch(IOException e){
            System.out.println("Erro inputClient");
        }
    }

    @Override
    public void run() {
        String clientInput = "";
        try{
        while(!(clientInput.equals("3"))){
            clientInput = input.readLine();
            System.out.println("Input recebido " +clientInput);
            switch(clientInput){
                    case "1":
                        System.out.println(": REGISTO :");
                        registo();
                        menu();
                        break;
                    case "2":
                        System.out.println(": LOGIN :");
                        login();
                        menu();
                        break;
                    case "3":
                        System.out.println("Exit");
                        break;
                    default:
                        System.out.println("Escolha invalida");
                        break;
                }
        }
        }catch(IOException e){
            System.out.println("Erro inputClient");
        }
        close();
    }
    
}
