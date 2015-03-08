/**
 * 
 */
package com.tingken.test.impl;

import java.io.File;
import java.util.Date;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import com.tingken.test.api.AuthResult;
import com.tingken.test.api.ServerCommand;
import com.tingken.test.api.ShowService;
import com.tingken.test.api.ShowServiceException;

/**
 * @author Administrator
 *
 */
public class ShowServiceImpl implements ShowService {

	/* (non-Javadoc)
	 * @see com.tingken.test.api.ShowService#uploadCapture(java.lang.String, org.apache.cxf.jaxrs.ext.multipart.MultipartBody)
	 */
	public Response uploadCapture(String senderAddress, MultipartBody body)
			throws ShowServiceException {
		return Response.ok().build();
	}

	public Response authenticate(String authCode, String dimension)
			throws ShowServiceException {
		AuthResult result = new AuthResult();
		result.setAuthSuccess(true);
		result.setShowPageAddress("www.sohu.com");
		return Response.ok(result).build();
	}

	public ServerCommand heartBeat(String authCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean uploadScreen(String authCode, Date captureTime, File capture) {
		// TODO Auto-generated method stub
		return false;
	}

}
