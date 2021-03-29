package com.powerstation.station.controller;

import com.powerstation.commonlibrary.OrderMessage;
import com.powerstation.station.services.ProvisioningServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class ProvisioningController {

    @Autowired
    ProvisioningServiceImpl provisioningService;


    @GetMapping("/operation/{bankID}/eject")
public void test(@PathVariable int bankID){

        System.out.println("****************  =>  "+bankID);

        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setSubscriptionId(3);
        orderMessage.setOrderId(3);
        provisioningService.ejectPowerBank(orderMessage, bankID);

    }

}
