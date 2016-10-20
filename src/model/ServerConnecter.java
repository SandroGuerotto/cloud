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
	private ServiceType serviceTypeInUse; //actual servicetype in use (Like DROPBOX)
	private CloudService cs; //service in use (Burims Connection to Dropbox TOKEN)
		
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
	
	/*
	 * Inserts a Service into the Database that allows the Connection between User and Service (Like Dropbox)
	 * @param service is the ServiceType that we want to Save (Dropbox)
	 * @param name is the name of the connection ("My Connection-name")
	 * @return Void No Return
	 */
	public void addService(ServiceType service, String name, String usertoken) throws AddServiceFailException, NoUserLoggedInException{
		try {
			this.service.addService(this.getLoggedInUser().getId(), service.getId(), name, usertoken);
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
			this.services = this.service.loadAllServices();
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

	/*
	 * This method updates the name of a existing Service-connection
	 * @param service is the Connection between User and Service that we want to update
	 * @param name is the new name/alias that we want to give this connection 
	 */
	public void updateService(CloudService service, String newname) throws UpdateServiceErrorException{
		try {
			this.service.updateService(service.getId(), newname);
		} catch (RemoteException e) {
			throw new UpdateServiceErrorException('w');
		}//-catch
	}//-updateService
	
	/*
	 * Deletes a existing ServiceConnection of a User
	 * @param service is the Service that we want to remove from db
	 * @return void 
	 */
	public void deleteService(CloudService service) throws DeleteServiceConnectionErrorException{
		try {
			this.service.removeService(service.getId());
		} catch (RemoteException e) {
			throw new DeleteServiceConnectionErrorException('w');
		}//-catch
	}//-deleteService
	
	/*
	 * Good practice for Checking if a user is logged in 
	 * @return User This returns the Logged In User as a Object
	 */
	public User getLoggedInUser() throws NoUserLoggedInException{
		if(this.user != null){
			return this.user;
		}//-if
		else{
			throw new NoUserLoggedInException('e');
		}//-else
	}//-getLoggInUser
	
	/*
	 * This function updates the user password for our application
	 * @param user Is the User that we want to update
	 * @param oldPassword Is the old password that we need to make this update
	 * @param newPassword Is the password that we want to set as current pw
	 */
	public void updateUserPw(String oldPassword, String newPassword) throws UpdateUserPwErrorException{
		try {
			this.service.changePassword(this.user.getId(), oldPassword, newPassword);
		} catch (RemoteException e) {
			throw new UpdateUserPwErrorException('w');
		}//-catch
	}//-updateUserPw
	
	//@TODO DOC
	public ServiceType setActualServiceType(ServiceType service){
		this.serviceTypeInUse = service;
		return this.serviceTypeInUse;
	}//-setActualServiceType
	
	//@TODO DOC
	public ServiceType getActualServiceType(){
		return this.serviceTypeInUse;
	}//-getActualServiceType


	public User getUser(){
		return this.user;
	}
}//-ServerConnecter
