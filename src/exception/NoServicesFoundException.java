package exception;

/**
 * Created by Sandro on 21.10.2016.
 */
public class NoServicesFoundException extends  Exception {
    private final String msg = "Es sind noch keine Service eingetragen";
    private char   type;

    public NoServicesFoundException(char type){
        this.type = type;
    }

    public String getMsg(){
        return this.msg;
    }
    public char getType(){
        return type;
    }
}
