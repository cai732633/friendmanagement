package com.spgroup.test;


import com.spgroup.test.Data.BlockPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<BlockPo, Integer> {


}
