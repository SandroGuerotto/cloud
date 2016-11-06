package exception;

/**
 * @author       :   Sandro Guerotto
 * @created      :   20.09.2016
 * @project      :   cloud
 * @package      :   exception
 * @version      :   1.0
 * @lastUpdated  : 04.11.2016 / by Sandro Guerotto
 * @description  :   Exception class for errors with the internet connection/ cloud services
 */
public class ConnectionErrorException extends CloudException {
	
	private final String msg = "Ein Fehler mit der Verbindung ist aufgetreten!";

	public ConnectionErrorException(char type) {
		setType(type);
		setMsg(msg);
	}

	public ConnectionErrorException() {
		setMsg(msg);
	}
}
