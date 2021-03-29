package com.powerstation.station.services;

import com.powerstation.commonlibrary.OrderMessage;
import com.powerstation.station.transport.AbstractServerMessageHandlerImpl;
import com.smart.charging.function.management.EjectPowerBankRequest;
import com.smart.charging.function.query.QueryPowerBankInfoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProvisioningServiceImpl implements ProvisioningService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private TCPServer tcpServer;

    public ProvisioningServiceImpl(TCPServer tcpServer){
        this.tcpServer = tcpServer;
    }


    public void getAvailableBank(OrderMessage message) throws InterruptedException {


        QueryPowerBankInfoRequest queryPowerBankInfoRequest = new QueryPowerBankInfoRequest();
        queryPowerBankInfoRequest.setToken(456);
        queryPowerBankInfoRequest.setPackLen((short)8);
        queryPowerBankInfoRequest.setVersion(1);
        this.tcpServer.sendMessage(AbstractServerMessageHandlerImpl.channelId, queryPowerBankInfoRequest);

        Thread.sleep(2000);
        ejectPowerBank(AbstractServerMessageHandlerImpl.slotID);
    }

    public void ejectPowerBank(int slotId){
        System.out.println("Required slot id "+AbstractServerMessageHandlerImpl.slotID);

        EjectPowerBankRequest ejectPowerBankRequest = new EjectPowerBankRequest();
        ejectPowerBankRequest.setSlot(slotId);
        ejectPowerBankRequest.setToken(456);
        ejectPowerBankRequest.setPackLen((short)8);
        ejectPowerBankRequest.setVersion(1);

        this.tcpServer.sendMessage(AbstractServerMessageHandlerImpl.channelId, ejectPowerBankRequest);
    }

    public void ejectPowerBank(OrderMessage message, int bankId){

        System.out.println("Ejecting bank "+bankId);

        QueryPowerBankInfoRequest queryPowerBankInfoRequest = new QueryPowerBankInfoRequest();
        queryPowerBankInfoRequest.setToken(456);
        queryPowerBankInfoRequest.setPackLen((short)8);
        queryPowerBankInfoRequest.setVersion(1);
        this.tcpServer.sendMessage(AbstractServerMessageHandlerImpl.channelId, queryPowerBankInfoRequest);

        System.out.println("Required slot id "+AbstractServerMessageHandlerImpl.slotID);

        EjectPowerBankRequest ejectPowerBankRequest = new EjectPowerBankRequest();
        ejectPowerBankRequest.setSlot(bankId);
        ejectPowerBankRequest.setToken(456);
        ejectPowerBankRequest.setPackLen((short)8);
        ejectPowerBankRequest.setVersion(1);

        System.out.println("ejectPowerBankRequest"+ejectPowerBankRequest.toString());
        System.out.println("ejectPowerBankRequest slot   "+ejectPowerBankRequest.getSlot());

        this.tcpServer.sendMessage(AbstractServerMessageHandlerImpl.channelId, ejectPowerBankRequest);
    }

}
