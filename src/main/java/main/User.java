/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 * @author Kenny
 */
public class User {

    private String userName;
    private String password;
    
    public User (String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    } 
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String toString() {
        return "User{" + "userName=" + userName + ", password=" + password + '}';
    }
    
    public boolean equals(Object o){
        if (this == o) return true;
        if ((o==null) || (this.getClass() != o.getClass())) return false;
        User a = (User) o;
        return ((this.userName.equals(a.getUserName())) && (this.password.equals(a.getPassword())));
    }
}
