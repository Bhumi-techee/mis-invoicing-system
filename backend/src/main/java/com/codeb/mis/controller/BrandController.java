package com.codeb.mis.controller;

import com.codeb.mis.dto.BrandRequest;
import com.codeb.mis.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brands")
@CrossOrigin(origins = "*")
public class BrandController {

    @Autowired
    private BrandService brandService;

    // GET /api/brands — get all active brands
    @GetMapping
    public ResponseEntity<?> getAllBrands() {
        return brandService.getAllBrands();
    }

    // GET /api/brands/filter/chain?chainId=1 — filter by company
    @GetMapping("/filter/chain")
    public ResponseEntity<?> getBrandsByChain(@RequestParam Integer chainId) {
        return brandService.getBrandsByChain(chainId);
    }

    // GET /api/brands/filter/group?groupId=1 — filter by group
    @GetMapping("/filter/group")
    public ResponseEntity<?> getBrandsByGroup(@RequestParam Integer groupId) {
        return brandService.getBrandsByGroup(groupId);
    }

    // POST /api/brands — add new brand
    @PostMapping
    public ResponseEntity<?> addBrand(@RequestBody BrandRequest request) {
        return brandService.addBrand(request);
    }

    // PUT /api/brands/{id} — update brand
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Integer id,
                                          @RequestBody BrandRequest request) {
        return brandService.updateBrand(id, request);
    }

    // DELETE /api/brands/{id} — soft delete brand
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Integer id) {
        return brandService.deleteBrand(id);
    }
}
