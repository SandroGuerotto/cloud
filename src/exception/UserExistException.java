package exception;

/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * @created          :   03.10.2016
 * @project          :   cloud
 * @package          :   exception
 * @version 		 :   1.0
 * @lastUpdated      :   04.11.2016 / by Sandro Guerotto
 * @description      :
 * 
 */
public class UserExistException extends CloudException {

	private final String msg = "Der Benutzername existiert bereits";

	public UserExistException(char type) {
		setType(type);
		setMsg(msg);
	}

	public UserExistException(){
		setMsg(msg);
	}
}
