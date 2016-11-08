package exception;

/**
 * @author       :   Floriana Gashi
 * @created      :   06.11.2016
 * @project      :   cloud
 * @package      :   exception
 * @version      :   1.0
 * @lastUpdated  : 	 08.11.2016 / by Floriana Gashi
 * @description  :   Exception class for errors with the authentication.
 */
public class AuthenticationFailedException extends  CloudException {

    private final String msg = "Die Authentifizierung ist fehlgeschlagen.";

    public AuthenticationFailedException(char type) {
        setType(type);
        setMsg(msg);
    }

    public AuthenticationFailedException() {
        setMsg(msg);
    }
}