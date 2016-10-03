package exception;

/**
 * @author          :   Sandro Guerotto
 * Created          :   20.09.2016
 * Project          :   cloud
 * Package          :   exception
 * @version         :   1.0
 * LastUpdated      :
 * LastUpdated      :   03.10.2016
 * Description      :   Exception class for errors with the internet connection/ cloud services
 */

public class ConnectionErrorException extends  Exception {


    private final String msg = "Ein Fehler mit der Verbindung ist aufgetreten!";
    private char   type;
    public ConnectionErrorException(char type){
        this.type = type;
    }



    public String getMsg(){
        return this.msg;



    }
}
