package com.spgroup.test.Service.Impl;

import com.spgroup.test.BlockRepository;
import com.spgroup.test.Data.BlockPo;
import com.spgroup.test.Data.FriendPo;
import com.spgroup.test.Data.FriendRequest;
import com.spgroup.test.Data.SubscriptionRequest;
import com.spgroup.test.FriendRepository;
import com.spgroup.test.Service.FriendManagementService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@Log4j
public class FriendManagementServiceImpl implements FriendManagementService {

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    BlockRepository blockRepository;

    public int checkFriendExistence(String source, String target)
    {
        try
        {
            FriendPo friendPo= new FriendPo();
            friendPo.setSource(source);

            Example<FriendPo> targetInfoExample = Example.of(friendPo);
            FriendPo friendPo1 = friendRepository.findOne(targetInfoExample).orElse(null);
            if (friendPo1!=null) {
                List<String> targetFriends= friendPo1.getTargets().get("emails"); //get target friend list
                for (String targetFriend :targetFriends)
                {
                    if (targetFriend.equals(target)) //if request friend is already in the target friend list
                    {
                        return 0;
                    }
                }
                return 1; //source existed, but target not in the target list
            }
            return 2; //source not existed
        }catch (Exception e)
        {
            log.error("checkFriendExistence error: ",e);
            throw new RuntimeException("checkFriendExistence error: "+e.toString());
        }

    }

    public boolean createSourceAndAssignTarget(String source, String target)
    {
        try
        {
            FriendPo friendPo= new FriendPo();
            friendPo.setSource(source);
            friendPo.setLastModifiedTimestamp(Instant.now().getEpochSecond());

            List<String> current= new ArrayList<>();

            current.add(target);
            Map<String, List<String>> targets = new HashMap<>();
            targets.put("emails",current);
            friendPo.setTargets(targets);

            friendRepository.save(friendPo);
            return true;

        }catch (Exception e)
        {
            log.error("createSourceAndAssignTarget error: ",e);
            throw new RuntimeException("createSourceAndAssignTarget error: "+e.toString());
        }

    }

    public boolean checkSourceExistence(String source)
    {
        try
        {
            FriendPo friendPo= new FriendPo();
            friendPo.setSource(source);

            Example<FriendPo> targetInfoExample = Example.of(friendPo);
            return friendRepository.exists(targetInfoExample);

        }catch (Exception e)
        {
            log.error("checkSourceExistence error: ",e);
            throw new RuntimeException("checkSourceExistence error: "+e.toString());
        }

    }

    public boolean checkBlockSourceExistence(String source)
    {
        try
        {
            BlockPo blockPo= new BlockPo();
            blockPo.setBlocked(source);

            Example<BlockPo> targetInfoExample = Example.of(blockPo);
            return blockRepository.exists(targetInfoExample);

        }catch (Exception e)
        {
            log.error("checkBlockSourceExistence error: ",e);
            throw new RuntimeException("checkBlockSourceExistence error: "+e.toString());
        }

    }

    public boolean isBlockedRelationship(String email1, String email2)
    {
        try
        {
            if (checkBlockSourceExistence(email1) ||checkBlockSourceExistence(email2)) {

                //check whether email1 blocked by email2
                BlockPo blockPo = new BlockPo();
                blockPo.setBlocked(email1);
                Example<BlockPo> targetInfoExample = Example.of(blockPo);
                BlockPo blockPo1 = blockRepository.findOne(targetInfoExample).orElse(null);
                if (blockPo1!=null) {
                    List<String> blockList= blockPo1.getBlockedBy().get("emails");
                    for (String block :blockList)
                    {
                        if (block.equals(email2))
                        {
                            return true;
                        }
                    }
                }

                //check whether email2 blocked by email1
                BlockPo blockPo2 = new BlockPo();
                blockPo.setBlocked(email2);
                Example<BlockPo> targetInfoExample1 = Example.of(blockPo2);
                BlockPo blockPo3 = blockRepository.findOne(targetInfoExample).orElse(null);
                if (blockPo3!=null) {
                    List<String> blockList= blockPo3.getBlockedBy().get("emails");
                    for (String block :blockList)
                    {
                        if (block.equals(email1))
                        {
                            return true;
                        }
                    }
                }
                return false;
            }
            else // if none of them exist, not blocked
            {
                return false;
            }

        }catch (Exception e)
        {
            log.error("isBlockedRelationship error: ",e);
            throw new RuntimeException("isBlockedRelationship error: "+e.toString());
        }

    }

