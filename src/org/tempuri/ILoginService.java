/**
 * ILoginService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface ILoginService extends java.rmi.Remote {
    public java.lang.Boolean usernameUnique(java.lang.String username) throws java.rmi.RemoteException;
    public java.lang.Boolean emailUnique(java.lang.String mail) throws java.rmi.RemoteException;
    public void register(java.lang.String username, java.lang.String mail, java.lang.String password) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.PrettySecureCloud_Model.User login(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
    public void changePassword(java.lang.Integer userId, java.lang.String currentPassword, java.lang.String newPassword) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.PrettySecureCloud_Model.CloudService addService(java.lang.Integer userId, org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType type, java.lang.String name, java.lang.String loginToken) throws java.rmi.RemoteException;
    public void updateService(java.lang.Integer userId, java.lang.Integer serviceId, java.lang.String newName, java.lang.String newLoginToken) throws java.rmi.RemoteException;
    public void removeService(java.lang.Integer userId, java.lang.Integer serviceId) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType[] loadAllServices() throws java.rmi.RemoteException;
}
