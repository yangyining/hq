/*                                                                 
 * NOTE: This copyright does *not* cover user programs that use HQ 
 * program services by normal system calls through the application 
 * program interfaces provided as part of the Hyperic Plug-in Development 
 * Kit or the Hyperic Client Development Kit - this is merely considered 
 * normal use of the program, and does *not* fall under the heading of 
 * "derived work". 
 *  
 * Copyright (C) [2004, 2005, 2006], Hyperic, Inc. 
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

package org.hyperic.hq.appdef.server.session;

import org.hyperic.hq.appdef.shared.ServiceTypeValue;
import org.hyperic.hq.appdef.shared.AppdefEntityConstants;

import java.util.Collection;
import java.util.HashSet;
import java.util.ArrayList;

/**
 *
 */
public class ServiceType extends AppdefResourceType
{
    private String plugin;
    private boolean isInternal;
    private ServerType serverType;
    private Collection applicationTypes = new HashSet();
    private Collection appServices = new HashSet();
    private Collection services = new ArrayList();
    private Collection serviceClusters = new ArrayList();

    /**
     * default constructor
     */
    public ServiceType()
    {
        super();
    }

    // Property accessors
    public String getPlugin()
    {
        return this.plugin;
    }

    public void setPlugin(String plugin)
    {
        this.plugin = plugin;
    }

    public boolean isIsInternal()
    {
        return this.isInternal;
    }

    /**
     * legacy EJB getter
     * @deprecated use isIsInternal() instead
     * @return
     */
    public boolean getIsInternal()
    {
        return this.isInternal;
    }

    public void setIsInternal(boolean internal)
    {
        this.isInternal = internal;
    }

    public ServerType getServerType()
    {
        return this.serverType;
    }

    public void setServerType(ServerType serverType)
    {
        this.serverType = serverType;
    }

    public Collection getApplicationTypes()
    {
        return this.applicationTypes;
    }

    public void setApplicationTypes(Collection applications)
    {
        this.applicationTypes = applications;
    }

    public Collection getAppServices()
    {
        return this.appServices;
    }

    public void setAppServices(Collection appServices)
    {
        this.appServices = appServices;
    }

    public Collection getServices()
    {
        return this.services;
    }

    public void setServices(Collection services)
    {
        this.services = services;
    }

    public Collection getServiceClusters()
    {
        return this.serviceClusters;
    }

    public void setServiceClusters(Collection serviceClusters)
    {
        this.serviceClusters = serviceClusters;
    }

    public int getAppdefType() {
        return AppdefEntityConstants.APPDEF_TYPE_SERVICE;
    }

    private ServiceTypeValue serviceTypeValue = new ServiceTypeValue();
    /**
     * legacy EJB DTO pattern
     * @deprecated use (this) ServiceType object instead
     * @return
     */
    public ServiceTypeValue getServiceTypeValue()
    {
        serviceTypeValue.setName(getName());
        serviceTypeValue.setSortName(getSortName());
        serviceTypeValue.setDescription(getDescription());
        serviceTypeValue.setPlugin(getPlugin());
        serviceTypeValue.setIsInternal(getIsInternal());
        serviceTypeValue.setId(getId());
        serviceTypeValue.setMTime(getMTime());
        serviceTypeValue.setCTime(getCTime());
        return serviceTypeValue;
    }

    /**
     * legacy DTO pattern
     * @depreacated use (this) ServiceType setters instead
     * @param val
     */
    public void setServiceTypeValue(ServiceTypeValue val)
    {
        setName( val.getName() );
        setSortName( val.getSortName() );
        setDescription( val.getDescription() );
        setPlugin( val.getPlugin() );
        setIsInternal( val.getIsInternal() );
        setModifiedTime(val.getMTime());
        setCreationTime(val.getCTime());
    }

    public boolean equals(Object obj)
    {
        return (obj instanceof ServiceType) && super.equals(obj);
    }
}
