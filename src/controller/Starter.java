package controller;

import javax.xml.rpc.ServiceException;

import exception.ConnectionErrorException;
import exception.FailLoadingServicesException;

/**
 * @author          :   Sandro Guerotto
 * Created          :   27.09.2016
 * Project          :   cloud
 * Package          :   controller
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   Starter class für das Programm
 */
public class Starter {

    public static void main(String[] args){
        Controller controller;
		try {
			controller = new Controller(args);
	        controller.start();
		} catch (ConnectionErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FailLoadingServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
