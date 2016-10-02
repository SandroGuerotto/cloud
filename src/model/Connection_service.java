package model;

/**
 * @author 			:   Burim Cakolli
 * Turns coffee & pizza into Software
 * Created          :   01.10.2016
 * Project          :   cloud
 * Package          :   model
 * @version 		:   1.0
 * LastUpdated      :   01.10.2016
 * Description      :
 * @todo
 * 
 */
public class Connection_service {

    private int id;
    private String name;
    private String token;
    private Service service;

    /* Methods for Database-oriented things */
    public void save(){
        //@todo
    }//-save

    public void update(){
        //@todo
    }//-update


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
