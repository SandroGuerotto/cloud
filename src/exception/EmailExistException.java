/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * Created          :   03.10.2016
 * Project          :   cloud
 * Package          :   exception
 * @version 		:   1.0
 * LastUpdated      :   03.10.2016 / by Burim Cakolli
 * Description      :
 * 
 */
package exception;

public class EmailExistException extends Exception{
	    private final String msg = "Die Email wurde bereits für ein Konto verwendet";
	    private char   type;

	    public EmailExistException(char type){
	        this.type = type;
	    }

	    public String getMsg(){
	        return this.msg;
	    }

}
