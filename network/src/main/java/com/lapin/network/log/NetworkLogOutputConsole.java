package com.lapin.network.log;

public class NetworkLogOutputConsole implements NetworkLogOutput{
    @Override
    public void outInfo(String msg) {
        System.out.println("INFO: "+ msg);
    }

    @Override
    public void outError(String msg) {
        System.err.println("ERROR: "+ msg);
    }
}
