package com.powerstation.station.transport;

import com.powerstation.station.services.ProvisioningServiceImpl;
import com.smart.charging.function.Message;
import com.smart.charging.function.login.LoginRequest;
import com.smart.charging.function.login.LoginResponse;
import com.smart.charging.function.login.Result;
import com.smart.charging.function.management.EjectPowerBankResponse;
import com.smart.charging.function.query.QueryPowerBankInfoResponse;
import com.smart.charging.transport.AbstractServerMessageHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AbstractServerMessageHandlerImpl extends AbstractServerMessageHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    public static ChannelId channelId;
    public static int slotID;
//    private ProvisioningServiceImpl provisioningService;
//    public AbstractServerMessageHandlerImpl(ProvisioningServiceImpl provisioningService) {
//        this.provisioningService = provisioningService;
//    }

    @Override
    public void onIncomingConnection(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelId = channelHandlerContext.channel().id();
        System.out.println("Incoming connection " + channelHandlerContext);
        System.out.println("Incoming channel id " + channelId);
    }

    @Override
    public void onShutdownConnection(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void onMessage(Message message, ChannelHandlerContext ctx) throws Exception {
        System.out.println("Message received from device : " + message.getCmd() + "  ctx " + ctx);
        Message responseMessage = null;


        if (!message.isResponse()) {

            if (message instanceof LoginRequest) {
                LoginRequest loginRequest = (LoginRequest) message;
                LoginResponse loginResponse = loginRequest.createResponse(Result.SUCCESS);
                ctx.writeAndFlush(loginResponse);
            }

            return;

        }

        if (message instanceof QueryPowerBankInfoResponse) {
            QueryPowerBankInfoResponse queryPowerBankInfoResponse = (QueryPowerBankInfoResponse) message;
            slotID = queryPowerBankInfoResponse.getSlot();

            System.out.println(queryPowerBankInfoResponse.getLevel());
            for (int i = 0; i < queryPowerBankInfoResponse.getPowerBandId().length; i++) {

                System.out.println("bank id=>  " + i + "  " + queryPowerBankInfoResponse.getPowerBandId()[i]);
            }

            logger.info("MS_ORDER: There are "+queryPowerBankInfoResponse.getRemainNum() + " Banks in station");
            logger.info("MS_ORDER: Fetching "+queryPowerBankInfoResponse.getSlot()+" number");
            logger.info("MS_ORDER: Fetching "+queryPowerBankInfoResponse.getPowerBandId()[queryPowerBankInfoResponse.getSlot()]+" bankID");

        }else if (message instanceof EjectPowerBankResponse){

            EjectPowerBankResponse ejectPowerBankResponse = (EjectPowerBankResponse) message;
            logger.info("MS_ORDER: "+ejectPowerBankResponse.getPowerBankId()+ " is ejected");

        }

    }
}
