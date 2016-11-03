package thread;

import exception.EmailExistException;
import exception.CloudRemoteException;
import exception.UserExistException;
import handler.EventhandlerLogin;
import model.ServerConnecter;

import java.rmi.RemoteException;

/**
 * @author      :   Sandro Guerotto
 * @version     :   1.0
 * @created     :   01.11.2016
 * @project     :   cloud
 * @package     :   thread
 * @lastupdate  :
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
            	serverConnecter.registerApp(username, email, password);
                eventhandlerLogin.onWorkEnd();
            }else{
            	throw new CloudRemoteException('e');
            }
            
        } catch (RemoteException e) {
            eventhandlerLogin.onWorkError(new CloudRemoteException('e'));
        } catch (EmailExistException | UserExistException | CloudRemoteException e) {
            eventhandlerLogin.onWorkError(e);
        }
    }
}
