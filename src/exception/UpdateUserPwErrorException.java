/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * Created          :   17.10.2016
 * Project          :   cloud
 * Package          :   exception
 * @version 		:   1.0
 * LastUpdated      :   17.10.2016 / by Burim Cakolli
 * Description      :
 * 
 */
package exception;

public class UpdateUserPwErrorException extends Exception{
    private final String msg = "Änderung des Userpassworts fehlgeschlagen";
    private char   type;

    public UpdateUserPwErrorException(char type){
        this.type = type;
    }

    public String getMsg(){
        return this.msg;
    }
    public char getType(){
    	return type;
    }

}
