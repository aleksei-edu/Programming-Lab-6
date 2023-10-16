package com.lapin.server;


import com.lapin.common.client.Client;
import com.lapin.common.utility.CollectionManager;
import com.lapin.common.utility.CommandManager;
import com.lapin.common.utility.FileManager;
import com.lapin.di.context.ApplicationContext;
import com.lapin.di.factory.BeanFactory;
import com.lapin.network.TCPConnection;
import com.lapin.network.conop.ServerTCPConnection;
import com.lapin.network.listener.ServerListener;
import com.lapin.server.utility.JavaCollectionManager;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class App {
    public static void main(String[] args) throws URISyntaxException {
        BeanFactory beanFactory = new BeanFactory(ApplicationContext.getInstance());
        ApplicationContext.getInstance().setBeanFactory(beanFactory);
        FileManager fileManager = new FileManager();
        fileManager.setEnv("LABA");
        CollectionManager collectionManager = new JavaCollectionManager(fileManager);
        CommandManager.getInstance().setCollectionManager(collectionManager);
        FileManager.setCollectionManager(collectionManager);
        CommandManager.getInstance().setFileManager(fileManager);
        collectionManager.loadCollection();
        URL config = App.class.getClassLoader().getResource("config.properties");
        File resources = new File(config.toURI());
        TCPConnection server = new TCPConnection(new ServerTCPConnection(new ServerRequestHandler(),resources));
        ServerListener serverListener = (ServerListener) server.start();
        Thread serverThread = new Thread(serverListener);
        Client admin = new Client(resources,serverListener);
        Thread adminSession = new Thread(admin);
        adminSession.start();
        serverThread.start();
    }
}
