package com.lapin.network.obj;

import com.lapin.network.StatusCodes;

public interface RequestHandler {
    public NetObj handle(NetObj netObj);
    public StatusCodes getStatusCode();
}
