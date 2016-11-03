package exception;

/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * @Created          :   19.10.2016
 * @Project          :   cloud
 * @Package          :   exception
 * @version 		:   1.0
 * @LastUpdated      :   19.10.2016 / by Burim Cakolli
 * @Description      :
 * 
 */
public class UsernameHasToBeFilledOutException extends CloudException {
	
	private final String msg = "Es muss ein Benutzername, Email & Passwort angegeben werden";
	private char type;

	public UsernameHasToBeFilledOutException(char type) {
		this.type = type;
		setMsg(msg);
	}

	public char getType() {
		return type;
	}
}
