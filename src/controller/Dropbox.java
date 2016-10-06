package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;

import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
import javafx.collections.FXCollections;

import model.Data;

/**
 * @author          :   Sasa Markovic
 * Created          :   03.10.2016
 * Project          :   cloud
 * Package          :   controller
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   Connector/uploader/downloader/getter from Dropbox
 */

public class Dropbox {
	//Wichtigsten Variabeln
	private DbxAppInfo appInfo;
	private DbxRequestConfig config;
	private DbxWebAuthNoRedirect webAuth;
	private String authtoken;
	private String accesstoken;
	private DbxClient client;
	private ObservableList<Data> list = FXCollections.observableArrayList();
	
	public static void main(String[] args0) throws IOException, URISyntaxException, DbxException{
		new Dropbox();
	}
	//Controller
	public Dropbox() throws IOException, URISyntaxException, DbxException{
		appInfo = new DbxAppInfo("4ib2r751sawik1x","xwcn09oaicgpo0d");
		config = new DbxRequestConfig("Testsecurecloudapp", Locale.getDefault().toString());
		webAuth = new DbxWebAuthNoRedirect(config, appInfo);
		
		firstlogin();
		makearchives();
		getarchives();
	}
	//Erstellt die variable "list"
	protected void makearchives() throws DbxException{
		
		DbxEntry.WithChildren files = client.getMetadataWithChildren("/");
		for(DbxEntry file : files.children){
			if(!file.isFolder()){
				
				Data dummy = new Data();
				dummy.setdata_type(getextension(file.name));
				dummy.setdata_size(convertLongtoString(file.asFile().numBytes));
				dummy.setdata_name(file.name);
				dummy.setdatacreate(convertDateToString(file.asFile().clientMtime));
				dummy.setdata_last(convertDateToString(file.asFile().lastModified));
				list.add(dummy);
				dummy = null;
			} else{
				Data dummy = new Data();
				dummy.setdata_type("Folder");
				dummy.setdata_size("");
				dummy.setdata_name(file.name);
				dummy.setdatacreate("");
				dummy.setdata_last("");
				list.add(dummy);
				dummy = null;
			}
		}
		
	}
	//bei registrationen oder invaliden accesstokens
	public void firstlogin() throws IOException, URISyntaxException, DbxException{
		Scanner sc = new Scanner(System.in);
		Desktop.getDesktop().browse(new URI(webAuth.start()));
		
		System.out.println("Kopieren Sie den Text und fügen Sie ihn ein!");
		authtoken = sc.nextLine();
		
		accesstoken = webAuth.finish(authtoken).accessToken;
		
		client = new DbxClient(config,accesstoken);
	}
	protected String convertLongtoString(Long args){
		
		String temp = Long.toString(args);
		
		return temp;
	}
	protected String convertDateToString(Date indate){
		String dateString = null;
		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
		try{
			dateString = sdfr.format( indate );
		}catch (Exception ex ){
			System.out.println(ex);
		}
		return dateString;
	}
	public String getextension(String fileName){
		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		    return extension;
		}
		return null;
	}
	public void downloadFile(){
		
	}
	public void uploadFile(File args){
		
	}
	public void login(){
		client = new DbxClient(config,accesstoken);
	}
	public void logout(){
		client = null;
	}
	public void setappInfo(DbxAppInfo args){
		appInfo = args;
	}
	public void setconfig(DbxRequestConfig args){
		config = args;
	}
	public void setwebAuth(DbxWebAuthNoRedirect args){
		webAuth = args;
	}
	public void setauthtoken(String args){
		authtoken = args;
	}
	public void setclient(DbxClient args){
		client = args;
	}
	public DbxAppInfo getappInfo(){
		return appInfo;
	}
	public DbxRequestConfig getconfig(){
		return config;
	}
	public DbxWebAuthNoRedirect getwebAuth(){
		return webAuth;
	}
	public String getauthtoken(){
		return authtoken;
	}
	public DbxClient getclient(){
		return client;
	}
	public ObservableList<Data> getarchives(){
		return list;
	}
}
