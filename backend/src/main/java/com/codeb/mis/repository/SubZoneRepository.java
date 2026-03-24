package com.codeb.mis.repository;

import com.codeb.mis.model.SubZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubZoneRepository extends JpaRepository<SubZone, Integer> {
    List<SubZone> findByIsActiveTrue();
    List<SubZone> findByIsActiveTrueAndBrand_BrandId(Integer brandId);
    List<SubZone> findByIsActiveTrueAndBrand_Chain_ChainId(Integer chainId);
    List<SubZone> findByIsActiveTrueAndBrand_Chain_Group_GroupId(Integer groupId);
    boolean existsByBrand_BrandIdAndIsActiveTrue(Integer brandId);
}
