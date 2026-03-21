package com.codeb.mis.repository;

import com.codeb.mis.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    List<Brand> findByIsActiveTrue();
    List<Brand> findByIsActiveTrueAndChain_ChainId(Integer chainId);
    List<Brand> findByIsActiveTrueAndChain_Group_GroupId(Integer groupId);
    boolean existsByChain_ChainIdAndIsActiveTrue(Integer chainId);
}
