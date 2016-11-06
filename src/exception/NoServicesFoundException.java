package exception;

/**
 * @author           :   Sandro Guerotto
 * @created          :   23.09.2016
 * @project          :   cloud
 * @package          :   exception
 * @version          :   1.0
 * @lastUpdated      :	 04.11.2016 / by Sandro Guerotto
 * @description      :   Exception class when no Service is found
 */
public class NoServicesFoundException extends CloudException {
	
	private final String msg = "Es sind noch keine Service eingetragen";

	public NoServicesFoundException(char type) {
		setType(type);
		setMsg(msg);
	}

	public NoServicesFoundException() {
		setMsg(msg);
	}
}
