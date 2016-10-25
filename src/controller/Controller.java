package controller;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import exception.*;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.CloudService;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.User;

import com.dropbox.core.DbxException;

import handler.I_EventhandlerDataScreen;
import handler.I_EventhandlerHomeScreen;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Data;
import model.ServerConnecter;

/**
 * @author           :   Sandro Guerotto
 * @created          :   04.10.2016
 * @Project          :   cloud
 * @Package          :   controller
 * @version          :   1.0
 * @LastUpdated      :
 * @Description      :   Allgemeiner Kontroller für das Programm
 */
public class Controller implements I_EventhandlerDataScreen, I_EventhandlerHomeScreen {

	private String[] args;
	private Dropbox dropbox;
	private ServerConnecter servconnection;
	
    public Controller(String[] args) throws ConnectionErrorException, ServiceException, FailLoadingServicesException{
    	this.args = args;
    	this.servconnection = new ServerConnecter();
    }
    
    
    /*SERVER DATA FUNCTIONS */
    
	/*
	 * Tries to log in the user into the application and set him as applicationUser
	 * @param username the name that the users identify himself
	 * @param password the password from the user in clear-text
	 */
    public void login(String username, String password) throws LoginFailedException{
    	this.servconnection.loginApp(username, password);
    }//-logMeIn
    
	/*
	 * Register a new user into the application if the username and email is unique
	 * @param username the name that the users identify himself
	 * @param email the email adress of a user to contact him
	 * @param password the password from the user in clear-text
	 */
    public void register(String username, String email, String password) throws RemoteException, UserExistException, EmailExistException{
    		this.servconnection.registerApp(username, email, password);
    }//-register
   
	/*
	 * Loads all supported Services into the application
	 */
    public void getAllClouds() throws LoadSupportedServicesException{
	   this.servconnection.getAllServices();
    }//-getAllClouds
   
    /*
	 * Sets the selected ServiceType
	 * @param ServiceType service is the ServiceType that we want to use
	 */
    public void setCloudTypeInUse(ServiceType servicetype){
	   this.servconnection.setActualServiceType(servicetype);
    }//-setCloudTypeInUse
        
    /*
	 * Gets the actual ServiceType that is set
	 * @return ServiceType That Service that is in use
	 */
    public ServiceType getCloudTypeInUse(){
    	return this.servconnection.getActualServiceType();
    }//-getCloudTypeInUse
   
	/*
	 * Inserts a Service into the Database that allows the Connection between User and Service (Like Dropbox)
	 * @param service is the ServiceType that we want to Save (Dropbox)
	 * @param name is the name of the connection ("My Connection-name")
	 */
    public void saveCloudConnection(String connection_name, String usertoken) throws AddServiceFailException, NoUserLoggedInException{
	   this.servconnection.addService(this.servconnection.getActualServiceType(), connection_name, usertoken);
    }//-saveCloudConnection
   
    /*
	 * This method updates the name of a existing Service-connection
	 * @param service is the Connection between User and Service that we want to update
	 * @param name is the new name/alias that we want to give this connection 
	 */
    public void updateCloudConnection(CloudService service, String newname) throws UpdateServiceErrorException{
    	this.servconnection.updateService(service, newname);
    }//-updateCloudConnection
    
	/*
	 * Deletes a existing ServiceConnection of a User
	 * @param CloudService is the Service that we want to remove from db
	 */    
    public void deleteCloudConnection(CloudService service) throws DeleteServiceConnectionErrorException{
    	this.servconnection.deleteService(service);
    }//-deleteCloudConnection
    
	/*
	 * Good practice for Checking if a user is logged in 
	 * @return User This returns the Logged In User as a Object
	 */
    public User getLoggedInUser() throws NoUserLoggedInException{
    	return this.servconnection.getLoggedInUser();
    }//-getLoggedInUser
    
	/*
	 * This function updates the user password for our application
	 * @param user Is the User that we want to update
	 * @param oldPassword Is the old password that we need to make this update
	 * @param newPassword Is the password that we want to set as current pw
	 */
    public void setUserPw(String oldPassword, String newPassword) throws UpdateUserPwErrorException{
    	this.servconnection.updateUserPw(oldPassword, newPassword);
    }//-setUserPW
    
    /*END SERVER DATA FUNCTIONS*/

	public String getUsername(){
		return servconnection.getUser().getUsername();
	}



    //starter
    public void start(){
        StarterLogin starterLogin = new StarterLogin();
        starterLogin.show(args, this);
    }

    /* GoTO Methode */
    public void gotoHome(Stage stage){
    	StarterHome starterHome = new StarterHome();
//    	starterHome.setController(this);
//    	starterHome.start(stage);
        starterHome.start(stage, this);
    }
    
    public void gotoData(Stage stage){
    	dpxtestlogin();
    	StarterData starterData = new StarterData();
//    	starterData.setController(this);
    		starterData.start(stage, this);
    	
    }
    /* END GoTO Methode*/
    
    public void kill(){
    	Platform.exit();
    	System.exit(0);
    }

    @Override
    public ObservableList<Data> getAllData() throws NoFilesException {

    	if (dropbox.getarchives() == null) {
			throw new NoFilesException('i');
		}else{
			return dropbox.getarchives();
		}
        
    }

    @Override
    public void delete_data(ObservableList<Data> deletelist) throws DeleteException, ConnectionErrorException {
    	
    }

    @Override
    public void upload_data(List<File> uploadlist) throws UploadException, ConnectionErrorException, IOException, DbxException {
        for(File file: uploadlist){
            dropbox.uploadFile(file.getAbsolutePath());
        }

        dropbox.makearchives();
    }

    @Override
    public void download_data(ObservableList<Data> downloadlist) throws DownloadException, ConnectionErrorException {
    	for(Data data: downloadlist){
    		dropbox.downloadFile(data.getdata_name());
    	}
    }


    /*  Home Screen Methode  */
    @Override
    public ServiceType[] getServices() throws LoadSupportedServicesException, NoServicesFoundException {
        if (servconnection.getAllServices() == null){
            throw new NoServicesFoundException('i');
        }else{
            return servconnection.getAllServices();
        }
    }


    public String getLink(ServiceType serviceType)  {
        String url = "";
        switch (serviceType.getName()){
            case "Dropbox":
//                dropbox = new Dropbox();
            	servconnection.setActualServiceType(serviceType);
                url =  "https://www.dropbox.com/1/oauth2/authorize?locale=de_DE&client_id=4ib2r751sawik1x&response_type=code";
                break;
        }
        return url;
    }

    /* END Home Screen Methode */

    // TODO Test Methoden
	public void dpxtestlogin(){
    	try {
			dropbox = new Dropbox();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
