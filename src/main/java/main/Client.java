/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.Menus.linha;
import static main.Menus.loggedMenu;
import static main.Menus.mainMenu;
import static main.Menus.maisMusica;

/**
 *
 * @author Kenny
 */
public class Client {
    private Socket clientSocket;
    private int port;
    private String host;
    private BufferedReader stdIn;
    private BufferedReader input;
    private PrintWriter output;
    private OutputStream outputStream;
    private InputStream inputStream;
    
    public Client (int port, String host){
        this.port = port;
        this.host = host;
        
    }
    
    public void initializing () throws InterruptedException{
        try {
            this.clientSocket = new Socket(this.host, this.port);
            this.inputStream = clientSocket.getInputStream();
            this.outputStream = clientSocket.getOutputStream();    
            this.stdIn = new BufferedReader(new InputStreamReader(System.in));
            this.input = new BufferedReader (new InputStreamReader(this.inputStream));
            this.output = new PrintWriter(this.outputStream);
            initialMenu(); 
        } catch (IOException ex) {
            System.out.println("Erro ao inicializar");
        }
    }
    
    public String login(){
        String success = "";
        String username = "";
        String password = "";
       try{
           while(!(success.equals("t"))){
                linha();
                System.out.println("Insira Username para Login: ");
                username = this.stdIn.readLine();
                this.output.println(username);
                this.output.flush();
                linha();
                System.out.println("Insira Password para Login: ");
                password = this.stdIn.readLine();
                this.output.println(password);
                this.output.flush();
                success = this.input.readLine();
                if(success.equals("t")){
                    linha();
                    System.out.println("Login efectuado com sucesso!");
                    linha();
                }else{
                    linha();
                    System.out.println("Insucesso no Login, tente de novo");
                    linha();
                }
           }
        }catch(IOException e){
            System.out.println("Erro inputClient");
        }
       return (username);
    }
    
    public String registo(){
        String success = "";
        String username = "";
        String password = "";
        try{
            while(!(success.equals("t"))){
                linha();
                System.out.println("Insira UserName para registo: ");
                username = this.stdIn.readLine();
                this.output.println(username);
                this.output.flush();
                linha();
                System.out.println("Insira Password para registo: ");
                password = this.stdIn.readLine();
                this.output.println(password);
                this.output.flush();
                success = this.input.readLine();
                if(success.equals("t")){
                    linha();
                    System.out.println("Registo efectuado com sucesso!");
                    linha();
                }else{
                    linha();
                    System.out.println("Insucesso no Registo, tente novamente");
                    linha();
                }
            }
        }catch(IOException e){
            System.out.println("Erro Registo");
        }
        return username;
    }
    
    public void upload(){
        String next="";
        String titulo = "";
        String interprete = "";
        String etiqueta = "";
        String path = "";
        byte [] conteudo;
        int ano = 0;
        int id = 0;
        try{
            while(!(next.equals("n"))){
                linha();
                System.out.println("Insira Titulo da Musica: ");
                titulo = this.stdIn.readLine();
                this.output.println(titulo);
                this.output.flush();
                linha();
                System.out.println("Insira Interprete da Musica: ");
                interprete = this.stdIn.readLine();
                this.output.println(interprete);
                this.output.flush();
                linha();
                System.out.println("Insira Ano da Musica: ");
                ano = Integer.parseInt(this.stdIn.readLine());
                this.output.println(Integer.toString(ano));
                this.output.flush();
                linha();
                System.out.println("Insira o path do ficheiro de musica");
                path = this.stdIn.readLine();
                conteudo = pathToBytes(path);
                System.out.println("CONTEUDO received: "+conteudo.length);
                this.output.println(Integer.toString(conteudo.length));
                this.output.flush();
                this.outputStream.write(conteudo);
                this.outputStream.flush();
                linha();
                System.out.println("Insira etiquetas uma a uma, insira done quando terminar");
                while(!(etiqueta.equals("done"))){
                    etiqueta = this.stdIn.readLine();
                    this.output.println(etiqueta);
                    this.output.flush();
                }  
                id = Integer.parseInt(this.input.readLine());
                if(id<0){
                    linha();
                    System.out.println("Musica já existe. Tente Novamente.");
                    linha();
                    next = "y";
                    this.output.println(next);
                    this.output.flush();
                }else{
                    linha();
                    System.out.println("Musica inserida com sucesso com o ID "+id+"!");
                    linha();
                    maisMusica();
                    next = this.stdIn.readLine();
                    this.output.println(next);
                    this.output.flush();
                }
                conteudo = null;
            }
        }catch(IOException ex){
            System.out.println("erro no upload");
        }  
    }
    
