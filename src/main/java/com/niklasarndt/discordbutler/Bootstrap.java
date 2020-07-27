package com.niklasarndt.discordbutler;

import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Niklas on 2020/07/24.
 */
public class Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        if (System.getenv("SENTRY_DSN") != null || System.getProperty("sentry.dsn") != null) {

            Sentry.init();
            logger.info("Has Sentry been initialized correctly? {}", Sentry.isInitialized());
        } else {
            logger.info("Specify your DSN via SENTRY_DSN to enable Sentry logging.");
        }

        try {
            new Butler(args);
        } catch (Exception e) {
            logger.error("Encountered uncaught exception", e);
        }
    }
}
