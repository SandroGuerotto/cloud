package exception;

/**
 * Exception when File not found
 * @author Tim Meier
 * @version 1.0
 * @project cloud
 * @package exception
 * @created 25.10.2016
 * @lastUpdate 04.11.2016 / by Sandro Guerotto
 */
public class EncryptionFileNotFoundException extends CloudException {

	private final String msg = "Datei wurde nicht gefunden!";

	public EncryptionFileNotFoundException(char type) {
		setType(type);
		setMsg(msg);
	}

	public EncryptionFileNotFoundException() {
		setMsg(msg);
	}

}
