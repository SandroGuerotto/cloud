/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * Created          :   02.10.2016
 * Project          :   cloud
 * Package          :   model
 * @version 		:   1.0
 * LastUpdated      :   02.10.2016 / by Burim Cakolli
 * Description      :
 * 
 */
package model;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.User;

import javax.xml.rpc.ServiceException;
import org.tempuri.*;
import exception.*;
import message.Message;

public class ServerConnecter {
	
	private LoginServiceLocator serviceLocator;
	private BasicHttpsBinding_ILoginServiceStub service;
	private User user; //logged in User
	
	public ServerConnecter() throws ConnectionErrorException, ServiceException{
		this.start_service();
	}//-Standart Constructor
	
	private void start_service() throws ConnectionErrorException{
		this.serviceLocator = new LoginServiceLocator();
		try {
			this.service = (BasicHttpsBinding_ILoginServiceStub) serviceLocator.getBasicHttpsBinding_ILoginService();
		} catch (ServiceException e) {
			throw new ConnectionErrorException('e');
		}//-catch
	}//-start_service
	
	public User loginApp(String username, String password){
		try {
			this.user = this.service.login(username, password);
			return this.user;
		} catch (RemoteException e) {
			// TODO THROW FOR COULD NOT LOGIN
			e.printStackTrace();
			return null;
		}//-catch 
	}//-loginApp
	
	public User registerApp(String username, String email, String password) throws RemoteException, ConnectionErrorException{
		if(this.service.usernameUnique(username) == true && this.service.emailUnique(email) == true){
			this.service.register(username, email, password);
			this.user = this.service.login(username, password);
			return user;
		}//-if
		else{
			throw new ConnectionErrorException('e');
		}//-else
	}//-registerApp
	
	/* public static void main(String args[]) throws Exception {
		
		 
		 String username = "Burim";
		 String email = "Burim.cakolli@hotmail.com";
		 boolean usernameUnique = service.usernameUnique(username);
		 boolean emailUnique = service.emailUnique(email);
		 
		 System.out.println("Username Unique "+usernameUnique);
		 System.out.println("Email Unique "+emailUnique);
		 
		 if(usernameUnique == true && emailUnique == true){
			 service.register(username, email, "TESTPASSWORD");
		 }//-if
		 else{
			System.out.println("Username or Email already exists"); 
		 } 
		 
	 } */

}//-ServerConnecter
