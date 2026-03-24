package com.codeb.mis.service;

import com.codeb.mis.dto.EstimateRequest;
import com.codeb.mis.model.Chain;
import com.codeb.mis.model.Estimate;
import com.codeb.mis.repository.ChainRepository;
import com.codeb.mis.repository.EstimateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EstimateService {

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private ChainRepository chainRepository;

    public ResponseEntity<?> getAllEstimates() {
        return ResponseEntity.ok(estimateRepository.findAll());
    }

    public ResponseEntity<?> getEstimatesByBrand(String brandName) {
        return ResponseEntity.ok(estimateRepository.findByBrandName(brandName));
    }

    public ResponseEntity<?> getEstimatesByGroup(String groupName) {
        return ResponseEntity.ok(estimateRepository.findByGroupName(groupName));
    }

    public ResponseEntity<?> createEstimate(EstimateRequest request) {
        if (request.getChainId() == null)
            return ResponseEntity.badRequest().body("Please select a company.");
        if (request.getGroupName() == null || request.getGroupName().trim().isEmpty())
            return ResponseEntity.badRequest().body("Please select a group.");
        if (request.getBrandName() == null || request.getBrandName().trim().isEmpty())
            return ResponseEntity.badRequest().body("Please select a brand.");
        if (request.getZoneName() == null || request.getZoneName().trim().isEmpty())
            return ResponseEntity.badRequest().body("Please select a zone.");
        if (request.getService() == null || request.getService().trim().isEmpty())
            return ResponseEntity.badRequest().body("Service details cannot be empty.");
        if (request.getQty() == null || request.getQty() <= 0)
            return ResponseEntity.badRequest().body("Total quantity must be greater than 0.");
        if (request.getCostPerUnit() == null || request.getCostPerUnit() <= 0)
            return ResponseEntity.badRequest().body("Cost per unit must be greater than 0.");
        if (request.getDeliveryDate() == null || request.getDeliveryDate().isEmpty())
            return ResponseEntity.badRequest().body("Delivery date cannot be empty.");

        Optional<Chain> chainOpt = chainRepository.findById(request.getChainId());
        if (chainOpt.isEmpty())
            return ResponseEntity.badRequest().body("Selected company not found.");

        Estimate estimate = new Estimate();
        estimate.setChain(chainOpt.get());
        estimate.setGroupName(request.getGroupName().trim());
        estimate.setBrandName(request.getBrandName().trim());
        estimate.setZoneName(request.getZoneName().trim());
        estimate.setService(request.getService().trim());
        estimate.setQty(request.getQty());
        estimate.setCostPerUnit(request.getCostPerUnit());
        estimate.setTotalCost(request.getQty() * request.getCostPerUnit());
        estimate.setDeliveryDate(LocalDate.parse(request.getDeliveryDate()));
        estimate.setDeliveryDetails(request.getDeliveryDetails() != null ? request.getDeliveryDetails().trim() : "");
        estimateRepository.save(estimate);
        return ResponseEntity.ok("Estimate created successfully.");
    }

    public ResponseEntity<?> updateEstimate(Integer id, EstimateRequest request) {
        Optional<Estimate> existing = estimateRepository.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();

        if (request.getChainId() == null)
            return ResponseEntity.badRequest().body("Please select a company.");
        if (request.getGroupName() == null || request.getGroupName().trim().isEmpty())
            return ResponseEntity.badRequest().body("Please select a group.");
        if (request.getBrandName() == null || request.getBrandName().trim().isEmpty())
            return ResponseEntity.badRequest().body("Please select a brand.");
        if (request.getZoneName() == null || request.getZoneName().trim().isEmpty())
            return ResponseEntity.badRequest().body("Please select a zone.");
        if (request.getService() == null || request.getService().trim().isEmpty())
            return ResponseEntity.badRequest().body("Service details cannot be empty.");
        if (request.getQty() == null || request.getQty() <= 0)
            return ResponseEntity.badRequest().body("Total quantity must be greater than 0.");
        if (request.getCostPerUnit() == null || request.getCostPerUnit() <= 0)
            return ResponseEntity.badRequest().body("Cost per unit must be greater than 0.");
        if (request.getDeliveryDate() == null || request.getDeliveryDate().isEmpty())
            return ResponseEntity.badRequest().body("Delivery date cannot be empty.");

        Optional<Chain> chainOpt = chainRepository.findById(request.getChainId());
        if (chainOpt.isEmpty())
            return ResponseEntity.badRequest().body("Selected company not found.");

        Estimate estimate = existing.get();
        estimate.setChain(chainOpt.get());
        estimate.setGroupName(request.getGroupName().trim());
        estimate.setBrandName(request.getBrandName().trim());
        estimate.setZoneName(request.getZoneName().trim());
        estimate.setService(request.getService().trim());
        estimate.setQty(request.getQty());
        estimate.setCostPerUnit(request.getCostPerUnit());
        estimate.setTotalCost(request.getQty() * request.getCostPerUnit());
        estimate.setDeliveryDate(LocalDate.parse(request.getDeliveryDate()));
        estimate.setDeliveryDetails(request.getDeliveryDetails() != null ? request.getDeliveryDetails().trim() : "");
        estimateRepository.save(estimate);
        return ResponseEntity.ok("Estimate updated successfully.");
    }

    public ResponseEntity<?> deleteEstimate(Integer id) {
        Optional<Estimate> existing = estimateRepository.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        estimateRepository.deleteById(id);
        return ResponseEntity.ok("Estimate deleted successfully.");
    }
}
