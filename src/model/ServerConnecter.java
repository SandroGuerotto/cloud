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

import org.tempuri.*;

public class ServerConnecter {
	 public static void main(String args[]) throws Exception {
		 LoginServiceLocator serviceLocator = new LoginServiceLocator();
		 BasicHttpsBinding_ILoginServiceStub service = (BasicHttpsBinding_ILoginServiceStub) serviceLocator.getBasicHttpsBinding_ILoginService();
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
		 
	 }

}//-ServerConnecter
