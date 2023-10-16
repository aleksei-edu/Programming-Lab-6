package com.lapin.server;

import com.lapin.common.network.objimp.RequestCommand;
import com.lapin.common.network.objimp.ResponseCommand;
import com.lapin.common.utility.OutManager;
import com.lapin.common.utility.Pair;
import com.lapin.network.StatusCodes;
import com.lapin.network.obj.NetObj;
import com.lapin.network.obj.RequestHandler;
import com.lapin.common.utility.CommandManager;

public class ServerRequestHandler implements RequestHandler {
    private StatusCodes statusCodes;
    @Override
    public NetObj handle(NetObj netObj) {
        if(netObj != null){
            RequestCommand rc = (RequestCommand) netObj;
            CommandManager.execute(rc.getCmd(), rc.getArg(), rc.argObj());
            Pair response = OutManager.pop();
            statusCodes = (StatusCodes) response.getFirst();
            return new ResponseCommand(statusCodes,(String) response.getSecond());
        }
        return null;
    }

    @Override
    public StatusCodes getStatusCode() {
        return statusCodes;
    }
}
