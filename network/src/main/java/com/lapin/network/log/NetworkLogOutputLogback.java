package com.lapin.network.log;

import org.slf4j.Logger;

public class NetworkLogOutputLogback implements NetworkLogOutput{
    private final Logger logger;
    public NetworkLogOutputLogback(Logger logger){
        this.logger = logger;
    }
    @Override
    public void outInfo(String msg) {
        logger.info(msg);
    }

    @Override
    public void outError(String msg) {
        logger.error(msg);
    }
}
