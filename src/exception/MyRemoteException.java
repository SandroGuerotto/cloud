package exception;

/**
 * Created by Sandro on 02.11.2016.
 */
public class MyRemoteException extends Exception {

    private final String msg = "Ein Fehler mit der Verbindung ist aufgetreten";
    private char   type;

    public MyRemoteException(char type){
        this.type = type;
    }

    public String getMsg(){
        return this.msg;
    }
    public char getType(){
        return type;
    }
}
