package com.liaison.commons.util.settings;

import com.netflix.karyon.server.InitializationPhaseInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;

/**
 * interceptor that should be registered into the karyon bootstrap to spark configuration
 * @author jeremyfranklin-ross
 *
 */
class LiaisonArchaiusIntegrationInterceptor implements InitializationPhaseInterceptor {

    protected static final Logger logger = LoggerFactory.getLogger(LiaisonArchaiusIntegrationInterceptor.class);

    @Override
    public void onPhase(Phase phase) {
    	LiaisonConfigurationFactory.getConfiguration(); //warm up factory.
    }

    @Override
    public EnumSet<Phase> interestedIn() {
        return EnumSet.of(Phase.OnCreate);
    }
}
