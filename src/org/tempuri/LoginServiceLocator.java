/**
 * LoginServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class LoginServiceLocator extends org.apache.axis.client.Service implements org.tempuri.LoginService {

    public LoginServiceLocator() {
    }


    public LoginServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LoginServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_ILoginService
    private java.lang.String BasicHttpBinding_ILoginService_address = "http://prettysecurecloud.azurewebsites.net/LoginService.svc";

    public java.lang.String getBasicHttpBinding_ILoginServiceAddress() {
        return BasicHttpBinding_ILoginService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_ILoginServiceWSDDServiceName = "BasicHttpBinding_ILoginService";

    public java.lang.String getBasicHttpBinding_ILoginServiceWSDDServiceName() {
        return BasicHttpBinding_ILoginServiceWSDDServiceName;
    }

    public void setBasicHttpBinding_ILoginServiceWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_ILoginServiceWSDDServiceName = name;
    }

    public org.tempuri.ILoginService getBasicHttpBinding_ILoginService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_ILoginService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_ILoginService(endpoint);
    }

    public org.tempuri.ILoginService getBasicHttpBinding_ILoginService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.BasicHttpBinding_ILoginServiceStub _stub = new org.tempuri.BasicHttpBinding_ILoginServiceStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_ILoginServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_ILoginServiceEndpointAddress(java.lang.String address) {
        BasicHttpBinding_ILoginService_address = address;
    }


    // Use to get a proxy class for BasicHttpsBinding_ILoginService
    private java.lang.String BasicHttpsBinding_ILoginService_address = "https://prettysecurecloud.azurewebsites.net/LoginService.svc";

    public java.lang.String getBasicHttpsBinding_ILoginServiceAddress() {
        return BasicHttpsBinding_ILoginService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpsBinding_ILoginServiceWSDDServiceName = "BasicHttpsBinding_ILoginService";

    public java.lang.String getBasicHttpsBinding_ILoginServiceWSDDServiceName() {
        return BasicHttpsBinding_ILoginServiceWSDDServiceName;
    }

    public void setBasicHttpsBinding_ILoginServiceWSDDServiceName(java.lang.String name) {
        BasicHttpsBinding_ILoginServiceWSDDServiceName = name;
    }

    public org.tempuri.ILoginService getBasicHttpsBinding_ILoginService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpsBinding_ILoginService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpsBinding_ILoginService(endpoint);
    }

    public org.tempuri.ILoginService getBasicHttpsBinding_ILoginService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.BasicHttpsBinding_ILoginServiceStub _stub = new org.tempuri.BasicHttpsBinding_ILoginServiceStub(portAddress, this);
            _stub.setPortName(getBasicHttpsBinding_ILoginServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpsBinding_ILoginServiceEndpointAddress(java.lang.String address) {
        BasicHttpsBinding_ILoginService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.ILoginService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.BasicHttpBinding_ILoginServiceStub _stub = new org.tempuri.BasicHttpBinding_ILoginServiceStub(new java.net.URL(BasicHttpBinding_ILoginService_address), this);
                _stub.setPortName(getBasicHttpBinding_ILoginServiceWSDDServiceName());
                return _stub;
            }
            if (org.tempuri.ILoginService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.BasicHttpsBinding_ILoginServiceStub _stub = new org.tempuri.BasicHttpsBinding_ILoginServiceStub(new java.net.URL(BasicHttpsBinding_ILoginService_address), this);
                _stub.setPortName(getBasicHttpsBinding_ILoginServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_ILoginService".equals(inputPortName)) {
            return getBasicHttpBinding_ILoginService();
        }
        else if ("BasicHttpsBinding_ILoginService".equals(inputPortName)) {
            return getBasicHttpsBinding_ILoginService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "LoginService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_ILoginService"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpsBinding_ILoginService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_ILoginService".equals(portName)) {
            setBasicHttpBinding_ILoginServiceEndpointAddress(address);
        }
        else 
if ("BasicHttpsBinding_ILoginService".equals(portName)) {
            setBasicHttpsBinding_ILoginServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
