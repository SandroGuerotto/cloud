package exception;

/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * @Created          :   17.10.2016
 * @Project          :   cloud
 * @Package          :   exception
 * @version 		:   1.0
 * @LastUpdated      :   17.10.2016 / by Burim Cakolli
 * @Description      :
 * 
 */
public class LoadSupportedServicesException extends CloudException{
	
    private final String msg = "Die von der Applikation unterstützten Servicedienste konnten nicht vom Server geladen werden";
    private char   type;

    public LoadSupportedServicesException(char type){
        this.type = type;
        setMsg(msg);
    }

    public char getType(){
    	return type;
    }
}