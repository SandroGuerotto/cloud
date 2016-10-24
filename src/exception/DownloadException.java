package exception;

/**
 * @author          :   Sandro Guerotto
 * @Created          :   23.09.2016
 * @Project          :   cloud
 * @Package          :   exception
 * @version         :   1.0
 * @LastUpdated      :
 * @Description      :   Excetion for general download errors
 */
public class DownloadException extends Exception {


    private final String msg = "Ein Fehler während dem Herunterladen ist aufgetreten!";
    private char type;

    public DownloadException(char type) {
        this.type = type;
    }

    public String getMsg() {
        return this.msg;
    }
    public char getType(){
    	return type;
    }
}
