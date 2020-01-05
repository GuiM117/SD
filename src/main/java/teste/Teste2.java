/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kenny
 */
public class Teste2 implements Serializable{
    
    public byte[] musicToBytes(File f) throws IOException{
        ByteArrayOutputStream filestream = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(filestream);
            out.writeObject(this);
            out.flush();
            byte[] bArray = filestream.toByteArray();
            return bArray;
        } 
        finally{
            try{
                filestream.close();
            } catch (IOException ex) {
            Logger.getLogger(Teste2.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    public static void main(String[] args) throws IOException{
        String path = "C:\\Users\\Kenny\\Desktop\\Storage\\UM\\SD\\Projecto\\Musicas\\Musica1";
        String content = "";
        Teste2 t = new Teste2();
        File f = new File(path);
        byte[] conteudo = t.musicToBytes(f);
        content = Base64.getEncoder().encodeToString(conteudo);
        System.out.println(content);
        System.out.println("tamanho "+conteudo.length);
        byte[] conteudo2 = content.getBytes();
        System.out.println("tamanho "+conteudo2.length);
    }
    
}
