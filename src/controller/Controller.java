package controller;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.List;

import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.CloudService;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.User;

import com.dropbox.core.DbxException;

import exception.*;
import handler.EventhandlerDataScreen;
import handler.EventhandlerLogin;
import handler.I_EventhandlerDataScreen;
import handler.I_EventhandlerHomeScreen;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.AESEncryption;
import model.Data;
import model.ServerConnecter;
import thread.DownloadThread;
import thread.LoginThread;
import thread.RegisterThread;
import thread.UploadThread;

/**
 * @author      :   Sandro Guerotto
 * @version     :   2.0
 * @created     :   04.10.2016
 * @Project     :   cloud
 * @Package     :   controller
 * @LastUpdated :   04.11.2016 / by Sandro Guerotto
 * @Description :   used from gui handler to communicate with server and services
 */
public class Controller implements I_EventhandlerDataScreen, I_EventhandlerHomeScreen {

    private Dropbox dropbox;
    private ServerConnecter servconnection;
    private AESEncryption encryption;

    public Controller() {
        this.encryption = new AESEncryption();
    }
    
    
    /*SERVER DATA FUNCTIONS */

    /**
     * Tries to log in the user into the application and set him as applicationUser
     * @param username the name that the users identify himself
     * @param password the password from the user in clear-text
     */
    public void login(String username, String password, EventhandlerLogin handler) {
        LoginThread loginThread = new LoginThread(this.servconnection, username, password, handler);
        loginThread.start();
//        this.servconnection.loginApp(username, password);
    }//-logMeIn

    /**
     * Register a new user into the application if the username and email is unique
     * @param username the name that the users identify himself
     * @param email the email adress of a user to contact him
     * @param password the password from the user in clear-text
     */
    public void register(String username, String email, String password, EventhandlerLogin handler) throws RemoteException, UserExistException, EmailExistException {
        RegisterThread registerThread = new RegisterThread(this.servconnection, username, password, email, handler);
        registerThread.start();
//        this.servconnection.registerApp(username, email, password);
    }//-register

    /**
     * Loads all supported Services into the application
     */
    public void getAllClouds() throws LoadSupportedServicesException {
        this.servconnection.getAllServices();
    }//-getAllClouds

    /**
     * Sets the selected ServiceType
	 * @param servicetype  ServiceType is the ServiceType that we want to use
	 */
    public void setCloudTypeInUse(ServiceType servicetype) {
        this.servconnection.setActualServiceType(servicetype);
    }//-setCloudTypeInUse

    /**
     * Gets the actual ServiceType that is set
	 * @return ServiceType That Service that is in use
	 */
    public ServiceType getCloudTypeInUse() {
        return this.servconnection.getActualServiceType();
    }//-getCloudTypeInUse

    /**
     * Inserts a Service into the Database that allows the Connection between User and Service (Like Dropbox)
     * @param service is the ServiceType that we want to Save (Dropbox)
     * @param connection_name is the name of the connection ("My Connection-name")
     */
    public void saveCloudConnection(String connection_name, String usertoken) throws AddServiceFailException, NoUserLoggedInException {
        this.servconnection.addService(this.servconnection.getActualServiceType(), connection_name, usertoken);
    }//-saveCloudConnection

    /**
     * This method updates the name of a existing Service-connection
	 * @param service is the Connection between User and Service that we want to update
	 * @param newname is the new name/alias that we want to give this connection
	 */
    public void updateCloudConnection(CloudService service, String newname) throws UpdateServiceErrorException {
        this.servconnection.updateService(service, newname);
    }//-updateCloudConnection

    /**
     * Deletes a existing ServiceConnection of a User
     * @param service is the Service that we want to remove from db
     */
    public void deleteCloudConnection(CloudService service) throws DeleteServiceConnectionErrorException {
        this.servconnection.deleteService(service);
    }//-deleteCloudConnection

    /**
     * Good practice for Checking if a user is logged in
     * @return User This returns the Logged In User as a Object
     */
    public User getLoggedInUser() throws NoUserLoggedInException {
        return this.servconnection.getLoggedInUser();
    }//-getLoggedInUser

