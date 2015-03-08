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
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

public class ConsoleLoggingOutInterceptor extends LoggingOutInterceptor {
    private static final Logger LOG = Logger.getLogger("facility.Logger.ConsoleLoggingInterceptor",
            BundleUtils.getBundleName(LoggingOutInterceptor.class));
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

    public static class ConsoleHandler extends StreamHandler {

        /**
         * Create a <tt>ConsoleHandler</tt> for <tt>System.err</tt>.
         * <p>
         * The <tt>ConsoleHandler</tt> is configured based on
         * <tt>LogManager</tt> properties (or their default values).
         *
         */
        public ConsoleHandler() {
            configure();
            setOutputStream(System.out);
        }

        /**
         * Publish a <tt>LogRecord</tt>.
         * <p>
         * The logging request was made initially to a <tt>Logger</tt>
         * object, which initialized the <tt>LogRecord</tt> and
         * forwarded it here.
         * <p>
         *
         * @param record description of the log event. A null record
         *            is silently ignored and is not published
         */
        @Override
        public void publish(LogRecord record) {
            super.publish(record);
            flush();
        }

        /**
         * Override <tt>StreamHandler.close</tt> to do a flush but not
         * to close the output stream. That is, we do <b>not</b> close
         * <tt>System.err</tt>.
         */
        @Override
        public void close() {
            flush();
        }

        private void configure() {
            setLevel(Level.ALL);
            setFilter(null);
            setFormatter(new SimpleFormatter());
        }
    }

}
