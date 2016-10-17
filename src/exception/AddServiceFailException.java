/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * Created          :   08.10.2016
 * Project          :   cloud
 * Package          :   exception
 * @version 		:   1.0
 * LastUpdated      :   08.10.2016 / by Burim Cakolli
 * Description      :
 * 
 */
package exception;

public class AddServiceFailException extends Exception{
    private final String msg = "Speicherung der Service-Verbindung fehlgeschlagen";
    private char   type;

    public AddServiceFailException(char type){
        this.type = type;
    }

    public String getMsg(){
        return this.msg;
    }
    public char getType(){
    	return type;
    }

}
