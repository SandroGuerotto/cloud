/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * Created          :   03.10.2016
 * Project          :   cloud
 * Package          :   whitebox
 * @version 		:   1.0
 * LastUpdated      :   08.10.2016 / by Burim Cakolli
 * Description      :
 * 
 */
package whitebox;

import static org.junit.Assert.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.xml.rpc.ServiceException;

import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.CloudService;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.AddServiceFailException;
import exception.ConnectionErrorException;
import exception.EmailExistException;
import exception.FailLoadingServicesException;
import exception.LoadSupportedServicesException;
import exception.LoginFailedException;
import exception.NoUserLoggedInException;
import exception.UserExistException;
import model.ServerConnecter;

public class ServerConnecterTest {
	ServerConnecter sc;
	String username 	= "TestUsername4";
	String email 		= "Test.User@hotmail4.com";
	String password 	= "root1234"; 
	
	/*
	 * Gets object and returns it back as a String for Debugging
	 * @param user is the User that we want informations in String
	 * @return String This returns a String of user information
	 */
	public String toStringObject(User user){
		String userinfos  = "----------------------\n";
	   	userinfos += "User-Data:  \n";
	   	userinfos += "ID: "+user.getId().toString()+"\n";
	   	userinfos += "Username: "+user.getUsername()+"\n";
	   	userinfos += "Email: "+user.getMail()+"\n";
	   	userinfos += "Services: "+"\n";
	   	CloudService[] services = user.getServices();
	    for(int i = 0; i < services.length; i++){
	    	userinfos += "-ID: "+services[i].getId()+"\n";
	    	userinfos += "-Name : "+services[i].getName()+"\n";
	    	userinfos += "-Type"+services[i].getType()+"\n";
	    	userinfos += "-Token:"+services[i].getLoginToken()+"\n";
		}//-for	
    	return userinfos;
	}//-printUser
	
	/*
	 * Gets object and returns it back as a String for Debugging
	 * @param services is the ServiceType that we want informations in String
	 * @return String This returns a String of Object information
	 */
	public String toStringObject(ServiceType[] services){
		String str  = "----------------------\n";
		str  += "All supported Servies in Application\n";
	    for(int i = 0; i < services.length; i++){
	    	str += "ID: "+services[i].getId()+"\n";
	    	str += "Token"+services[i].getKey();
	    	str += "Name : "+services[i].getName()+"\n";
	    	str += "Type"+services[i].getSecret()+"\n";
		}//-for	
    	return str;
	}
	
	@BeforeClass
	public static void startTest(){
		System.out.println("ServerConnecterTest started!");
	}//-start
	
	@Before
	public void startConnection() throws ConnectionErrorException, ServiceException, FailLoadingServicesException{
		this.sc = new ServerConnecter();
		System.out.println("he\n");
	}//-startConnection
	
	@Test
	public void registerApp() throws RemoteException, UserExistException, EmailExistException {
		User user = this.sc.registerApp(username, email, password);
		assertNotNull(user);
	}//-loginApp

	@Test
	public void loginApp() throws LoginFailedException {
		User user = this.sc.loginApp(username, password);
		assertNotNull(user);
	}//-loginApp

	@Test
	public void addServiceConnection() throws LoadSupportedServicesException, AddServiceFailException, LoginFailedException, NoUserLoggedInException{
		this.sc.loginApp(username, password);
		ServiceType[] services = this.sc.getAllServices();
		this.sc.addService(services[0], "My First DropBox Connection", "FakeTokenXZAXUASDJHAKSDJHASDJKSADASDSAD");
	}//-addServiceConnection
	
	@Test
	public void loadServices() throws LoadSupportedServicesException{
		this.sc.loadServices();
	}//-loadServices 	
	
	@Test
	public void getAllServices() throws LoadSupportedServicesException{
		ServiceType[] allServices = this.sc.getAllServices();
		assertNotNull(allServices);
	}//-getAllServices
	
	@Test
	public void updateService(){
		//@TODO
	}//-updateService
	
	@Test
	public void deleteService(){
		//@TODO
	}//-updateService
	
	@Test
	public void getLoggedUser(){
		//@TODO
	}//-updateService
	
	@Test
	public void changePassword(){
		//@TODO
	}//-updateService
	
	
/*	@After
	public void debug() throws LoadSupportedServicesException, LoginFailedException{
		System.out.println( toStringObject(this.sc.getAllServices()) );
		System.out.println( toStringObject(this.sc.loginApp(username, password)) );
	}//-debug	
*/
}//-Test.Class
