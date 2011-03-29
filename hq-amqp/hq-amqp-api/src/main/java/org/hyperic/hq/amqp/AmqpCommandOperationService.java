/*
 * NOTE: This copyright does *not* cover user programs that use HQ
 * program services by normal system calls through the application
 * program interfaces provided as part of the Hyperic Plug-in Development
 * Kit or the Hyperic Client Development Kit - this is merely considered
 * normal use of the program, and does *not* fall under the heading of
 * "derived work".
 *
 * Copyright (C) [2009-2010], VMware, Inc.
 * This file is part of HQ.
 *
 * HQ is free software; you can redistribute it and/or modify
 * it under the terms version 2 of the GNU General Public License as
 * published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA.
 */

package org.hyperic.hq.amqp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperic.hq.agent.FileData;
import org.hyperic.hq.agent.FileDataResult;
import org.hyperic.hq.agent.client.AgentCommandsClient;
import org.hyperic.hq.amqp.util.Operations;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;


/**
 * Created by the client factory on the server and AgentClient on the Agent.
 * Temporary to handle legacy command strategy.
 * TODO error strategy
 * TODO route to agents.authToken.operation
 * @author Helena Edelson
 */
public class AmqpCommandOperationService implements AgentCommandsClient {

    protected final Log logger = LogFactory.getLog(this.getClass());

    /** temporary for migration */
    protected AgentAmqpOperationService agentOperationService;

    protected boolean unidirectional;

    /**
     * temporary: the legacy client impl
     */
    protected OperationService operationService;

    /**
     * The implementation for Server, Agent, and AgentHandler
     */
    protected AgentCommandsClient legacyClient;
     
    /**
     * temporary: for the legacy Agent constructor
     * @param legacyClient
     */
    public AmqpCommandOperationService(AgentCommandsClient legacyClient) {
        this(new AgentAmqpOperationService(), legacyClient, false);
    }

    /**
     * Temporary: for the legacy Server constructor
     * @param operationService    pre-configured operation service
     * @param legacyClient        the legacy client implementation
     * @param agentUnidirectional currently just used for the server ping operation
     */
    public AmqpCommandOperationService(OperationService operationService, AgentCommandsClient legacyClient, boolean agentUnidirectional) {
        this.operationService = operationService;
        this.unidirectional = agentUnidirectional;
        this.legacyClient = legacyClient;
        this.agentOperationService = new AgentAmqpOperationService();
    }

    public AgentAmqpOperationService getTemplate() {
        return agentOperationService;
    }

    /**
     * The first to be overridden.
     * Do we really need to return a duration during the transition?
     * @return duration
     * @see org.hyperic.hq.agent.client.AgentCommandsClient#ping()
     */
    public long ping() { 
        logger.info("***.ping()");
        if (unidirectional) return 0;

        try {
            agentOperationService.agentPing();
            //return (legacyClient instanceof LegacyAgentCommandsClientImpl) ? template.agentPing() : template.ping();
        }
        catch (Exception e) {
            handleException(e, Operations.PING);
        } 
        return 0;
    }

    public void timedPing(int append) throws IOException, InterruptedException {
        agentOperationService.timedPing(append);
    }


    public void restart() {
        try {
            legacyClient.restart();
        } catch (Exception e) {
            handleException(e, Operations.RESTART);
        }
    }

    public void die() {
        try {
            legacyClient.die();
        } catch (Exception e) {
            handleException(e, Operations.DIE);
        }
    }

    public String getCurrentAgentBundle() {
        try {
            return legacyClient.getCurrentAgentBundle();
        } catch (Exception e) {
            handleException(e, Operations.GET_AGENT_BUNDLE);
            return null;
        }
    }

    public Map upgrade(String tarFile, String destination) {
        try {
            return legacyClient.upgrade(tarFile, destination);
        } catch (Exception e) {
            handleException(e, Operations.UPGRADE);
            return null;
        }
    }

    public FileDataResult[] agentSendFileData(FileData[] destFiles, InputStream[] streams) {
        try {
            return legacyClient.agentSendFileData(destFiles, streams);
        } catch (Exception e) {
            handleException(e, Operations.SEND_FILE);
            return null;
        }
    }

    public Map<String, Boolean> agentRemoveFile(Collection<String> files) {
        try {
            return legacyClient.agentRemoveFile(files);
        } catch (Exception e) {
            handleException(e, Operations.REMOVE_FILE);
            return null;
        }
    }

    /**
     * Temporary strategy until legacy remoting etc is replaced and
     * until an API exception handling strategy is created.
     * Currently, AgentRemoteException or AgentConnectionException can
     * be thrown from legacy implementations.
     */
    protected void handleException(Throwable t, String operation) {
        logger.error(t.getClass().getSimpleName() + " thrown while executing " + operation, t);
    }



    /* if (unidirectional) return 0;
        logger.info("***********.ping()");
        long sendTime = System.currentTimeMillis();
        long duration = 0;

        try {

            logger.info("Sending " + Operations.PING);
            operationService.send(Operations.PING);
            duration = System.currentTimeMillis() - sendTime;
            logger.info("***********ping() executed, returning duration=" + duration);

        } catch (Exception e) {
            handleException(e, Operations.PING);
        }
        return duration;*/
}