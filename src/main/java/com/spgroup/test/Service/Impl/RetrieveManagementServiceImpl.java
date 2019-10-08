package com.spgroup.test.Service.Impl;

import com.spgroup.test.Data.RetrieveRequest;
import com.spgroup.test.Service.BlockManagementService;
import com.spgroup.test.Service.FriendManagementService;
import com.spgroup.test.Service.RetrieveManagementService;
import com.spgroup.test.Service.SubscriptionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class RetrieveManagementServiceImpl implements RetrieveManagementService {

    @Autowired
    BlockManagementService blockManagementService;

    @Autowired
    FriendManagementService friendManagementService;


    @Autowired
    SubscriptionManagementService subscriptionManagementService;


    @Override
    public List<String> retrieve(RetrieveRequest retrieveRequest) {

        List<String> emails= new ArrayList<>();

        //check text @email.
        if (retrieveRequest.getText()!=null)
        {
            Matcher m= Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(retrieveRequest.getText());
            while (m.find()) {
                emails.add(m.group());
            }
        }

        //check friend emails
        List<String> friendEmails= friendManagementService.get(retrieveRequest.getSender());
        if (friendEmails!=null)
            emails.addAll(friendEmails);



        //check subscription emails
        List<String> subscriptionEmails= subscriptionManagementService.get(retrieveRequest.getSender());
        if (subscriptionEmails!=null)
            emails.addAll(subscriptionEmails);


        //filter out the blocked emails
        emails.removeIf(b -> blockManagementService.isBlocked(retrieveRequest.getSender(),b) );

        return emails;
    }
}
