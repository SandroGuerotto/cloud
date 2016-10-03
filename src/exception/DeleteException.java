package exception;

/**
 * @author          :   Sandro Guerotto
 * Created          :   23.09.2016
 * Project          :   cloud
 * Package          :   exception
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   Exception class for error while deleting files
 */
public class DeleteException extends Exception {

    private final String msg = "Ein Fehler während dem Löschen ist aufgetreten!";
    private char   type;
    public DeleteException(char type;){
        this.type = type;
    }

    public String getMsg(){
        return this.msg;
    }
}
