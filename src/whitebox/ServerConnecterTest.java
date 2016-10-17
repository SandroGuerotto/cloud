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
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import exception.ConnectionErrorException;
import exception.EmailExistException;
import exception.FailLoadingServicesException;
import exception.LoginFailedException;
import exception.UserExistException;
import model.ServerConnecter;

public class ServerConnecterTest {
	ServerConnecter sc;
	String username 	= "TestUsername4";
	String email 		= "Test.User@hotmail4.com";
	String password 	= "root1234"; 
	
	/*
	 * Gets object and returns it back as a String for Debuging
	 * @param user is the User that we want informations in String
	 * @return String This returns a String of user information
	 */
	public String toStringObject(User user){
	   	String userinfos = "User-Data:  \n";
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
	
	@BeforeClass
	public static void startTest(){
		System.out.println("ServerConnecterTest started!");
	}//-start
	
	@Before
	public void startConnection() throws ConnectionErrorException, ServiceException, FailLoadingServicesException{
		this.sc = new ServerConnecter();
	}//-startConnection
	
	@Test
	public void registerApp() throws RemoteException, UserExistException, EmailExistException {
		User user = this.sc.registerApp(username, email, password);
		System.out.println();
		assertNotNull(user);
	}//-loginApp

	@Test
	public void loginApp() throws LoginFailedException {
		User user = this.sc.loginApp(username, password);
		assertNotNull(user);
		System.out.println(toStringObject(user));
	}//-loginApp

	@Test
	public void addService(){
		// @TODO
	}//-addService 	
	
}
