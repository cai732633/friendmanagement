package com.spgroup.test.Service;


import com.spgroup.test.Data.SubscriptionRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionManagementService {
    void subscription(SubscriptionRequest subscriptionRequest);

    List<String> get(String sender);
}
