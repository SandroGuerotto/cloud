package thread;

import exception.LoginFailedException;
import handler.EventhandlerLogin;
import model.ServerConnecter;

/**
 * @author      :   Sandro Guerotto
 * @version     :   1.0
 * @created     :   01.11.2016
 * @project     :   cloud
 * @package     :   thread
 * @lastupdate  :
 * @description :   Handles login to the server. callback to login screen
 */
public class LoginThread extends Thread {

    private String username, password;
    private ServerConnecter serverConnecter;
    private EventhandlerLogin eventhandlerLogin;

    public LoginThread(ServerConnecter serverConnecter, String username, String password, EventhandlerLogin handler){
        this.serverConnecter = serverConnecter;
        this.username = username;
        this.password = password;
        this.eventhandlerLogin = handler;
    }

    /**
     * Starts process to login user. Any error and progress will be showed at screen
     */
    @Override
    public void run(){
        try {
            eventhandlerLogin.onWorkStart();
            serverConnecter.loginApp(username, password);
            eventhandlerLogin.onWorkEnd();
        } catch (LoginFailedException e) {
            eventhandlerLogin.onWorkError(e);
        }
    }
}
