package com.lapin.network.listener;

import com.lapin.di.context.ApplicationContext;
import com.lapin.network.StatusCodes;
import com.lapin.network.log.NetworkLogOutputConsole;
import com.lapin.network.log.NetworkLogger;
import com.lapin.network.obj.NetObj;
import com.lapin.network.obj.RequestHandler;
import com.lapin.network.obj.ResponseBodyKeys;
import com.lapin.network.obj.Serializer;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.nio.channels.SelectionKey.OP_ACCEPT;

public class ServerListener implements Listenerable, Runnable{
    protected ServerSocketChannel ssc;
    private StatusCodes serverStatus = StatusCodes.OK;
    protected NetworkLogger netLogger;
    protected RequestHandler requestHandler;
    private static final int BUFFER_SIZE = 1024*10;
    protected final Map<SocketChannel, ByteBuffer> channelBuffer = new HashMap<>();
    protected Selector sel;
    protected SelectionKey key;

    public ServerListener(RequestHandler requestHandler, ServerSocketChannel ssc){
       this.requestHandler = requestHandler;
       netLogger = ApplicationContext.getInstance().getBean(NetworkLogger.class);
       netLogger.setLogOutput(new NetworkLogOutputConsole());
        this.ssc = ssc;
        try {
            sel = Selector.open();
        } catch (IOException e) {
            netLogger.error("Failed to open selector!");
        }
        try {
            ssc.configureBlocking(false);
        } catch (IOException e) {
            netLogger.error("Non-blocking mode not available!");
        }
        try {
            key = ssc.register(sel, OP_ACCEPT);
            netLogger.info("Server is ready to accept");
        } catch (ClosedChannelException e) {
            netLogger.error("Failed key registration");
        }
    }
    public void run() {
        while (!serverStatus.equals(StatusCodes.EXIT_SERVER)) {
            try {
                int numOfKeys = sel.select();
                if (numOfKeys == 0){
                    continue;
                }
                Set<SelectionKey> keys = sel.selectedKeys();
                for (var it = keys.iterator(); it.hasNext(); ) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            accept();
                        } else if (key.isReadable()) {
                            read(key);
                        } else if (key.isWritable()) {
                            write(key);
                        }
                    }
                }
            } catch (IOException e) {
                netLogger.error("Failed in listen!");
            }
        }
    }

    protected void accept() throws IOException {
        SocketChannel sc = ssc.accept();
        sc.configureBlocking(false);
        sc.register(sel, SelectionKey.OP_READ);
        channelBuffer.put(sc,ByteBuffer.allocate(0));
        netLogger.info("New session: " + sc.socket().getRemoteSocketAddress());
    }
    protected SocketAddress kill(SocketChannel channel) throws IOException {
        SocketAddress address = channel.getRemoteAddress();
        netLogger.info("Session: " + address + " closed");
        channelBuffer.remove(channel);
        channel.close();
        return address;
    }

    protected void read(SelectionKey key){
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            int bytesRead = channel.read(buffer);
            if (bytesRead == -1) {
                kill(channel);
                return;
            }
            ByteBuffer newBuffer = ByteBuffer.allocate(channelBuffer.get(channel).capacity() + bytesRead);
            newBuffer.put(channelBuffer.get(channel).array());
            newBuffer.put(ByteBuffer.wrap(buffer.array(), 0, bytesRead));
            channelBuffer.put(channel, newBuffer);
            NetObj request = (NetObj) Serializer.deserialize(channelBuffer.get(channel).array());
            NetObj response = requestHandler.handle(request);
            serverStatus = requestHandler.getStatusCode();
            this.setServerStatus((StatusCodes)response.getBody().get(ResponseBodyKeys.STATUS_CODE));
            channelBuffer.put(channel,ByteBuffer.wrap(Serializer.serialize(response)));
            channel.register(sel, SelectionKey.OP_WRITE);
            } catch (IOException e) {
                netLogger.error("Failed to process request!");
            }
    }

    protected void write(SelectionKey key){
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = channelBuffer.get(channel);
            int responseLen = 0;
            int bytesWritten = channel.write(buffer);
            responseLen += bytesWritten;
            while (buffer.hasRemaining()) {
                bytesWritten = channel.write(buffer);
                responseLen += bytesWritten;
            }
            channelBuffer.put(channel, ByteBuffer.allocate(0));
            channel.register(sel, SelectionKey.OP_READ);
        }
        catch (IOException e){
            netLogger.error("Failed to send response!");
        }
    }

    public void setServerStatus(StatusCodes serverStatus) {
        this.serverStatus = serverStatus;
    }
}
