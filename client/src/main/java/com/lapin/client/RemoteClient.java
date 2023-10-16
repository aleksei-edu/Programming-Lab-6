package com.lapin.client;

import com.lapin.common.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class RemoteClient {
    public static void main(String[] args) throws URISyntaxException {
        File resources = new File(RemoteClient
                .class
                .getClassLoader()
                .getResource("config.properties")
                .toURI());
        Client user = new Client(resources);
        user.run();
    }
}