    public void search(){
        String etiqueta;
        String success = "";
        String m = "";
        try{
            while(!(success.equals("t"))){
                linha();                
                System.out.println("Insira etiqueta de musicas: ");
                etiqueta = this.stdIn.readLine();
                this.output.println(etiqueta);
                this.output.flush();
                success = this.input.readLine();
                if(success.equals("t")){
                    linha();
                    while(!(m.equals("f"))){
                        m = this.input.readLine();
                        System.out.println(m);
                        m = "reset";
                    }
                }else{
                    linha();
                    System.out.println("Etiqueta não encontrada. Tente Novamente");
                }
            }
        }catch(IOException ex){
            System.out.println("erro no upload");
        }  
    }
    
    public void download(){
        System.out.println(":Download:");
    }
    
    /*public byte[] pathToBytes(String path) throws IOException{
        byte[] conteudo;
        File f = new File(path);
        ByteArrayOutputStream filestream = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(filestream);
            out.writeObject(f);
            out.flush();
            byte[] bArray = filestream.toByteArray();
            return bArray;
        } 
        finally{
            try{
                filestream.close();
            } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }*/
    public byte[] pathToBytes (String path) throws IOException{
        File file = new File(path);
        FileInputStream fis = null;
        byte[] conteudo = new byte[(int) file.length()];
        try{
            fis = new FileInputStream(file);
            fis.read(conteudo);
            fis.close();             
        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
        return conteudo;
    }
    
    public void initialMenu(){
        String stdinput = "";
        String rec = "";
        mainMenu();
        try {
            while(!(stdinput.equals("3"))){
                stdinput = stdIn.readLine();
                this.output.println(stdinput);
                this.output.flush();
                switch(stdinput){
                    case "1":
                        System.out.println(":::::::::::::: REGISTO :::::::::::::::::");
                        rec = registo();
                        menu(rec);
                        break;
                    case "2":
                        System.out.println(":::::::::::::: LOGIN :::::::::::::::::");
                        rec = login();
                        menu(rec);
                        break;
                    case "3":
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Escolha Invalida");
                        break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        close();  
    }
    
    public void menu(String username){
        String stdinput = "";
        loggedMenu(username);
        try {
            while(!(stdinput.equals("4"))){
                stdinput = stdIn.readLine();
                this.output.println(stdinput);
                this.output.flush();
                switch(stdinput){
                    case "1":
                        System.out.println(":::::::::::::: PUBLICAR MUSICA :::::::::::::::::");
                        upload();
                        loggedMenu(username);
                        break;
                    case "2":
                        System.out.println(":::::::::::::: PROCURAR MUSICA :::::::::::::::::");
                        search();
                        //loggedMenu(username);
                        break;
                    case "3":
                        download();
                        break;
                    case "4":
                        System.out.println("Exiting...");
                        mainMenu();
                        break;
                    default:
                        System.out.println("Escolha invalida");
                        break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close(){
        try {
            this.clientSocket.shutdownInput();
            this.clientSocket.shutdownOutput();
            this.clientSocket.close();
            this.stdIn.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public static void main(String[] args) throws InterruptedException{
        Client client = new Client(12345,"localhost");
        client.initializing();
    }
}
