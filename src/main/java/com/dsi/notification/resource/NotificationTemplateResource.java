package com.dsi.notification.resource;

import com.dsi.notification.dto.NotificationTemplateDto;
import com.dsi.notification.dto.transformer.NotificationTransformer;
import com.dsi.notification.exception.CustomException;
import com.dsi.notification.model.NotificationTemplate;
import com.dsi.notification.service.NotificationTemplateService;
import com.dsi.notification.service.impl.NotificationTemplateServiceImpl;
import com.dsi.notification.util.Utility;
import com.wordnik.swagger.annotations.*;
import com.wordnik.swagger.jaxrs.PATCH;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */

@Path("/v1/notification_template")
@Api(value = "/NotificationTemplate", description = "Operations about Notification Template")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class NotificationTemplateResource {

    private static final Logger logger = Logger.getLogger(NotificationTemplateResource.class);

    private static final NotificationTransformer TRANSFORMER = new NotificationTransformer();

    private static final NotificationTemplateService templateService = new NotificationTemplateServiceImpl();

    @GET
    @ApiOperation(value = "Read All Notification Template", notes = "Read All Notification Template", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read all notification template success"),
            @ApiResponse(code = 500, message = "Read all notification template failed, unauthorized.")
    })
    public Response readAllNotificationTemplate(@QueryParam("typeId") String typeId,
                                                @QueryParam("typeName") String typeName,
                                                @QueryParam("templateId") String templateId,
                                                @QueryParam("templateName") String templateName,
                                                @QueryParam("systemId") String systemId) throws CustomException {

        logger.info("Read all notification template:: start");
        return Response.ok().entity(TRANSFORMER.getAllNotificationTemplateDto(templateService.
                getAllNotificationTemplate(typeId, typeName, systemId, templateId, templateName))).build();
    }

    @POST
    @ApiOperation(value = "Create Notification Template", notes = "Create Notification Template", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Create notification template success"),
            @ApiResponse(code = 500, message = "Create notification template failed, unauthorized.")
    })
    public Response createNotificationTemplate(@ApiParam(value = "Notification Template", required = true)
                                                           NotificationTemplateDto notificationTemplateDto) throws CustomException {

        logger.info("Notification template create:: start");
        NotificationTemplate notificationTemplate = TRANSFORMER.getNotificationTemplate(notificationTemplateDto);
        templateService.saveNotificationTemplate(notificationTemplate);

        return Response.ok().entity(TRANSFORMER.getAllNotificationTemplateDto(templateService.
                getAllNotificationTemplate(null, null, null, null, null))).build();
    }

    @PUT
    @ApiOperation(value = "Update Notification Template", notes = "Update Notification Template", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update notification template success"),
            @ApiResponse(code = 500, message = "Update notification template failed, unauthorized.")
    })
    public Response updateNotificationTemplate(@ApiParam(value = "Notification Template", required = true)
                                                       List<NotificationTemplate> notificationTemplates) throws CustomException {

        logger.info("Notification template update:: start");
        templateService.updateNotificationTemplate(notificationTemplates);

        return Response.ok().entity(TRANSFORMER.getAllNotificationTemplateDto(templateService.
                getAllNotificationTemplate(null, null, null, null, null))).build();
    }

    @DELETE
    @Path("/{notification_template_id}")
    @ApiOperation(value = "Delete Notification Template", notes = "Delete Notification Template", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete notification template success"),
            @ApiResponse(code = 500, message = "Delete notification template failed, unauthorized.")
    })
    public Response deleteNotificationTemplate(@PathParam("notification_template_id") String templateId) throws CustomException {

        logger.info("Delete notification template:: start");
        templateService.deleteNotificationTemplate(templateId);

        return Response.ok().entity(null).build();
    }
}
