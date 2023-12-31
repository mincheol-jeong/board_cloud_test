// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.cloudstack.api.command.admin.request;

import javax.inject.Inject;

import org.apache.cloudstack.acl.RoleType;
import org.apache.cloudstack.api.APICommand;
import org.apache.cloudstack.api.ApiConstants;
import org.apache.cloudstack.api.BaseListCmd;
import org.apache.cloudstack.api.Parameter;
import org.apache.cloudstack.api.ResponseObject;
import org.apache.cloudstack.api.ServerApiException;
import org.apache.cloudstack.api.response.ResourceRequestResponse;
import org.apache.cloudstack.api.response.ListResponse;
import org.apache.cloudstack.api.response.ZoneResponse;
import org.apache.log4j.Logger;
import org.apache.cloudstack.context.CallContext;

import com.cloud.exception.ConcurrentOperationException;
import com.cloud.request.ResourceRequestService;

@APICommand(name = ListResourceRequestCmd.APINAME,
        description = "Lists Resource Request Status",
        responseObject = ResourceRequestResponse.class,
        responseView = ResponseObject.ResponseView.Restricted,
        authorized = {RoleType.Admin, RoleType.ResourceAdmin, RoleType.DomainAdmin, RoleType.User})
public class ListResourceRequestCmd extends BaseListCmd {
    public static final Logger LOGGER = Logger.getLogger(ListResourceRequestCmd.class.getName());
    public static final String APINAME = "listResourceRequest";

    @Inject
    private ResourceRequestService resourceRequestService;

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////
    @Parameter(name = ApiConstants.NAME, type = CommandType.STRING,
    description = "name of the Resource Request(a substring match is made against the parameter value, data for all matching Desktop will be returned)")
    private String name;

    @Parameter(name = ApiConstants.ID, type = CommandType.UUID,
            entityType = ResourceRequestResponse.class,
            description = "the ID of the Desktop Master Version")
    private Long id;

    @Parameter(name = ApiConstants.ZONE_ID, type = CommandType.UUID,
            entityType = ZoneResponse.class,
            description = "the ID of the zone in which Desktop Master Version will be available")
    private Long zoneId;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getZoneId() {
        return zoneId;
    }

    @Override
    public String getCommandName() {
        return APINAME.toLowerCase() + "response";
    }

    @Override
    public long getEntityOwnerId() {
        return CallContext.current().getCallingAccountId();
    }
    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////
    @Override
    public void execute() throws ServerApiException, ConcurrentOperationException {
        ListResponse<ResourceRequestResponse> response = resourceRequestService.listResourceRequest(this);
        response.setResponseName(getCommandName());
        setResponseObject(response);
    }
}

