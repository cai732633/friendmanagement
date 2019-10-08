package com.spgroup.test.Controller;

import com.spgroup.test.Common.CommonResponse;
import com.spgroup.test.Data.*;
import com.spgroup.test.Service.BlockManagementService;
import com.spgroup.test.Service.SubscriptionManagementService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Log4j
public class SubscriptionController {

    @Autowired
    SubscriptionManagementService subscriptionManagementService;

    @Autowired
    BlockManagementService blockManagementService;

    @PostMapping(value = "/subscription")
    public ResponseEntity<CommonResponse> subscription(@RequestBody SubscriptionRequest subscriptionRequest){
        if ((subscriptionRequest.getRequestor()==null)||(subscriptionRequest.getTarget()==null))
        {
            throw new RuntimeException("parameter missing, pls input Requestor and target value !");
        }

        subscriptionManagementService.subscription(subscriptionRequest);

        return new ResponseEntity<>(new CommonResponse(true), HttpStatus.OK);
    }

    @PostMapping(value = "/block")
    public ResponseEntity<CommonResponse> block(@RequestBody BlockRequest blockRequest){
        if ((blockRequest.getRequestor()==null)||(blockRequest.getTarget()==null))
        {
            throw new RuntimeException("parameter missing, pls input Requestor and target value !");
        }

        blockManagementService.block(blockRequest);

        return new ResponseEntity<>(new CommonResponse(true), HttpStatus.OK);
    }

}
