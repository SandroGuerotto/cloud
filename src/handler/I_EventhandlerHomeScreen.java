package handler;

import exception.LoadSupportedServicesException;
import exception.NoServicesFoundException;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType;

public interface I_EventhandlerHomeScreen {

 ServiceType[] getServices() throws LoadSupportedServicesException, NoServicesFoundException;

}
