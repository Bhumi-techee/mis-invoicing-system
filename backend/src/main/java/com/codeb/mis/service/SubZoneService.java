package com.codeb.mis.service;

import com.codeb.mis.dto.SubZoneRequest;
import com.codeb.mis.model.Brand;
import com.codeb.mis.model.SubZone;
import com.codeb.mis.repository.BrandRepository;
import com.codeb.mis.repository.SubZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SubZoneService {

    @Autowired
    private SubZoneRepository subZoneRepository;

    @Autowired
    private BrandRepository brandRepository;

    public ResponseEntity<?> getAllZones() {
        return ResponseEntity.ok(subZoneRepository.findByIsActiveTrue());
    }

    public ResponseEntity<?> getZonesByBrand(Integer brandId) {
        return ResponseEntity.ok(subZoneRepository.findByIsActiveTrueAndBrand_BrandId(brandId));
    }

    public ResponseEntity<?> getZonesByChain(Integer chainId) {
        return ResponseEntity.ok(subZoneRepository.findByIsActiveTrueAndBrand_Chain_ChainId(chainId));
    }

    public ResponseEntity<?> getZonesByGroup(Integer groupId) {
        return ResponseEntity.ok(subZoneRepository.findByIsActiveTrueAndBrand_Chain_Group_GroupId(groupId));
    }

    public ResponseEntity<?> addZone(SubZoneRequest request) {
        if (request.getZoneName() == null || request.getZoneName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Zone name cannot be empty.");
        }
        if (request.getBrandId() == null) {
            return ResponseEntity.badRequest().body("Please select a brand.");
        }
        Optional<Brand> brandOpt = brandRepository.findById(request.getBrandId());
        if (brandOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Selected brand not found.");
        }
        SubZone zone = new SubZone();
        zone.setZoneName(request.getZoneName().trim());
        zone.setBrand(brandOpt.get());
        zone.setIsActive(true);
        subZoneRepository.save(zone);
        return ResponseEntity.ok("Zone added successfully.");
    }

    public ResponseEntity<?> updateZone(Integer id, SubZoneRequest request) {
        Optional<SubZone> existing = subZoneRepository.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        if (request.getZoneName() == null || request.getZoneName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Zone name cannot be empty.");
        }
        if (request.getBrandId() == null) {
            return ResponseEntity.badRequest().body("Please select a brand.");
        }
        Optional<Brand> brandOpt = brandRepository.findById(request.getBrandId());
        if (brandOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Selected brand not found.");
        }
        SubZone zone = existing.get();
        zone.setZoneName(request.getZoneName().trim());
        zone.setBrand(brandOpt.get());
        subZoneRepository.save(zone);
        return ResponseEntity.ok("Zone updated successfully.");
    }

    public ResponseEntity<?> deleteZone(Integer id) {
        Optional<SubZone> existing = subZoneRepository.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        SubZone zone = existing.get();
        zone.setIsActive(false);
        subZoneRepository.save(zone);
        return ResponseEntity.ok("Zone deleted successfully.");
    }
}
