package exception;

/**
 * @author :   Burim Cakolli
 * @Created          :   08.10.2016
 * @Project          :   cloud
 * @Package          :   exception
 * @version 		:   1.0
 * @LastUpdated      :   04.11.2016 / by Sandro Guerotto
 * @Description      :
 * 
 */
public class AddServiceFailException extends CloudException {
	
	private final String msg = "Speicherung der Service-Verbindung fehlgeschlagen";

	public AddServiceFailException(char type) {
		setType(type);
		setMsg(msg);
	}
	public AddServiceFailException(){
		setMsg(msg);
	}

}
