
package com.qfw.xfire;

import javax.jws.WebService;

@WebService(serviceName = "WebServiceSingleQueryOfUnzipService", targetNamespace = XfireUrl.url, endpointInterface = "com.qfw.xfire.WebServiceSingleQueryOfUnzip")
public class WebServiceSingleQueryOfUnzipServiceImpl
    implements WebServiceSingleQueryOfUnzip
{


    public String test() {
        throw new UnsupportedOperationException();
    }

    public String testConnection() {
        throw new UnsupportedOperationException();
    }

    public String download(String userID, String password, String batNo, String outputStyle) {
        throw new UnsupportedOperationException();
    }

    public String queryReport(String userID, String password, String queryCondition, String outputStyle) {
        throw new UnsupportedOperationException();
    }

    public String changePassword(String userID, String oldPasswowd, String newPassword) {
        throw new UnsupportedOperationException();
    }

}
