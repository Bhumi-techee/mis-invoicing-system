package com.codeb.mis.controller;

import com.codeb.mis.dto.EstimateRequest;
import com.codeb.mis.service.EstimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estimates")
@CrossOrigin(origins = "*")
public class EstimateController {

    @Autowired
    private EstimateService estimateService;

    // GET /api/estimates
    @GetMapping
    public ResponseEntity<?> getAllEstimates() {
        return estimateService.getAllEstimates();
    }

    // GET /api/estimates/filter/brand?brandName=
    @GetMapping("/filter/brand")
    public ResponseEntity<?> getByBrand(@RequestParam String brandName) {
        return estimateService.getEstimatesByBrand(brandName);
    }

    // GET /api/estimates/filter/group?groupName=
    @GetMapping("/filter/group")
    public ResponseEntity<?> getByGroup(@RequestParam String groupName) {
        return estimateService.getEstimatesByGroup(groupName);
    }

    // POST /api/estimates
    @PostMapping
    public ResponseEntity<?> createEstimate(@RequestBody EstimateRequest request) {
        return estimateService.createEstimate(request);
    }

    // PUT /api/estimates/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEstimate(@PathVariable Integer id,
                                             @RequestBody EstimateRequest request) {
        return estimateService.updateEstimate(id, request);
    }

    // DELETE /api/estimates/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEstimate(@PathVariable Integer id) {
        return estimateService.deleteEstimate(id);
    }
}
