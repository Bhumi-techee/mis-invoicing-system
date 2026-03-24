package com.codeb.mis.service;

import com.codeb.mis.dto.BrandRequest;
import com.codeb.mis.model.Brand;
import com.codeb.mis.model.Chain;
import com.codeb.mis.repository.BrandRepository;
import com.codeb.mis.repository.ChainRepository;
import com.codeb.mis.repository.SubZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired private BrandRepository brandRepository;
    @Autowired private ChainRepository chainRepository;
    @Autowired private SubZoneRepository subZoneRepository;

    public ResponseEntity<?> getAllBrands() {
        return ResponseEntity.ok(brandRepository.findByIsActiveTrue());
    }

    public ResponseEntity<?> getBrandsByChain(Integer chainId) {
        return ResponseEntity.ok(brandRepository.findByIsActiveTrueAndChain_ChainId(chainId));
    }

    public ResponseEntity<?> getBrandsByGroup(Integer groupId) {
        return ResponseEntity.ok(brandRepository.findByIsActiveTrueAndChain_Group_GroupId(groupId));
    }

    public ResponseEntity<?> addBrand(BrandRequest request) {
        if (request.getBrandName() == null || request.getBrandName().trim().isEmpty())
            return ResponseEntity.badRequest().body("Brand name cannot be empty.");
        if (request.getChainId() == null)
            return ResponseEntity.badRequest().body("Please select a company.");
        Optional<Chain> chainOpt = chainRepository.findById(request.getChainId());
        if (chainOpt.isEmpty())
            return ResponseEntity.badRequest().body("Selected company not found.");
        Brand brand = new Brand();
        brand.setBrandName(request.getBrandName().trim());
        brand.setChain(chainOpt.get());
        brand.setIsActive(true);
        brandRepository.save(brand);
        return ResponseEntity.ok("Brand added successfully.");
    }

    public ResponseEntity<?> updateBrand(Integer id, BrandRequest request) {
        Optional<Brand> existing = brandRepository.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        if (request.getBrandName() == null || request.getBrandName().trim().isEmpty())
            return ResponseEntity.badRequest().body("Brand name cannot be empty.");
        if (request.getChainId() == null)
            return ResponseEntity.badRequest().body("Please select a company.");
        Optional<Chain> chainOpt = chainRepository.findById(request.getChainId());
        if (chainOpt.isEmpty())
            return ResponseEntity.badRequest().body("Selected company not found.");
        Brand brand = existing.get();
        brand.setBrandName(request.getBrandName().trim());
        brand.setChain(chainOpt.get());
        brandRepository.save(brand);
        return ResponseEntity.ok("Brand updated successfully.");
    }

    public ResponseEntity<?> deleteBrand(Integer id) {
        Optional<Brand> existing = brandRepository.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        // Check if brand is linked to any active zone
        if (subZoneRepository.existsByBrand_BrandIdAndIsActiveTrue(id)) {
            return ResponseEntity.badRequest().body("Cannot delete brand linked to a Zone.");
        }
        Brand brand = existing.get();
        brand.setIsActive(false);
        brandRepository.save(brand);
        return ResponseEntity.ok("Brand deleted successfully.");
    }
}
