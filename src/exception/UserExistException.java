package exception;

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
public class UserExistException extends CloudException {

	private final String msg = "Der Benutzername existiert bereits";
	private char type;

	public UserExistException(char type) {
		this.type = type;
		setMsg(msg);
	}

	public char getType() {
		return type;
	}
}
