package exception;

/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * @Created          :   17.10.2016
 * @Project          :   cloud
 * @Package          :   exception
 * @version 		 :   1.0
 * @LastUpdated      :   17.10.2016 / by Burim Cakolli
 * @Description      :
 */
public class UpdateServiceErrorException extends CloudException{
	
    private final String msg = "Aktualisierung der Service-Verbindung fehlgeschlagen";
    private char   type;

    public UpdateServiceErrorException(char type){
        this.type = type;
        setMsg(msg);
    }
    
    public char getType(){
    	return type;
    }

}
