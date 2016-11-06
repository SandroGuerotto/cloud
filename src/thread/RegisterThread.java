package thread;

import exception.*;
import handler.EventhandlerLogin;
import model.ServerConnecter;
import model.Validator;

import java.rmi.RemoteException;

/**
 * @author      :   Sandro Guerotto
 * @version     :   1.0
 * @created     :   01.11.2016
 * @project     :   cloud
 * @package     :   thread
 * @lastupdate  :   04.11.2016 by / Sandro Guerotto
 * @description :   Handles sign up to the server. callback to login screen
 */
public class RegisterThread extends Thread {

    private String username, password, email;
    private ServerConnecter serverConnecter;
    private EventhandlerLogin eventhandlerLogin;

    public RegisterThread(ServerConnecter serverConnecter, String username, String password, String email, EventhandlerLogin handler){
        this.serverConnecter = serverConnecter;
        this.username = username;
        this.password = password;
        this.email = email;
        this.eventhandlerLogin = handler;
    }

    /**
     * Starts process to sign up user. Any error and progress will be showed at screen
     */
    @Override
    public void run(){
        try {
            eventhandlerLogin.onWorkStart();
            if(serverConnecter != null){
                Validator.validate(email);
            	serverConnecter.registerApp(username, email, password);
                eventhandlerLogin.onWorkEnd();
            }else{
            	throw new ConnectionErrorException();
            }
            
        } catch (RemoteException e) {
            eventhandlerLogin.onWorkError(new ConnectionErrorException());
        } catch (CloudException e) {
            eventhandlerLogin.onWorkError(e);
        }
    }
}
