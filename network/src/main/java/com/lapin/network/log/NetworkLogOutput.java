package com.lapin.network.log;

public interface NetworkLogOutput {
    public void outInfo(String msg);
    public void outError(String msg);
}
