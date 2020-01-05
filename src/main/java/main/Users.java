/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;

/**
 *
 * @author Kenny
 */
public class Users {
    private HashMap<String, User> userCollection;
    
    public Users(){
        this.userCollection = new HashMap<String,User>();
    }
    
    // Login com limitação de acessos
    public synchronized boolean login(User u){
        // verificar se existe user
        if(userCollection.containsKey(u.getUserName())){
            User u2 = userCollection.get(u.getUserName());
            return (u.getPassword().equals(u2.getPassword()));            
        }
        return false;
    }
    
    // Registo com limitação de acessos
    public synchronized boolean registo(User u){
        // verificar se username esta em uso
        if(userCollection.containsKey(u.getUserName())){
            return false;
        }else{
            userCollection.put(u.getUserName(),u);
            return true;
        }
    }
    
}
