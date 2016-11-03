package exception;

/**
 * Created by Sandro on 02.11.2016.
 */
public class CloudRemoteException extends CloudException {

	private final String msg = "Ein Fehler mit der Verbindung ist aufgetreten";
	private char type;

	public CloudRemoteException(char type) {
		this.type = type;
		setMsg(msg);
	}

	public char getType() {
		return type;
	}
}
