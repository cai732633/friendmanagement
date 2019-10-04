package com.spgroup.test.Service;

import com.spgroup.test.Data.FriendRequest;
import com.spgroup.test.Data.SubscriptionRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FriendManagementService {
    void create(FriendRequest friendRequest);

    List<String> get(String source);

    List<String> getCommon(FriendRequest friendRequest);

}
