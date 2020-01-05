/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Kenny
 */
public class Menus {
    public static void mainMenu(){
        System.out.println(":::::::::::::: MENU :::::::::::::::::"+"\n");
        System.out.println("1 - Registo");
        System.out.println("2 - Login");
        System.out.println("3 - Sair");
    }
    public static void loggedMenu(String username){
        System.out.println(":::::::::::::: WELCOME "+username+" :::::::::::::::::");
        System.out.println("1 - Publicar música");
        System.out.println("2 - Procurar música");
        System.out.println("3 - Descarregar música");
        System.out.println("4 - Sair");
    }
    public static void linha(){
        System.out.println("--------------------------------------------");
    }
    
}
