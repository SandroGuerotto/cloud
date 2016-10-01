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
public class Service {

    private int id;
    private String name;
    private String appKey;
    private String appSecret;

    public Service(int id, String name, String appKey, String appSecret) {
        this.id = id;
        this.name = name;
        this.appKey = appKey;
        this.appSecret = appSecret;
    }//-Standard Constructor

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}//-class
