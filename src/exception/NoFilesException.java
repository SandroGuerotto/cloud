package exception;

/**
 * @author          :   Sandro Guerotto
 * @Created          :   06.10.2016
 * @Project          :   cloud
 * @Package          :   exception
 * @version         :   1.0
 * @LastUpdated      :	04.11.2016 / by Sandro Guerotto
 * @Description      :   Exception class for error while deleting files
 */
public class NoFilesException extends CloudException {

	private final String msg = "Es sind keine Daten vorhanden!";

	public NoFilesException(char type) {
		setType(type);
		setMsg(msg);
	}
	public NoFilesException() {
		setMsg(msg);
	}

}
