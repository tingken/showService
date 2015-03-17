/*------------------------------------------------------------------------------
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/
package com.tingken.test.api;

import java.io.File;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import com.tingken.test.exception.ShowServiceException;


/**
 * Interface for Oneapi send MMS.
 * 
 * @author ehuahao
 */
@Path("/showService")
public interface ShowService {

    /**
     * Send an MMS message to one or more mobile terminals.
     * 
     * @param senderAddress address to whom a responding SMS may be
     *            sent
     * @param request The first part of the request multipart body
     *            with name "root-fields", content type is
     *            "application/json", the content is the JSON string
     *            of the outbound message request.
     * @param attachments The second part of the request multipart
     *            body with name "attachments", the content is a
     *            multipart too, contains multiple attachments.
     * @return {@link Response}
     * @throws ServiceFailureException
     */
    @POST
    @Path("/uploadCapture")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response uploadCapture(@PathParam("deviceId")
    String deviceId, MultipartBody body) throws ShowServiceException;
    

    @GET
    @Path("/authenticate")
    @Produces(MediaType.APPLICATION_JSON)
	Response authenticate(@QueryParam("regNum") String authCode,
			@QueryParam("deviceId") String deviceId,
			@QueryParam("dimension") String dimension)
			throws ShowServiceException;

    @GET
    @Path("/heartBeat")
    @Produces(MediaType.APPLICATION_JSON)
    ServerCommand heartBeat(@QueryParam("deviceId")String deviceId);

    @GET
    @Path("/latestVersion")
    @Produces(MediaType.APPLICATION_JSON)
    VersionInfo getLatestVersion();

	boolean uploadScreen(String authCode, Date captureTime, File capture);
}
