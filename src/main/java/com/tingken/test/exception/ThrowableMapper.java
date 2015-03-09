/*------------------------------------------------------------------------------
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/
package com.tingken.test.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.ericsson.jigsaw.oauthserver.exception.OAuthError;
//import com.ericsson.jigsaw.oauthserver.exception.OAuthErrorCode;
//import com.ericsson.jigsaw.oauthserver.exception.OAuthErrorDescriptionConstants;

/**
 * Convert all other exceptions to json format OAuthError. The http
 * status code is 500 and the error code is server_error.
 */
public class ThrowableMapper implements ExceptionMapper<Throwable> {


    @Override
    public Response toResponse(Throwable e) {

//        OAuthError error = new OAuthError(OAuthErrorCode.server_error,
//                OAuthErrorDescriptionConstants.INTERNAL_SERVER_ERROR);
        return Response.status(Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON)//.entity(error)
        		.build();
    }

}
