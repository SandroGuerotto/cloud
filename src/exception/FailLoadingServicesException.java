package exception;

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
public class FailLoadingServicesException extends CloudException {
	
	private final String msg = "Fehler beim laden der Services ist aufgetreten";
	private char type;

	public FailLoadingServicesException(char type) {
		this.type = type;
		setMsg(msg);
	}

	public char getType() {
		return type;
	}
}
