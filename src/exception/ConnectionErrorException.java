package exception;

/**
 * @author       :   Burim Cakolli
 * @created      :   08.11.2016
 * @project      :   cloud
 * @package      :   exception
 * @version      :   1.0
 * @lastUpdated  : 	 08.11.2016 / by Burim Cakolli
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
