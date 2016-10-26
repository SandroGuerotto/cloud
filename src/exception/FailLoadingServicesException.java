/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * @Created          :   08.10.2016
 * @Project          :   cloud
 * @Package          :   exception
 * @version 		:   1.0
 * @LastUpdated      :   08.10.2016 / by Burim Cakolli
 * @Description      :
 * 
 */
package exception;

public class FailLoadingServicesException extends Exception{
    private final String msg = "Fehler beim laden der Services ist aufgetreten";
    private char   type;

    public FailLoadingServicesException(char type){
        this.type = type;
    }

    public String getMsg(){
        return this.msg;
    }
    public char getType(){
    	return type;
    }
}
