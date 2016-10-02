/**
 * LoginService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface LoginService extends javax.xml.rpc.Service {
    public java.lang.String getBasicHttpBinding_ILoginServiceAddress();

    public org.tempuri.ILoginService getBasicHttpBinding_ILoginService() throws javax.xml.rpc.ServiceException;

    public org.tempuri.ILoginService getBasicHttpBinding_ILoginService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getBasicHttpsBinding_ILoginServiceAddress();

    public org.tempuri.ILoginService getBasicHttpsBinding_ILoginService() throws javax.xml.rpc.ServiceException;

    public org.tempuri.ILoginService getBasicHttpsBinding_ILoginService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
