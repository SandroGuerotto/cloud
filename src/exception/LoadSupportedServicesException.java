package exception;

/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * @Created          :   17.10.2016
 * @Project          :   cloud
 * @Package          :   exception
 * @version 		:   1.0
 * @LastUpdated      :   04.11.2016 / by Sandro Guerotto
 * @Description      :
 * 
 */
public class LoadSupportedServicesException extends CloudException{
	
    private final String msg = "Die von der Applikation unterstützten Servicedienste konnten nicht vom Server geladen werden";
    public LoadSupportedServicesException(char type){
        setType(type);
        setMsg(msg);
    }

    public LoadSupportedServicesException(){
        setMsg(msg);
    }
}