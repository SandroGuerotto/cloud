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
    public void update(org.datacontract.schemas._2004._07.PrettySecureCloud_Model.User newUserData) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType[] loadAllServices() throws java.rmi.RemoteException;
}
