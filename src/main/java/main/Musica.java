/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;

/**
 *
 * @author Kenny
 */
public class Musica {
    private String titulo;
    private String interprete;
    private int ano;
    private int id;
    private boolean usada;
    private ArrayList<String> etiquetas;
    private int descarregamentos;
    private byte[] conteudo;
    
    public Musica (String titulo, String interprete, int ano,byte[] conteudo, ArrayList<String> etiquetas){
        this.titulo = titulo;
        this.interprete = interprete;
        this.ano = ano;
        this.id = 0;
        this.conteudo = conteudo;
        this.usada = false;
        this.etiquetas = etiquetas;
        this.descarregamentos = 0;
    }

    public String getTitulo() {
        return titulo;
    }

    public ArrayList<String> getEtiquetas() {
        return etiquetas;
    }

    public String getInterprete() {
        return interprete;
    }

    public int getAno() {
        return ano;
    }

    public int getId() {
        return id;
    }

    public int getDescarregamentos() {
        return descarregamentos;
    }

    public byte[] getConteudo() {
        return conteudo;
    }
    
    public boolean isUsada() {
        return usada;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEtiquetas(ArrayList<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsada(boolean usada) {
        this.usada = usada;
    }
    
    public synchronized void incrementaDescarregamentos (){
        this.descarregamentos++;
    }
    
    public String toString(){
        StringBuilder s = new StringBuilder("Musica\n");
    
        s.append("Titulo: "+this.titulo+"\n");
        s.append("Interprete: "+this.interprete+"\n");
        s.append("Ano: "+this.ano+"\n");
        s.append("Id: "+this.id+"\n");
        s.append("Etiquetas: \n");
        for(int i = 0; i<this.etiquetas.size(); i++){
            s.append(etiquetas.get(i)+";\n");
        }
        if(this.isUsada()) s.append("A ser usada\n");
        else s.append("Livre\n");
        
        return s.toString();
    }
    
}
