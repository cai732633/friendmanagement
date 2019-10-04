package com.spgroup.test.Service;


import com.spgroup.test.Data.SubscriptionRequest;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionManagementService {
    void subscription(SubscriptionRequest subscriptionRequest);
}
