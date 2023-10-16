package com.lapin.network.conop;

import com.lapin.network.listener.Listenerable;

import java.nio.channels.spi.AbstractSelectableChannel;

public interface ConnectionType {
    public void openSocket();
    public Listenerable connect();

}
