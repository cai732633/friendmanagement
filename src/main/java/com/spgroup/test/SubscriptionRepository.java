package com.spgroup.test;


import com.spgroup.test.Data.SubscriptionPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionPo, Integer> {


}
