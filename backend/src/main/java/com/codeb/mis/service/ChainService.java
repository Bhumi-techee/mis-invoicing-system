package com.codeb.mis.service;

import com.codeb.mis.dto.ChainRequest;
import com.codeb.mis.model.Chain;
import com.codeb.mis.model.Group;
import com.codeb.mis.repository.BrandRepository;
import com.codeb.mis.repository.ChainRepository;
import com.codeb.mis.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ChainService {

    @Autowired private ChainRepository chainRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private BrandRepository brandRepository;

    public ResponseEntity<?> getAllChains() {
        return ResponseEntity.ok(chainRepository.findByIsActiveTrue());
    }

    public ResponseEntity<?> getChainsByGroup(Integer groupId) {
        return ResponseEntity.ok(chainRepository.findByIsActiveTrueAndGroup_GroupId(groupId));
    }

    public ResponseEntity<?> addChain(ChainRequest request) {
        if (request.getCompanyName() == null || request.getCompanyName().trim().isEmpty())
            return ResponseEntity.badRequest().body("Company name cannot be empty.");
        if (request.getGstnNo() == null || request.getGstnNo().trim().isEmpty())
            return ResponseEntity.badRequest().body("GSTN number cannot be empty.");
        if (request.getGroupId() == null)
            return ResponseEntity.badRequest().body("Please select a group.");
        if (chainRepository.existsByGstnNo(request.getGstnNo().trim()))
            return ResponseEntity.badRequest().body("GSTN number already exists.");
        Optional<Group> groupOpt = groupRepository.findById(request.getGroupId());
        if (groupOpt.isEmpty())
            return ResponseEntity.badRequest().body("Selected group not found.");
        Chain chain = new Chain();
        chain.setCompanyName(request.getCompanyName().trim());
        chain.setGstnNo(request.getGstnNo().trim());
        chain.setGroup(groupOpt.get());
        chain.setIsActive(true);
        chainRepository.save(chain);
        return ResponseEntity.ok("Chain added successfully.");
    }

    public ResponseEntity<?> updateChain(Integer id, ChainRequest request) {
        Optional<Chain> existing = chainRepository.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        if (request.getCompanyName() == null || request.getCompanyName().trim().isEmpty())
            return ResponseEntity.badRequest().body("Company name cannot be empty.");
        if (request.getGstnNo() == null || request.getGstnNo().trim().isEmpty())
            return ResponseEntity.badRequest().body("GSTN number cannot be empty.");
        if (request.getGroupId() == null)
            return ResponseEntity.badRequest().body("Please select a group.");
        if (chainRepository.existsByGstnNoAndChainIdNot(request.getGstnNo().trim(), id))
            return ResponseEntity.badRequest().body("GSTN number already exists.");
        Optional<Group> groupOpt = groupRepository.findById(request.getGroupId());
        if (groupOpt.isEmpty())
            return ResponseEntity.badRequest().body("Selected group not found.");
        Chain chain = existing.get();
        chain.setCompanyName(request.getCompanyName().trim());
        chain.setGstnNo(request.getGstnNo().trim());
        chain.setGroup(groupOpt.get());
        chainRepository.save(chain);
        return ResponseEntity.ok("Chain updated successfully.");
    }

    public ResponseEntity<?> deleteChain(Integer id) {
        Optional<Chain> existing = chainRepository.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        // Check if chain is linked to any active brand
        if (brandRepository.existsByChain_ChainIdAndIsActiveTrue(id)) {
            return ResponseEntity.badRequest().body("Cannot delete company linked to a Brand.");
        }
        Chain chain = existing.get();
        chain.setIsActive(false);
        chainRepository.save(chain);
        return ResponseEntity.ok("Chain deleted successfully.");
    }
}
