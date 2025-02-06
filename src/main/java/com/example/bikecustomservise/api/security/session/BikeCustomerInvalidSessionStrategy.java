package com.example.bikecustomservise.api.security.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class BikeCustomerInvalidSessionStrategy implements InvalidSessionStrategy {

    private static final String DESTINATION_URL = "http://" + "\"^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$\"" + "/rest/redirect";
    private final BikeCustomerSessionStrategy sessionStrategy = new BikeCustomerSessionStrategyImpl();

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("Starting new session (if required) and redirecting to" + DESTINATION_URL);
        }

        this.sessionStrategy.sendCustomerRedirect(request, response, DESTINATION_URL);

    }
}
