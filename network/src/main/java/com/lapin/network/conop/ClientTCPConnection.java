package com.lapin.network.conop;

import com.lapin.di.context.ApplicationContext;
import com.lapin.network.listener.ClientListener;
import com.lapin.network.listener.Listenerable;
import com.lapin.network.log.NetworkLogOutputConsole;
import com.lapin.network.log.NetworkLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.Properties;

public class ClientTCPConnection implements ConnectionType {
    private SocketChannel socketChannel;
    private SocketAddress addr;
    private final NetworkLogger netLogger;
    private Properties properties;

    public ClientTCPConnection(File resources) {
        netLogger = ApplicationContext.getInstance().getBean(NetworkLogger.class);
        netLogger.setLogOutput(new NetworkLogOutputConsole());
        properties = new Properties();
        try {
            properties.load(new FileInputStream(resources));
        } catch (IOException e) {
            netLogger.error("Не удалось загрузить config");
        }
    }


    @Override
    public void openSocket() {
        try {
            socketChannel = SocketChannel.open();
        }
        catch (IOException e) {
            netLogger.error("Failed to open socket!");
        }
        try {
            InetAddress host = null;
            host = InetAddress.getLocalHost();
            if (host == null) throw new UnknownHostException();
            Integer port = Integer.parseInt(properties.getProperty("port"));
            addr = new InetSocketAddress(host, port);
        } catch (UnknownHostException e) {
            netLogger.error("Failed! Unknown host!");
        }
    }

    @Override
    public Listenerable connect() {
        int reconnectionAttempts = 1;
        boolean processingStatus = true;
        while (processingStatus) {
            if (reconnectionAttempts >= 2) netLogger.info("Reconnecting to the server...");
            else netLogger.info("Connecting to the server...");
            try {
                socketChannel = SocketChannel.open();
                socketChannel.connect(addr);
            } catch (IOException e) {
                netLogger.error("Server connection error!");
                reconnectionAttempts++;
                if (reconnectionAttempts > Integer.parseInt(properties.getProperty("MaxReconnectionAttemps"))) {
                    netLogger.error("Connection timed out!");
                }
                try {
                    netLogger.info((reconnectionAttempts) + " reconnection attempt will be made after "
                            + (Integer.parseInt(properties.getProperty("timeout")) / 1000) + " seconds.");
                    Thread.sleep(Integer.parseInt(properties.getProperty("timeout")));
                } catch (InterruptedException ex) {
                    netLogger.error("Client sleep error!");
                }
                continue;
            }
            processingStatus = false;
            netLogger.info("Client connected!");
        }
        return new ClientListener(socketChannel);
    }

}
