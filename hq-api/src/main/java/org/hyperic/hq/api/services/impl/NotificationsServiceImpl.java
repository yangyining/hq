package org.hyperic.hq.api.services.impl;

import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperic.hq.api.model.NotificationsReport;
import org.hyperic.hq.api.services.NotificationsService;
import org.hyperic.hq.api.transfer.NotificationsTransfer;
import org.hyperic.hq.api.transfer.mapping.ExceptionToErrorCodeMapper;
import org.hyperic.hq.auth.shared.SessionNotFoundException;
import org.hyperic.hq.auth.shared.SessionTimeoutException;
import org.hyperic.hq.notifications.UnregisteredException;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationsServiceImpl extends RestApiService implements NotificationsService {
    private final Log log = LogFactory.getLog(NotificationsServiceImpl.class);
    @Autowired
    protected NotificationsTransfer notificationsTransfer;
    @Autowired
    protected ExceptionToErrorCodeMapper errorHandler ; 

    public NotificationsReport poll(Long id) throws SessionNotFoundException, SessionTimeoutException {
        try {
            ApiMessageContext apiMessageContext = newApiMessageContext();
            return notificationsTransfer.poll(id, apiMessageContext);
        } catch (UnregisteredException e) {
            errorHandler.log(e);
            throw errorHandler.newWebApplicationException(new Throwable(), Response.Status.NOT_FOUND,
                ExceptionToErrorCodeMapper.ErrorCode.UNREGISTERED_FOR_NOTIFICATIONS, e.getMessage());
        }
    }

    public void unregister(Long id) throws SessionNotFoundException, SessionTimeoutException {
        notificationsTransfer.unregister(id);
    }

    // temporary method for testing
    public String post(String message) throws SessionNotFoundException, SessionTimeoutException {
        if (log.isDebugEnabled()) {
            log.debug("received post body=" + message);
        }
        return new Integer(message.length()).toString();
    }
    
}