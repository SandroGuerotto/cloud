package exception;

/**
 * Exception when File not found
 * @author Tim Meier
 * @version 1.0
 * @project cloud
 * @package exception
 * @created 25.10.2016
 * @lastUpdate 25.10.2016 / by Tim Meier
 */
public class EncryptionFileNotFoundException extends CloudException {
	private final String msg = "Datei wurde nicht gefunden!";
    private char   type;

    public EncryptionFileNotFoundException(char type){
        this.type = type;
        setMsg(msg);
    }
    public char getType(){
    	return type;
    }
}
