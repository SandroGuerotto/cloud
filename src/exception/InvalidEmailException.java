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
public class InvalidEmailException extends CloudException {

	private final String msg = "Die Email ist ungültig";

	public InvalidEmailException(char type) {
		setType(type);
		setMsg(msg);
	}

	public InvalidEmailException(){
		setMsg(msg);
	}
}
