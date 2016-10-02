package model;

import java.util.ArrayList;

/**
 * @author          :   Burim Cakolli
 * Turns coffee & pizza into Software
 * Created          :   01.10.2016
 * Project          :   cloud
 * Package          :   model
 * @version         :   1.0
 * LastUpdated      :   01.10.2016
 * Description      :
 * Contents all User-Informations that we need in our application.
 * The informations comes from a Microsoft Server
 */
public class User {

    private int id;
    private String username;
    private String password;
    private String private_key;
    private String public_key;
    private ArrayList<Connection_service> user_services;


    public User(int id, String username, String password, String private_key, String public_key, ArrayList<Connection_service> user_services) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.private_key = private_key;
        this.public_key = public_key;
        this.user_services = user_services;
    }//-Standard-Constructor

    /* Methods for Database-oriented things */
    public void save(){
        //@todo
    }//-save

    public void update(){
       //@todo
    }//-update

    /* Getter/Setter #Standards */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public ArrayList<Connection_service> getUser_services() {
        return user_services;
    }

    public void setUser_services(ArrayList<Connection_service> user_services) {
        this.user_services = user_services;
    }

}//-class
