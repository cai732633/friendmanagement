package com.spgroup.test.Service;

import com.spgroup.test.Data.BlockRequest;
import com.spgroup.test.Data.RetrieveRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RetrieveManagementService {
    List<String> retrieve(RetrieveRequest retrieveRequest);
}
