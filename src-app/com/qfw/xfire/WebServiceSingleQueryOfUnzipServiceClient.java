
package com.qfw.xfire;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.namespace.QName;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

public class WebServiceSingleQueryOfUnzipServiceClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;

    public WebServiceSingleQueryOfUnzipServiceClient() {
        create0();
        Endpoint WebServiceSingleQueryOfUnzipLocalEndpointEP = service0.addEndpoint(new QName(XfireUrl.url, "WebServiceSingleQueryOfUnzipLocalEndpoint"), new QName(XfireUrl.url, "WebServiceSingleQueryOfUnzipLocalBinding"), "xfire.local://WebServiceSingleQueryOfUnzipService");
        endpoints.put(new QName(XfireUrl.url, "WebServiceSingleQueryOfUnzipLocalEndpoint"), WebServiceSingleQueryOfUnzipLocalEndpointEP);
        Endpoint WebServiceSingleQueryOfUnzipEP = service0.addEndpoint(new QName(XfireUrl.url, "WebServiceSingleQueryOfUnzip"), new QName(XfireUrl.url, "WebServiceSingleQueryOfUnzipSoapBinding"), XfireUrl.url);
        endpoints.put(new QName(XfireUrl.url, "WebServiceSingleQueryOfUnzip"), WebServiceSingleQueryOfUnzipEP);
    }

    public Object getEndpoint(Endpoint endpoint) {
        try {
            return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
        } catch (MalformedURLException e) {
            throw new XFireRuntimeException("Invalid URL", e);
        }
    }

    public Object getEndpoint(QName name) {
        Endpoint endpoint = ((Endpoint) endpoints.get((name)));
        if ((endpoint) == null) {
            throw new IllegalStateException("No such endpoint!");
        }
        return getEndpoint((endpoint));
    }

    public Collection getEndpoints() {
        return endpoints.values();
    }

    private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap props = new HashMap();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service0 = asf.create((com.qfw.xfire.WebServiceSingleQueryOfUnzip.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName(XfireUrl.url, "WebServiceSingleQueryOfUnzipLocalBinding"), "urn:xfire:transport:local");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName(XfireUrl.url, "WebServiceSingleQueryOfUnzipSoapBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
    }

    public WebServiceSingleQueryOfUnzip getWebServiceSingleQueryOfUnzipLocalEndpoint() {
        return ((WebServiceSingleQueryOfUnzip)(this).getEndpoint(new QName(XfireUrl.url, "WebServiceSingleQueryOfUnzipLocalEndpoint")));
    }

    public WebServiceSingleQueryOfUnzip getWebServiceSingleQueryOfUnzipLocalEndpoint(String url) {
        WebServiceSingleQueryOfUnzip var = getWebServiceSingleQueryOfUnzipLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public WebServiceSingleQueryOfUnzip getWebServiceSingleQueryOfUnzip() {
        return ((WebServiceSingleQueryOfUnzip)(this).getEndpoint(new QName(XfireUrl.url, "WebServiceSingleQueryOfUnzip")));
    }

    public WebServiceSingleQueryOfUnzip getWebServiceSingleQueryOfUnzip(String url) {
        WebServiceSingleQueryOfUnzip var = getWebServiceSingleQueryOfUnzip();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

}
