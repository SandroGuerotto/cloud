package org.tempuri;

public class ILoginServiceProxy implements org.tempuri.ILoginService {
  private String _endpoint = null;
  private org.tempuri.ILoginService iLoginService = null;
  
  public ILoginServiceProxy() {
    _initILoginServiceProxy();
  }
  
  public ILoginServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initILoginServiceProxy();
  }
  
  private void _initILoginServiceProxy() {
    try {
      iLoginService = (new org.tempuri.LoginServiceLocator()).getBasicHttpBinding_ILoginService();
      if (iLoginService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iLoginService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iLoginService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iLoginService != null)
      ((javax.xml.rpc.Stub)iLoginService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.ILoginService getILoginService() {
    if (iLoginService == null)
      _initILoginServiceProxy();
    return iLoginService;
  }
  
  public java.lang.Boolean usernameUnique(java.lang.String username) throws java.rmi.RemoteException{
    if (iLoginService == null)
      _initILoginServiceProxy();
    return iLoginService.usernameUnique(username);
  }
  
  public java.lang.Boolean emailUnique(java.lang.String mail) throws java.rmi.RemoteException{
    if (iLoginService == null)
      _initILoginServiceProxy();
    return iLoginService.emailUnique(mail);
  }
  
  public void register(java.lang.String username, java.lang.String mail, java.lang.String password) throws java.rmi.RemoteException{
    if (iLoginService == null)
      _initILoginServiceProxy();
    iLoginService.register(username, mail, password);
  }
  
  public org.datacontract.schemas._2004._07.PrettySecureCloud_Model.User login(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException{
    if (iLoginService == null)
      _initILoginServiceProxy();
    return iLoginService.login(username, password);
  }
  
  public void changePassword(java.lang.Integer userId, java.lang.String currentPassword, java.lang.String newPassword) throws java.rmi.RemoteException{
    if (iLoginService == null)
      _initILoginServiceProxy();
    iLoginService.changePassword(userId, currentPassword, newPassword);
  }
  
  public org.datacontract.schemas._2004._07.PrettySecureCloud_Model.CloudService addService(java.lang.Integer userId, org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType type, java.lang.String name, java.lang.String loginToken) throws java.rmi.RemoteException{
    if (iLoginService == null)
      _initILoginServiceProxy();
    return iLoginService.addService(userId, type, name, loginToken);
  }
  
  public void updateService(java.lang.Integer userId, java.lang.Integer serviceId, java.lang.String newName, java.lang.String newLoginToken) throws java.rmi.RemoteException{
    if (iLoginService == null)
      _initILoginServiceProxy();
    iLoginService.updateService(userId, serviceId, newName, newLoginToken);
  }
  
  public void removeService(java.lang.Integer userId, java.lang.Integer serviceId) throws java.rmi.RemoteException{
    if (iLoginService == null)
      _initILoginServiceProxy();
    iLoginService.removeService(userId, serviceId);
  }
  
  public org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType[] loadAllServices() throws java.rmi.RemoteException{
    if (iLoginService == null)
      _initILoginServiceProxy();
    return iLoginService.loadAllServices();
  }
  
  
}