/**
 * @author :   Burim Cakolli
 * Turns coffee & pizza into Software
 * @Created          :   19.10.2016
 * @Project          :   cloud
 * @Package          :   exception
 * @version 		:   1.0
 * @LastUpdated      :   19.10.2016 / by Burim Cakolli
 * @Description      :
 * 
 */
package exception;

public class UsernameHasToBeFilledOutException extends  Exception {
    private final String msg = "Es muss ein Benutzername, Email & Passwort angegeben werden";
    private char   type;

    public UsernameHasToBeFilledOutException(char type){
        this.type = type;
    }

    public String getMsg(){
        return this.msg;
    }
    public char getType(){
    	return type;
    }
}
