package com.qfw.xfire;

public class TestXfire {

	public static void main(String[] args) {
		WebServiceSingleQueryOfUnzipServiceClient client = new WebServiceSingleQueryOfUnzipServiceClient();
		WebServiceSingleQueryOfUnzip zip = client.getWebServiceSingleQueryOfUnzip();
		//System.out.println(zip.testConnection());
		String xml = zip.queryReport("pmwsquery", "/o8FTDxgnhqP4/7SBdwEBw==", "", "xml");
		//System.out.println(xml);

	}

}