    /**
     * This function updates the user password for our application
     * @param oldPassword Is the old password that we need to make this update
     * @param newPassword Is the password that we want to set as current pw
     */
    public void setUserPw(String oldPassword, String newPassword) throws UpdateUserPwErrorException {
        this.servconnection.updateUserPw(oldPassword, newPassword);
    }//-setUserPW


    public void startServerConnecter() throws FailLoadingServicesException, ConnectionErrorException {
        this.servconnection = new ServerConnecter();
        this.servconnection.start_service();
    }
    /*END SERVER DATA FUNCTIONS*/

    public String getUsername() {
        return servconnection.getUser().getUsername();
    }


    //starter
    public void start(Stage stage) {
        StarterLogin starterLogin = new StarterLogin();
        starterLogin.start(stage, this);
    }

    /* GoTO Methode */
    public void gotoHome(Stage stage) {
        StarterHome starterHome = new StarterHome();
        starterHome.start(stage, this);
    }

    public void gotoData(Stage stage) throws AddServiceFailException, NoUserLoggedInException {
        dpxlogin("TEST LOGIN");
        StarterData starterData = new StarterData();
//    	starterData.setController(this);
        starterData.start(stage, this);

    }
    /* END GoTO Methode*/

    public void kill() {
        Platform.exit();
        System.exit(0);
    }

    @Override
    public ObservableList<Data> getAllData() throws NoFilesException, ErrorException{
    	try {
			dropbox.makearchives();
		} catch (DbxException e) {
			e.printStackTrace();
            throw new ErrorException();
		}
        if (dropbox.getarchives() == null) {
            throw new NoFilesException('i');
        } else {
            return dropbox.getarchives();
        }

    }

    @Override
    public void delete_data(ObservableList<Data> deletelist) throws DeleteException {
        for (Data data : deletelist){
            dropbox.deleteFile(data);
        }
    }

    /**
     * Creates for every entry in uploadlist a thread which uploads the file to the service
     * @param uploadlist contains all files to upload
     */
    @Override
    public void upload_data(List<File> uploadlist, EventhandlerDataScreen handler) {
        int i = 1;
        for (File file: uploadlist){
            UploadThread thread = new UploadThread(handler, this, file, uploadlist.size(), i);
            thread.start();
            i++;
        }

    }

    /**
     * Creates for every entry in downloadlist a thread which downloads the file from the service
     * @param downloadlist  list contains all data to download
     */
    @Override
    public void download_data(ObservableList<Data> downloadlist, EventhandlerDataScreen handler) {
        int i = 1;
        for (Data data: downloadlist){
            DownloadThread thread = new DownloadThread(handler, this, data, downloadlist.size(), i);
            thread.start();
            i++;
        }
    }

    /**
     * logout from current dropbox session
     */
    public void logout(){
        dropbox.logout();
    }
    /* END Data Screen Methode */

    /*  Home Screen Methode  */
    @Override
    public ServiceType[] getServices() throws LoadSupportedServicesException, NoServicesFoundException {
        if (servconnection.getAllServices() == null) {
            throw new NoServicesFoundException(ExceptionType.INFORMATION);
        } else {
            return servconnection.getAllServices();
        }
    }


    public String getLink(ServiceType serviceType) {
        String url = "";
        switch (serviceType.getName()) {
            case "Dropbox":
//                dropbox = new Dropbox();
                servconnection.setActualServiceType(serviceType);
                url = "https://www.dropbox.com/1/oauth2/authorize?locale=de_DE&client_id=4ib2r751sawik1x&response_type=code";
                break;
        }
        return url;
    }

    /* END Home Screen Methode */
    
    public void dpxlogin(String connection_name) throws AddServiceFailException, NoUserLoggedInException{
        try {
            dropbox = new Dropbox(this.getCloudTypeInUse());
            dropbox.firstlogin();
//            dropbox.makearchives();
            dropbox.getarchives();
            connection_name = "TEST LOGIN AMK"; //anpassen wenn autofunktion von Florian fertig
            this.saveCloudConnection(connection_name, dropbox.getAccesstoken());
            
        } catch (IOException | URISyntaxException | DbxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Dropbox getDropbox(){
        return this.dropbox;
    }
    public AESEncryption getEncryption(){
        return this.encryption;
    }
    public ServerConnecter getServconnection(){
        return this.servconnection;
    }
 }
