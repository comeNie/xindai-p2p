
package com.qfw.xfire;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "WebServiceSingleQueryOfUnzip", targetNamespace = XfireUrl.url)
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface WebServiceSingleQueryOfUnzip {


    @WebMethod(operationName = "test", action = "")
    @WebResult(name = "testReturn", targetNamespace = XfireUrl.url)
    public String test();

    @WebMethod(operationName = "testConnection", action = "")
    @WebResult(name = "testConnectionReturn", targetNamespace = XfireUrl.url)
    public String testConnection();

    @WebMethod(operationName = "download", action = "")
    @WebResult(name = "downloadReturn", targetNamespace = XfireUrl.url)
    public String download(
        @WebParam(name = "userID", targetNamespace = XfireUrl.url)
        String userID,
        @WebParam(name = "password", targetNamespace = XfireUrl.url)
        String password,
        @WebParam(name = "batNo", targetNamespace = XfireUrl.url)
        String batNo,
        @WebParam(name = "outputStyle", targetNamespace = XfireUrl.url)
        String outputStyle);

    @WebMethod(operationName = "queryReport", action = "")
    @WebResult(name = "queryReportReturn", targetNamespace = XfireUrl.url)
    public String queryReport(
        @WebParam(name = "userID", targetNamespace = XfireUrl.url)
        String userID,
        @WebParam(name = "password", targetNamespace = XfireUrl.url)
        String password,
        @WebParam(name = "queryCondition", targetNamespace = XfireUrl.url)
        String queryCondition,
        @WebParam(name = "outputStyle", targetNamespace = XfireUrl.url)
        String outputStyle);

    @WebMethod(operationName = "changePassword", action = "")
    @WebResult(name = "changePasswordReturn", targetNamespace = XfireUrl.url)
    public String changePassword(
        @WebParam(name = "userID", targetNamespace = XfireUrl.url)
        String userID,
        @WebParam(name = "oldPasswowd", targetNamespace = XfireUrl.url)
        String oldPasswowd,
        @WebParam(name = "newPassword", targetNamespace = XfireUrl.url)
        String newPassword);

}
