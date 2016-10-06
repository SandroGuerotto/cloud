package controller;


import exception.ConnectionErrorException;
import exception.DeleteException;
import exception.DownloadException;
import exception.UploadException;
import handler.I_EventhandlerDataScreen;
import handler.I_EventhandlerHomeScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Data;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.User;
import org.tempuri.ILoginService;

import com.dropbox.core.DbxException;

import controller.StarterLogin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author          :   Sandro Guerotto
 * Created          :   04.10.2016
 * Project          :   cloud
 * Package          :   controller
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   Allgemeiner Kontroller für das Programm
 */
public class Controller implements I_EventhandlerDataScreen, I_EventhandlerHomeScreen, ILoginService {

	private String[] args;
	private Dropbox dropbox;
	
    public Controller(String[] args){
    	this.args = args;
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


    //starter
    public void start(){
        StarterLogin starterLogin = new StarterLogin();
        starterLogin.show(args, this);
    }
    
    public void gotoHome(Stage stage){
    	stage.close();
    	StarterHome starterHome = new StarterHome();
    	starterHome.setController(this);
    	starterHome.start(stage);
    }
    
    public void gotoData(Stage stage){
    	stage.close();
    	StarterData starterData = new StarterData();
    	starterData.setController(this);
    	starterData.start(stage);
    }
    
    public void kill(){
    	Platform.exit();
        System.exit(0);
    }

    @Override
    public ObservableList<Data> getAllData() {
        return dropbox.getarchives();
    }

    @Override
    public void delete_data(ObservableList<Data> deletelist) throws DeleteException, ConnectionErrorException {

    }

    @Override
    public void upload_data(List<File> uploadlist) throws UploadException, ConnectionErrorException {

    }

    @Override
    public void download_data(ArrayList<Data> downloadlist) throws DownloadException, ConnectionErrorException {

    }

    @Override
    public void getLogin(String Username) {

    }

    @Override
    public Boolean usernameUnique(String username) throws RemoteException {
        return null;
    }

    @Override
    public Boolean emailUnique(String mail) throws RemoteException {
        return null;
    }

    @Override
    public void register(String username, String mail, String password) throws RemoteException {

    }

    @Override
    public User login(String username, String password) throws RemoteException {
        return null;
    }

    @Override
    public void update(User newUserData) throws RemoteException {

    }

    @Override
    public ServiceType[] loadAllServices() throws RemoteException {
        return new ServiceType[0];
    }



	@Override
	public void getDropboxAPI(String api) {
		// TODO Auto-generated method stub
		
	}
}
