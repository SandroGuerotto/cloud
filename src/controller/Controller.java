package controller;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.CloudService;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.User;

import com.dropbox.core.DbxException;

import exception.AddServiceFailException;
import exception.ConnectionErrorException;
import exception.DeleteException;
import exception.DeleteServiceConnectionErrorException;
import exception.DownloadException;
import exception.EmailExistException;
import exception.FailLoadingServicesException;
import exception.LoadSupportedServicesException;
import exception.LoginFailedException;
import exception.NoFilesException;
import exception.NoUserLoggedInException;
import exception.UpdateServiceErrorException;
import exception.UpdateUserPwErrorException;
import exception.UploadException;
import exception.UserExistException;
import exception.UsernameHasToBeFilledOutException;
import handler.I_EventhandlerDataScreen;
import handler.I_EventhandlerHomeScreen;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Data;
import model.ServerConnecter;

/**
 * @author          :   Sandro Guerotto
 * Created          :   04.10.2016
 * Project          :   cloud
 * Package          :   controller
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   Allgemeiner Kontroller f�r das Programm
 */
public class Controller implements I_EventhandlerDataScreen, I_EventhandlerHomeScreen {

	private String[] args;
	private Dropbox dropbox;
	private ServerConnecter servconnection;
	
    public Controller(String[] args) throws ConnectionErrorException, ServiceException, FailLoadingServicesException{
    	this.args = args;
    	this.servconnection = new ServerConnecter();
    }
    
    
    /*SERVER DATA FUNCTIONS PS: Add doc! */
    public void login(String username, String password) throws LoginFailedException{
    	this.servconnection.loginApp(username, password);
    }//-logMeIn
    
    public void register(String username, String email, String password) throws RemoteException, UserExistException, EmailExistException{
    		this.servconnection.registerApp(username, email, password);
    }//-register
   
    public void getAllClouds() throws LoadSupportedServicesException{
	   this.servconnection.getAllServices();
    }//-getAllClouds
   
    public void setCloudTypeInUse(ServiceType servicetype){
	   this.servconnection.setActualServiceType(servicetype);
    }//-setCloudTypeInUse
        
    public ServiceType getCloudTypeInUse(){
    	return this.servconnection.getActualServiceType();
    }//-getCloudTypeInUse
   
    public void saveCloudConnection(String connection_name, String usertoken) throws AddServiceFailException, NoUserLoggedInException{
	   this.servconnection.addService(this.servconnection.getActualServiceType(), connection_name, usertoken);
    }//-saveCloudConnection
   
    public void updateCloudConnection(CloudService service, String newname) throws UpdateServiceErrorException{
    	this.servconnection.updateService(service, newname);
    }//-updateCloudConnection
    
    public void deleteCloudConnection(CloudService service) throws DeleteServiceConnectionErrorException{
    	this.servconnection.deleteService(service);
    }//-deleteCloudConnection
    
    public User getLoggedInUser() throws NoUserLoggedInException{
    	return this.servconnection.getLoggedInUser();
    }//-getLoggedInUser
    
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
    
    public void gotoHome(Stage stage){
    	StarterHome starterHome = new StarterHome();
    	starterHome.setController(this);
    	starterHome.start(stage);
    }
    
    public void gotoData(Stage stage){
    	dpxtestlogin();
    	StarterData starterData = new StarterData();
    	starterData.setController(this);
    		starterData.start(stage);
    	
    }
    
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
    public void upload_data(List<File> uploadlist) throws UploadException, ConnectionErrorException {
    	// TODO convert file list to single files
    	// dropbox.uploadFile(null);
    }

    @Override
    public void download_data(ObservableList<Data> downloadlist) throws DownloadException, ConnectionErrorException {
    	for(Data data: downloadlist){
    		dropbox.downloadFile(data.getdata_name());
    	}
    }

    @Override
    public void getLogin(String Username) {
    	
    }

	@Override
	public void getDropboxAPI(String api) {
		// TODO Auto-generated method stub
		
	}
	
	private void dpxtestlogin(){
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
