 package exception;

/**
 * @author : Burim Cakolli Turns coffee & pizza into Software
 * @Created : 03.10.2016
 * @Project : cloud
 * @Package : exception
 * @version : 1.0
 * @LastUpdated : 04.11.2016 / by Sandro Guerotto
 * @Description :
 * 
 */
public class EmailExistException extends CloudException {
	
	private final String msg = "Die Email wurde bereits für ein Konto verwendet";

	public EmailExistException(char type) {
		setType(type);
		setMsg(msg);
	}

	public EmailExistException(){
		setMsg(msg);
	}
}
