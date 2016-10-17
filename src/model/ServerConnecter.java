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

import java.rmi.RemoteException;

import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.CloudService;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.User;

import javax.xml.rpc.ServiceException;
import org.tempuri.*;
import exception.*;

public class ServerConnecter {
	
	private LoginServiceLocator serviceLocator;
	private BasicHttpsBinding_ILoginServiceStub service;
	private User user; //logged in User
	private ServiceType[] services; //all supported Cloud-Services
	private CloudService cs; //service in use
		
	/* Standard Constructor with no parameters*/
	public ServerConnecter() throws ConnectionErrorException, ServiceException, FailLoadingServicesException{
		this.start_service();
	}//-Standart Constructor
	
	/* Starts the service to communicate with the server */
	private void start_service() throws ConnectionErrorException, FailLoadingServicesException{
		this.serviceLocator = new LoginServiceLocator();
		try {
			this.service = (BasicHttpsBinding_ILoginServiceStub) serviceLocator.getBasicHttpsBinding_ILoginService();
			try {
				this.services = this.service.loadAllServices();
			} catch (RemoteException e) {
				throw new FailLoadingServicesException('e');
			}
		} catch (ServiceException e) {
			throw new ConnectionErrorException('e');
		}//-catch
	}//-start_service
	
	/*
	 * Tries to log in the user into the application and set him as applicationUser
	 * @param username the name that the users identify himself
	 * @param password the password from the user in clear-text
	 * @return User This returns a object of user if the login function had success
	 */
	public User loginApp(String username, String password) throws LoginFailedException{
			try {
				this.user = this.service.login(username, password);
				return this.user;
			} catch (RemoteException e) {
				throw new LoginFailedException('e');
			}	
	}//-loginApp
	
	/*
	 * Register a new user into the application if the username and email is unique
	 * @param username the name that the users identify himself
	 * @param email the email adress of a user to contact him
	 * @param password the password from the user in clear-text
	 * @return User This returns a object of user if the register and login functions had success
	 */
	public User registerApp(String username, String email, String password) throws UserExistException, RemoteException, EmailExistException {
		if(this.service.usernameUnique(username) == true){
			if(this.service.emailUnique(email) == true){
				this.service.register(username, email, password);
				this.user = this.service.login(username, password);
				return this.user;
			}//-if
			else{
				throw new EmailExistException('e');
			}//-else
		}//-if
		else{
			throw new UserExistException('e');
		}//-else
	}//-registerApp
	
	//Don't forget the doc! @TODO
	public void addService(ServiceType service, String name, String usertoken) throws AddServiceFailException{
		try {
			this.service.addService(this.user.getId(), service, name, usertoken);
		} catch (RemoteException e) {
			throw new AddServiceFailException('w');
		}
	}//-addService
	
	/*
	 * Loads all supported Services from the Server into the Application
	 * @return void Because this only loads the Data into the ServerConnecter-Object
	 */
	public void loadServices() throws LoadSupportedServicesException{
		try {
			this.service.loadAllServices();
		} catch (RemoteException e) {
			throw new LoadSupportedServicesException('e');
		}
	}//-loadServices
	
	/*
	 * Loads all supported Services return them
	 * @return ServiceType[] Returns ArrayList with Objects ServiceType in it
	 */
	public ServiceType[] getAllServices() throws LoadSupportedServicesException{
		this.loadServices();
		return this.services;
	}//-getServices

}//-ServerConnecter
