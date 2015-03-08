/*------------------------------------------------------------------------------
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/
package com.tingken.test.util;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.message.Message;
import com.tingken.test.util.ConsoleLoggingOutInterceptor.ConsoleHandler;

public class ConsoleLoggingInInterceptor extends LoggingInInterceptor {
    private static final Logger LOG = Logger.getLogger("facility.Logger.ConsoleLoggingInterceptor",
            BundleUtils.getBundleName(LoggingInInterceptor.class));
    static {
        LOG.setLevel(Level.OFF);
        for (Handler h : LOG.getHandlers()) {
            LOG.removeHandler(h);
        }
        LOG.addHandler(new ConsoleHandler());
    }

    @Override
    protected Logger getLogger() {
        return LOG;

    }
    @Override
    public void handleMessage(Message message) throws Fault {
        if (LOG.isLoggable(Level.INFO)) {
            writer = null;
            logging(LOG, message);
        }
    }

}
