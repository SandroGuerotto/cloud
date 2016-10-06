package exception;

/**
 * @author          :   Sandro Guerotto
 * Created          :   06.10.2016
 * Project          :   cloud
 * Package          :   exception
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   Exception class for error while deleting files
 */
public class NoFilesException extends Exception {

    private final String msg = "Es sind keine Daten vorhanden!";
    private char   type;
    public NoFilesException(char type){
        this.type = type;
    }

    public String getMsg(){
        return this.msg;
    }
    
    public char getType(){
    	return type;
    }
}
