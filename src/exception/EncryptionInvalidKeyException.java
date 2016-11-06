package exception;

/**
 * Exception when AES key invalid
 * @author Tim Meier
 * @version 1.0
 * @project cloud
 * @package exception
 * @created 25.10.2016
 * @lastUpdate 04.11.2016 / by Sandro Guerotto
 */
public class EncryptionInvalidKeyException extends CloudException {
	
	private final String msg = "AES Schlüssel ist ungültig!";

	public EncryptionInvalidKeyException(char type) {
		setType(type);
		setMsg(msg);
	}

	public EncryptionInvalidKeyException() {
		setMsg(msg);
	}
}
