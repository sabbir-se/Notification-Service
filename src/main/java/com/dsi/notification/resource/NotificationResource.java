package com.dsi.notification.resource;

import com.dsi.notification.dto.NotificationDto;
import com.dsi.notification.dto.transformer.NotificationTransformer;
import com.dsi.notification.exception.CustomException;
import com.dsi.notification.model.Notification;
import com.dsi.notification.service.NotificationConfigService;
import com.dsi.notification.service.NotificationService;
import com.dsi.notification.service.impl.NotificationConfigServiceImpl;
import com.dsi.notification.service.impl.NotificationServiceImpl;
import com.dsi.notification.util.Constants;
import com.wordnik.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */

@Path("/v1")
@Api(value = "/Notification", description = "Operations about Notification Service")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class NotificationResource {

    private static final Logger logger = Logger.getLogger(NotificationResource.class);

    private static final NotificationTransformer TRANSFORMER = new NotificationTransformer();

    private static final NotificationService notificationService = new NotificationServiceImpl();
    private static final NotificationConfigService configService = new NotificationConfigServiceImpl();

    @GET
    @Path("/notification")
    @ApiOperation(value = "Read All Notification", notes = "Read All Notification", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read all notification success"),
            @ApiResponse(code = 500, message = "Read all notification failed, unauthorized.")
    })
    public Response readAllNotification(@QueryParam("typeId") String typeId,
                                        @QueryParam("typeName") String typeName,
                                        @QueryParam("templateId") String templateId,
                                        @QueryParam("systemId") String systemId,
                                        @QueryParam("notificationId") String notificationId) throws CustomException {

        logger.info("Read all notification:: start");
        return Response.ok().entity(TRANSFORMER.getAllNotificationDto(notificationService.
                getAllNotificationProcess(typeId, typeName, templateId, systemId, notificationId))).build();
    }

    @POST
    @Path("/notification")
    @ApiOperation(value = "Create Notifications", notes = "Create Notifications", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Create notifications success"),
            @ApiResponse(code = 500, message = "Create notifications failed, unauthorized.")
    })
    public Response createNotifications(@ApiParam(value = "Notification List", required = true)
                                               List<NotificationDto> notificationDtoList) throws CustomException, JSONException {

        logger.info("Notification create:: start");
        List<Notification> notificationList = TRANSFORMER.getAllNotification(notificationDtoList);

        notificationService.saveNotification(notificationList);

        return Response.ok().entity(new JSONObject().put(Constants.MESSAGE, "Success").toString()).build();
    }

    @GET
    @Path("/notification_config")
    @ApiOperation(value = "Read All Notification Config", notes = "Read All Notification Config", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read all notification config success"),
            @ApiResponse(code = 500, message = "Read all notification config failed, unauthorized.")
    })
    public Response readAllNotificationConfig(@QueryParam("typeId") String typeId,
                                              @QueryParam("typeName") String typeName,
                                              @QueryParam("configId") String configId,
                                              @QueryParam("systemId") String systemId) throws CustomException {

        logger.info("Read all notification config:: start");
        return Response.ok().entity(configService.getAllNotificationConfig(typeId, typeName, configId, systemId)).build();
    }

    @GET
    @Path("/notification_type")
    @ApiOperation(value = "Read All Notification Type", notes = "Read All Notification Type", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read all notification type success"),
            @ApiResponse(code = 500, message = "Read all notification type failed, unauthorized.")
    })
    public Response readAllNotificationType() throws CustomException {
        logger.info("Read all notification type:: start");
        return Response.ok().entity(notificationService.getAllNotificationType()).build();
    }
}
