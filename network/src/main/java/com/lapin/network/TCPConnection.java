package com.lapin.network;

import com.lapin.network.conop.ConnectionType;
import com.lapin.network.listener.Listenerable;

import java.nio.channels.spi.AbstractSelectableChannel;

public class TCPConnection {
    private AbstractSelectableChannel socket;
    private ConnectionType connection;
    public TCPConnection(ConnectionType connection){
        this.connection = connection;
    }
    public Listenerable start(){
        connection.openSocket();
        return connection.connect();
    }

}
