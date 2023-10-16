package com.lapin.network.log;

public class NetworkLogger{
    private NetworkLogOutput nlo;
    public NetworkLogger(){
        this.nlo = new NetworkLogOutputNull();
    }
    public NetworkLogger(NetworkLogOutput nlo){
        this.nlo = nlo;
    }
    public void info(String msg) {
        nlo.outInfo(msg);
    }

    public void error(String msg) {
        nlo.outError(msg);
    }
    public void setLogOutput(NetworkLogOutput nlo){
        this.nlo = nlo;
    }
}
