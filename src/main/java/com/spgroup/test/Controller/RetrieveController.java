package com.spgroup.test.Controller;

import com.spgroup.test.Common.CommonResponse;
import com.spgroup.test.Data.*;
import com.spgroup.test.Service.RetrieveManagementService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import java.util.List;


@RestController
@Log4j
public class RetrieveController {

    @Autowired
    RetrieveManagementService retrieveManagementService;

    @PostMapping(value = "/retrieve")
    public ResponseEntity<CommonResponse> retrieve(@RequestBody RetrieveRequest retrieveRequest){
        if (retrieveRequest.getSender()==null)
        {
            throw new RuntimeException("parameter missing, pls input sender value !");
        }

        List<String> emails=  retrieveManagementService.retrieve(retrieveRequest);

        return new ResponseEntity<>(new CommonResponse(emails), HttpStatus.OK);
    }

}
