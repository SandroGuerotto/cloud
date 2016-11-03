/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * @Created          :   03.10.2016
 * @Project          :   cloud
 * @Package          :   exception
 * @version 		:   1.0
 * @LastUpdated      :   03.10.2016 / by Burim Cakolli
 * @Description      :
 * 
 */
package exception;

public class LoginFailedException extends CloudException{
	    private final String msg = "Benutzername oder Passwort falsch";
	    private char   type;

	    public LoginFailedException(char type){
	        this.type = type;
	        setMsg(msg);
	    }

	    public char getType(){
	    	return type;
	    }

}
