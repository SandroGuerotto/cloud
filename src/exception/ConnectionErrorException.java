package exception;

import message.Message;

/**
 * @author          :   Sandro Guerotto
 * Created          :   20.09.2016
 * Project          :   cloud
 * Package          :   exception
 * @version         :   1.0
 * LastUpdated      :   03.10.2016
 * Description      :   Exception class for errors with the internet connection/ cloud services
 */

public class ConnectionErrorException extends  Exception {
<<<<<<< HEAD
    public ConnectionErrorException(){
    	
=======
    private final String msg = "Ein Fehler mit der Verbindung ist aufgetreten!";
    private char   type;
    public ConnectionErrorException(char type){
        this.type = type;
    }

    public String getMsg(){
        return this.msg;
>>>>>>> branch 'master' of https://github.com/SandroGuerotto/cloud
    }
}
