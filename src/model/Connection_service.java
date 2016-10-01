package model;

/**
 * Turns coffee & pizza into Software
 *
 * @author :   Burim
 *         Created          :   01.10.2016
 *         Project          :   cloud
 *         Package          :   model
 * @version :   1.0
 *          LastUpdated      :   01.10.2016
 *          Description      :
 *          Contents all User-Informations that we need in our application.
 *          The informations comes from a Microsoft Server
 */
public class Connection_service {

    private int id;
    private String name;
    private String token;
    private Service service;

    public Connection_service(int id, String name, String token, Service service) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.service = service;
    }//-Standard Constructor

    /* Methods for Database-oriented things */

    /* Getter/Setter #Standard */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}//-class
