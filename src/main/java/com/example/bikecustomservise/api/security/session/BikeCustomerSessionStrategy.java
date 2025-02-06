package com.example.bikecustomservise.api.security.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface BikeCustomerSessionStrategy {

    void sendCustomerRedirect(HttpServletRequest request, HttpServletResponse response, String customUrl) throws IOException;
}
