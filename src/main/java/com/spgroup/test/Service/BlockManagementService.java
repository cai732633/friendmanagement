package com.spgroup.test.Service;

import com.spgroup.test.Data.BlockRequest;
import org.springframework.stereotype.Service;

@Service
public interface BlockManagementService {
    void block(BlockRequest blockRequest);
}
