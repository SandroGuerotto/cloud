package exception;

/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * @Created          :   08.10.2016
 * @Project          :   cloud
 * @Package          :   exception
 * @version 		:   1.0
 * @LastUpdated      :   04.11.2016 / by Sandro Guerotto
 * @Description      :
 * 
 */
public class FailLoadingServicesException extends CloudException {
	
	private final String msg = "Fehler beim laden der Services ist aufgetreten";
	public FailLoadingServicesException(char type) {
		setType(type);
		setMsg(msg);
	}

	public FailLoadingServicesException() {
		setMsg(msg);
	}
}
