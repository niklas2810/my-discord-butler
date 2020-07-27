package com.niklasarndt.discordbutler;

import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

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

            new Butler(buildEnvironmentFlags(args));
        } catch (Exception e) {
            logger.error("Encountered uncaught exception", e);
        }
    }

    private static String[] buildEnvironmentFlags(String[] args) {
        String[] envs = Optional.ofNullable(System.getenv("EXECUTION_FLAGS"))
                .orElse("").split(",");
        boolean oneOnly = envs.length == 0 || args.length == 0;

        String[] combined;
        if (oneOnly) combined = envs.length != 0 ? envs : args;
        else combined = new String[envs.length + args.length];

        if (!oneOnly) {
            System.arraycopy(envs, 0, combined, 0, envs.length);
            System.arraycopy(args, 0, combined, envs.length, args.length);
        }
        return combined;
    }
}
