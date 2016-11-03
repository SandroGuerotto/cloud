package exception;

/**
 * Exception when copy a stream failed
 * @author Tim Meier
 * @version 1.0
 * @project cloud
 * @package exception
 * @created 25.10.2016
 * @lastUpdate 25.10.2016 / by Tim Meier
 */
public class StreamCopyException extends CloudException {
	private final String msg = "Es gab ein Problem mit dem kopieren der Streams!";
    private char   type;

    public StreamCopyException(char type){
        this.type = type;
        setMsg(msg);
    }

    public char getType(){
    	return type;
    }
}
