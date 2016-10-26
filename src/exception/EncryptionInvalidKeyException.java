package exception;

/**
 * Exception when AES key invalid
 * @author Tim Meier
 * @version 1.0
 * @project cloud
 * @package exception
 * @created 25.10.2016
 * @lastUpdate 25.10.2016 / by Tim Meier
 */
public class EncryptionInvalidKeyException  extends Exception {
	private final String msg = "AES Schlüssel ist ungültig!";
    private char   type;

    public EncryptionInvalidKeyException(char type){
        this.type = type;
    }

    public String getMsg(){
        return this.msg;
    }
    public char getType(){
    	return type;
    }
}
