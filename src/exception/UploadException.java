package exception;


/**
 * @author          :   Sandro Guerotto
 * Created          :   23.09.2016
 * Project          :   cloud
 * Package          :   exception
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   Excetion for general upload errors
 */
public class UploadException extends Exception{

    private final String msg = "Ein Fehler während dem Hochladen ist aufgetreten!";
    private char type;

    public UploadException(char type) {
        this.type = type;
    }

    public String getMsg() {
        return this.msg;
    }
    public char getType(){
    	return type;
    }
}
