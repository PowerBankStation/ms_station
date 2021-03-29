package com.powerstation.station.services;

import com.smart.charging.function.Message;
import com.smart.charging.transport.AbstractServerMessageHandler;
import com.smart.charging.transport.server.Server;
import io.netty.channel.ChannelId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class TCPServer {


    AbstractServerMessageHandler abstractServerMessageHandler;

    public Server server;
    public ChannelId channelId;


    public TCPServer(AbstractServerMessageHandler abstractServerMessageHandler,
                     @Value("${iccid.server.ip}") String ipAddress,
                     @Value("${iccid.server.port}") int port) {

        this.abstractServerMessageHandler = abstractServerMessageHandler;
        System.out.println(ipAddress + " - " + port);
        server = new Server(ipAddress, port);

        Runnable runnable = () -> {
            server.setHandler(abstractServerMessageHandler);
            try {
                server.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }


    public void sendMessage(ChannelId channelId, Message message) {

        try {
            System.out.println("destination channel id "+channelId);
            server.send(channelId, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
