package com.dsi.notification.filter;

import com.dsi.notification.util.Constants;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * Created by sabbir on 6/16/16.
 */
public class ResponseCORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin","*");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "*, Cache-Control, Pragma, Origin, Authorization, X-Requested-With, PUT, DELETE, GET, POST, OPTIONS");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "*, Content-Type, X-XSRF-TOKEN, " + Constants.AUTHORIZATION);
    }
}
