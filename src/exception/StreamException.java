package exception;

/**
 * Exception when open a stream failed
 * @author Tim Meier
 * @version 1.0
 * @project cloud
 * @package exception
 * @created 25.10.2016
 * @lastUpdate 25.10.2016 / by Tim Meier
 */
public class StreamException extends Exception {
	private final String msg = "Es gab ein Problem mit dem FileStream!";
    private char   type;

    public StreamException(char type){
        this.type = type;
    }

    public String getMsg(){
        return this.msg;
    }
    public char getType(){
    	return type;
    }
}