    public boolean addTargetToSource(String source, String target)
    {
        try
        {
            FriendPo friendPo= new FriendPo();
            friendPo.setSource(source);

            Example<FriendPo> target1 = Example.of(friendPo);

            Optional<FriendPo> target2 = friendRepository.findOne(target1);

            if (target2.isPresent()) { //update record
                FriendPo friendPo1=target2.get();

                friendPo1.setLastModifiedTimestamp(Instant.now().getEpochSecond());
                List<String> current= friendPo1.getTargets().get("emails");
                if (!current.contains(target))
                    current.add(target);
                Map<String, List<String>> targets = new HashMap<>();
                targets.put("emails",current);
                friendPo1.setTargets(targets);
                friendRepository.save(friendPo1);
            }
            else { //cant find the target list
                log.error("addTargetToSource error: cannot find the friend in the source list, source friend = "+ source);
                throw new RuntimeException("addTargetToSource error: cannot find the friend in the source list, source friend = "+ source);
            }
            return true;

        }catch (Exception e)
        {
            log.error("addTargetToSource error: ",e);
            throw new RuntimeException("addTargetToSource error: "+e.toString());
        }

    }

    @Override
    public void create(FriendRequest friendRequest) {

        try
        {
            //////////////0. check whether 1st and 2nd been blocked or not

            if (isBlockedRelationship(friendRequest.getFriends().get(0),friendRequest.getFriends().get(1)))
            {
                log.error("Create friend error: friends are been blocked!, friends= "+friendRequest.getFriends().get(0)+", "+friendRequest.getFriends().get(1));
                throw new RuntimeException("Create friend error: friends are been blocked!, friends= "+friendRequest.getFriends().get(0)+", "+friendRequest.getFriends().get(1));
            }


            //////////////1. check whether 2nd in the  target list of 1st friend
            int rtn= checkFriendExistence(friendRequest.getFriends().get(0),friendRequest.getFriends().get(1) );

            //////////////1.1 if 2nd in the target list of 1st
            if (rtn==0) {
                rtn= checkFriendExistence(friendRequest.getFriends().get(0),friendRequest.getFriends().get(1) ); //check whether 1st in the  target list of 2nd friend
                if (rtn==0) //if 1st in the target list of 2nd. return. do nothing
                    return;
                else  //1st not in the  target list of 2nd friend
                {
                    //check second friend existence
                    if (checkSourceExistence(friendRequest.getFriends().get(1) )  ) //2nd friend exist as a source
                    {
                        addTargetToSource(friendRequest.getFriends().get(1), friendRequest.getFriends().get(0));
                    }
                    else  //2nd not exist as source, create 2nd and put 1st as 2nd's target
                    {
                        createSourceAndAssignTarget(friendRequest.getFriends().get(1), friendRequest.getFriends().get(0));
                    }
                }
            }
            ////////////////1.2 2nd not in the target list of 1st, add 2nd to target list of 1st
            if (rtn==1)
            {
                addTargetToSource(friendRequest.getFriends().get(0), friendRequest.getFriends().get(1));

                //check 2nd as aa source existence
                if (checkSourceExistence(friendRequest.getFriends().get(1) )  ) //2nd exist as source
                {
                    addTargetToSource(friendRequest.getFriends().get(1), friendRequest.getFriends().get(0)); //add 1st to the target list of 2nd
                }
                else  // //2nd not exist as source
                {
                    createSourceAndAssignTarget(friendRequest.getFriends().get(1), friendRequest.getFriends().get(0));
                }

            }
            /////////////////1.3 1st not exist as source
            if (rtn==2)
            {
                createSourceAndAssignTarget(friendRequest.getFriends().get(0), friendRequest.getFriends().get(1));

                //check 2nd as aa source existence
                if (checkSourceExistence(friendRequest.getFriends().get(1) )  ) //2nd exist as source
                {
                    addTargetToSource(friendRequest.getFriends().get(1), friendRequest.getFriends().get(0)); //add 1st to the target list of 2nd
                }
                else  // //2nd not exist as source
                {
                    createSourceAndAssignTarget(friendRequest.getFriends().get(1), friendRequest.getFriends().get(0));
                }
            }

        }catch (Exception e)
        {
            log.error("Create friend error: ",e);
            throw new RuntimeException("Create friend error: "+e.toString());
        }

    }

    @Override
    public List<String> get(String source) {
        try
        {
            FriendPo friendPo= new FriendPo();
            friendPo.setSource(source);

            Example<FriendPo> target1 = Example.of(friendPo);

            FriendPo friendPo1 = friendRepository.findOne(target1).orElse(null);
            if (friendPo1!=null)
            {
                List<String> emailList= friendPo1.getTargets().get("emails");
                return emailList;
            }
            return null;


        }catch (Exception e)
        {
            log.error("get friend error: ",e);
            throw new RuntimeException("get friend error: "+e.toString());
        }
    }

    @Override
    public List<String> getCommon(FriendRequest friendRequest) {
        try
        {
            List<String> emailList1= get(friendRequest.getFriends().get(0));
            List<String> emailList2= get(friendRequest.getFriends().get(1));
            if ((emailList1!=null)&&(emailList2!=null))
            {
                emailList1.retainAll(emailList2);
                return emailList1;

            }
            return null;

        }catch (Exception e)
        {
            log.error("getCommon friend error: ",e);
            throw new RuntimeException("getCommon friend error: "+e.toString());
        }
    }


}
