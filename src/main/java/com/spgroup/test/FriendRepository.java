package com.spgroup.test;


import com.spgroup.test.Data.FriendPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<FriendPo, Integer> {


}
