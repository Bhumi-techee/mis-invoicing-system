package com.codeb.mis.repository;

import com.codeb.mis.model.Chain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChainRepository extends JpaRepository<Chain, Integer> {
    List<Chain> findByIsActiveTrue();
    List<Chain> findByIsActiveTrueAndGroup_GroupId(Integer groupId);
    boolean existsByGstnNo(String gstnNo);
    boolean existsByGstnNoAndChainIdNot(String gstnNo, Integer chainId);
    boolean existsByGroup_GroupIdAndIsActiveTrue(Integer groupId);
}
