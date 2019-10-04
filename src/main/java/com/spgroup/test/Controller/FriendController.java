package com.spgroup.test.Controller;

import com.spgroup.test.Common.CommonResponse;

import com.spgroup.test.Data.BlockRequest;
import com.spgroup.test.Data.FriendGetRequest;
import com.spgroup.test.Data.FriendRequest;
import com.spgroup.test.Data.SubscriptionRequest;
import com.spgroup.test.Service.BlockManagementService;
import com.spgroup.test.Service.FriendManagementService;
import com.spgroup.test.Service.SubscriptionManagementService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j
public class FriendController {

    @Autowired
    FriendManagementService friendManagementService;

    @Autowired
    SubscriptionManagementService subscriptionManagementService;

    @Autowired
    BlockManagementService blockManagementService;

    @PostMapping(value = "/friend/add")
    public ResponseEntity<CommonResponse> create(@RequestBody FriendRequest friendRequest){
        if (friendRequest.getFriends()==null)
        {
            throw new RuntimeException("friend parameter is null, pls assign correct value !");
        }
        if (!(friendRequest.getFriends() instanceof ArrayList) ||(friendRequest.getFriends().size()!=2))
        {
            throw new RuntimeException("friend content is invalid, pls input 2 email addresses !");
        }

       friendManagementService.create(friendRequest);

       return new ResponseEntity<>(new CommonResponse(true), HttpStatus.OK);
    }

    @PostMapping(value = "/friend/get")
    public ResponseEntity<CommonResponse> get(@RequestBody FriendGetRequest friendGetRequest){
        if (friendGetRequest.getEmail()==null)
        {
            throw new RuntimeException("email parameter is null, pls assign correct value !");
        }


        List<String> emailList= friendManagementService.get(friendGetRequest.getEmail());

        return new ResponseEntity<>(new CommonResponse(emailList,emailList==null?0:emailList.size()), HttpStatus.OK);
    }

    @PostMapping(value = "/friend/getcommon")
    public ResponseEntity<CommonResponse> getCommon(@RequestBody FriendRequest friendRequest){
        if (friendRequest.getFriends()==null)
        {
            throw new RuntimeException("friend parameter is null, pls assign correct value !");
        }
        if (!(friendRequest.getFriends() instanceof ArrayList) ||(friendRequest.getFriends().size()!=2))
        {
            throw new RuntimeException("friend content is invalid, pls input 2 email addresses !");
        }


        List<String> emailList= friendManagementService.getCommon(friendRequest);

        return new ResponseEntity<>(new CommonResponse(emailList,emailList==null?0:emailList.size()), HttpStatus.OK);
    }

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
