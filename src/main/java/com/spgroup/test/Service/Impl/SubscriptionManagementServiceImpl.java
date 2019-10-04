package com.spgroup.test.Service.Impl;

import com.spgroup.test.Data.BlockRequest;
import com.spgroup.test.Data.FriendPo;
import com.spgroup.test.Data.SubscriptionPo;
import com.spgroup.test.Data.SubscriptionRequest;
import com.spgroup.test.Service.SubscriptionManagementService;
import com.spgroup.test.SubscriptionRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@Log4j
public class SubscriptionManagementServiceImpl implements SubscriptionManagementService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Override
    public void subscription(SubscriptionRequest subscriptionRequest) {
        try
        {
            SubscriptionPo subscriptionPo= new SubscriptionPo();
            subscriptionPo.setTarget(subscriptionRequest.getTarget());

            Example<SubscriptionPo> target1 = Example.of(subscriptionPo);

            Optional<SubscriptionPo> subscriptionPo1 = subscriptionRepository.findOne(target1);
            if (subscriptionPo1.isPresent())
            {
                SubscriptionPo SubscriptionPo2=subscriptionPo1.get();
                SubscriptionPo2.setLastModifiedTimestamp(Instant.now().getEpochSecond());
                List<String> emailList= SubscriptionPo2.getRequestor().get("emails");
                if (!emailList.contains(subscriptionRequest.getRequestor()))
                {
                    emailList.add(subscriptionRequest.getRequestor());
                    Map<String, List<String>> emails  = new HashMap<>();
                    emails.put("emails",emailList);
                    SubscriptionPo2.setRequestor(emails);
                    subscriptionRepository.save(SubscriptionPo2);
                }
            }
            else //target not exist
            {
                SubscriptionPo subscriptionPo3= new SubscriptionPo();
                subscriptionPo3.setLastModifiedTimestamp(Instant.now().getEpochSecond());
                subscriptionPo3.setTarget(subscriptionRequest.getTarget());
                List<String> emailList= new ArrayList<>();
                emailList.add(subscriptionRequest.getRequestor());
                Map<String, List<String>> emails  = new HashMap<>();
                emails.put("emails",emailList);
                subscriptionPo3.setRequestor(emails);
                subscriptionRepository.save(subscriptionPo3);

            }

        }catch (Exception e)
        {
            log.error("subscription error: ",e);
            throw new RuntimeException("subscription error: "+e.toString());
        }
    }


}
