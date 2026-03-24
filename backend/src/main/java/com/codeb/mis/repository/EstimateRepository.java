package com.codeb.mis.repository;

import com.codeb.mis.model.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Integer> {
    List<Estimate> findAll();
    List<Estimate> findByBrandName(String brandName);
    List<Estimate> findByGroupName(String groupName);
}
