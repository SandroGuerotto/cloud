package exception;

/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * @Created          :   17.10.2016
 * @Project          :   cloud
 * @Package          :   exception
 * @version 		 :   1.0
 * @LastUpdated      :   04.11.2016 / by Sandro Guerotto
 * @Description      :
 */
public class UpdateServiceErrorException extends CloudException{
	
    private final String msg = "Aktualisierung der Service-Verbindung fehlgeschlagen";

    public UpdateServiceErrorException(char type){
        setType(type);
        setMsg(msg);
    }
    
    public UpdateServiceErrorException(){
        setMsg(msg);
    }

}
