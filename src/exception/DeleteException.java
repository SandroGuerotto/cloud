package exception;

/**
 * @author           :   Sandro Guerotto
 * @created          :   23.09.2016
 * @project          :   cloud
 * @package          :   exception
 * @version          :   1.0
 * @lastUpdated      :	 04.11.2016 / by Sandro Guerotto
 * @description      :   Exception class for error while deleting files
 */
public class DeleteException extends CloudException {

	private final String msg = "Ein Fehler während dem Löschen ist aufgetreten!";

	public DeleteException(char type) {
		setType(type);
		setMsg(msg);
	}
	public DeleteException(){
		setMsg(msg);
	}

}
