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

import com.ericsson.jigsaw.oauthserver.OAuthConstants;
import com.ericsson.jigsaw.oauthserver.exception.OAuthError;
import com.ericsson.jigsaw.oauthserver.exception.OAuthErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * JsonProcessingExceptionMapper is used to convert the json parser
 * exception to bad request for client, providing more detail message
 * for user.
 * 
 * @see JsonProcessingException
 */
public class JsonProcessingExceptionMapper implements ExceptionMapper<JsonProcessingException> {

//    private Logger logger = LoggerFactory.getLogger(OAuthConstants.OAUTH_LOGGER_NAME);

    @Override
    public Response toResponse(JsonProcessingException e) {
        String message = e.getMessage();
//        logger.error(message, e);

        String displayMessage = message;
        if (message.contains("\n")) {
            String[] values = message.split("\n");
            if ((values != null) && (values.length > 0)) {
                displayMessage = values[0];
            }
        }
//        OAuthError error = new OAuthError(OAuthErrorCode.invalid_request, displayMessage);
        return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON)//.entity(error)
        		.build();
    }
}
