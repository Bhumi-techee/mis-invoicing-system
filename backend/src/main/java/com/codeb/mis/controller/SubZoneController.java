package com.codeb.mis.controller;

import com.codeb.mis.dto.SubZoneRequest;
import com.codeb.mis.service.SubZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zones")
@CrossOrigin(origins = "*")
public class SubZoneController {

    @Autowired
    private SubZoneService subZoneService;

    // GET /api/zones
    @GetMapping
    public ResponseEntity<?> getAllZones() {
        return subZoneService.getAllZones();
    }

    // GET /api/zones/filter/brand?brandId=1
    @GetMapping("/filter/brand")
    public ResponseEntity<?> getZonesByBrand(@RequestParam Integer brandId) {
        return subZoneService.getZonesByBrand(brandId);
    }

    // GET /api/zones/filter/chain?chainId=1
    @GetMapping("/filter/chain")
    public ResponseEntity<?> getZonesByChain(@RequestParam Integer chainId) {
        return subZoneService.getZonesByChain(chainId);
    }

    // GET /api/zones/filter/group?groupId=1
    @GetMapping("/filter/group")
    public ResponseEntity<?> getZonesByGroup(@RequestParam Integer groupId) {
        return subZoneService.getZonesByGroup(groupId);
    }

    // POST /api/zones
    @PostMapping
    public ResponseEntity<?> addZone(@RequestBody SubZoneRequest request) {
        return subZoneService.addZone(request);
    }

    // PUT /api/zones/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateZone(@PathVariable Integer id,
                                         @RequestBody SubZoneRequest request) {
        return subZoneService.updateZone(id, request);
    }

    // DELETE /api/zones/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteZone(@PathVariable Integer id) {
        return subZoneService.deleteZone(id);
    }
}
