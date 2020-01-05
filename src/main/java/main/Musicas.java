/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Kenny
 */
public class Musicas {
    private Musica[] musicCollection;
    private Condition ocupado;
    private ReentrantLock lockMusicas;
    private int size;
    private int prox;
  
    
    public Musicas(int size){
        this.size = size;
        this.musicCollection = new Musica[size];
        for(int i = 0; i<this.size;i++){
            musicCollection[i]=null;
        }
        this.prox = 0;
        this.lockMusicas = new ReentrantLock();
        this.ocupado = this.lockMusicas.newCondition();
    }
    
    // dar Lock nesta operação
    public int inserirMusica(Musica m){
        lockMusicas.lock();
        int id = -1;
        try{
            id = 0;
            boolean flag = false;
            for(int i = 0; i<this.size && !flag ;i++){
                if (this.musicCollection[i]==null){
                    id = i;
                    flag = true;
                }else{
                    if (m.getTitulo().equals(this.musicCollection[i].getTitulo())){
                        return -1;
                    }
                }
            }
            m.setId(id);
            this.musicCollection[id] = m;
            if(id > this.size-2){
                resizeMusicas();
            }
            return id;
        } finally {
            this.lockMusicas.unlock();
        }
    }
    
    public void resizeMusicas(){
        Musica[] m = new Musica[size*2];
        
        for(int i = 0;i<size;i++){
            m[i] = this.musicCollection[i];
        }
        
        this.size*=2;
        this.musicCollection = m;
    }
    
    public ArrayList<Musica> porEtiqueta(String etiqueta){
        ArrayList<Musica> musicasPorEtiqueta = new ArrayList<Musica>();
        boolean flag = false;
        
        for(int i = 0;i<size ;i++){
            if(this.musicCollection[i]!= null){
                if(this.musicCollection[i].getEtiquetas().contains(etiqueta)){
                    flag = true;
                    musicasPorEtiqueta.add(this.musicCollection[i]);
                }
            }
        }
        if(flag) return musicasPorEtiqueta;
        return null;
    }
    
    public String toString(){
        StringBuilder s = new StringBuilder("Musicas\n");
        
        for(int i=0;i<size;i++){
            s.append(this.musicCollection[i]+"\n");
        }
        return s.toString();
    }
}
