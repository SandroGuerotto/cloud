package controller;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.dropbox.core.DbxException;

import exception.ConnectionErrorException;
import exception.DeleteException;
import exception.DownloadException;
import exception.NoFilesException;
import exception.UploadException;
import handler.I_EventhandlerDataScreen;
import handler.I_EventhandlerHomeScreen;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Data;

/**
 * @author          :   Sandro Guerotto
 * Created          :   04.10.2016
 * Project          :   cloud
 * Package          :   controller
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   Allgemeiner Kontroller für das Programm
 */
public class Controller implements I_EventhandlerDataScreen, I_EventhandlerHomeScreen {

	private String[] args;
	private Dropbox dropbox;
	
    public Controller(String[] args){
    	this.args = args;

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
