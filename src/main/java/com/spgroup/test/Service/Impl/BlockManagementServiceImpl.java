package com.spgroup.test.Service.Impl;

import com.spgroup.test.BlockRepository;
import com.spgroup.test.Data.BlockPo;
import com.spgroup.test.Data.BlockRequest;

import com.spgroup.test.Data.FriendPo;
import com.spgroup.test.Service.BlockManagementService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@Log4j
public class BlockManagementServiceImpl implements BlockManagementService {

    @Autowired
    BlockRepository blockRepository;

    @Override
    public void block(BlockRequest blockRequest) {
        try
        {
            BlockPo blockPo = new BlockPo();
            blockPo.setBlocked(blockRequest.getTarget());

            Example<BlockPo> target1 = Example.of(blockPo);

            Optional<BlockPo> target2 = blockRepository.findOne(target1);

            if (target2.isPresent()) { //update record
                BlockPo blockPo1 = target2.get();

                blockPo1.setLastModifiedTimestamp(Instant.now().getEpochSecond());
                List<String> current = blockPo1.getBlockedBy().get("emails");
                if (!current.contains(blockRequest.getRequestor()))
                    current.add(blockRequest.getRequestor());
                Map<String, List<String>> targets = new HashMap<>();
                targets.put("emails", current);
                blockPo1.setBlockedBy(targets);
                blockRepository.save(blockPo1);
            }

            else { //cant find the target list, create new one
                BlockPo blockPo1 = new BlockPo();
                blockPo1.setBlocked(blockRequest.getTarget());
                blockPo1.setLastModifiedTimestamp(Instant.now().getEpochSecond());
                Map<String, List<String>> targets = new HashMap<>();
                List<String> current= new ArrayList<>();
                current.add(blockRequest.getRequestor());
                targets.put("emails",current);
                blockPo1.setBlockedBy(targets);
                blockRepository.save(blockPo1);
            }

        }catch (Exception e)
        {
            log.error("block error: ",e);
            throw new RuntimeException("block error: "+e.toString());
        }
    }

    @Override
    public boolean isBlocked(String blocked, String blockedBy) {
        try
        {
            BlockPo blockPo= new BlockPo();
            blockPo.setBlocked(blocked);

            Example<BlockPo> target1 = Example.of(blockPo);

            BlockPo blockPo1 = blockRepository.findOne(target1).orElse(null);
            if (blockPo1!=null)
            {
                List<String> emailList= blockPo1.getBlockedBy().get("emails");
                if (emailList.contains(blockedBy))
                    return true;
            }
            return false;

        }catch (Exception  e)
        {
            log.error("isBlocked error: ",e);
            throw new RuntimeException("isBlocked error: "+e.toString());
        }
    }
}
