package com.codeb.mis.controller;

import com.codeb.mis.dto.ChainRequest;
import com.codeb.mis.service.ChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chains")
@CrossOrigin(origins = "*")
public class ChainController {

    @Autowired
    private ChainService chainService;

    // GET /api/chains — get all active chains
    @GetMapping
    public ResponseEntity<?> getAllChains() {
        return chainService.getAllChains();
    }

    // GET /api/chains/filter?groupId=1 — filter by group
    @GetMapping("/filter")
    public ResponseEntity<?> getChainsByGroup(@RequestParam Integer groupId) {
        return chainService.getChainsByGroup(groupId);
    }

    // POST /api/chains — add new chain
    @PostMapping
    public ResponseEntity<?> addChain(@RequestBody ChainRequest request) {
        return chainService.addChain(request);
    }

    // PUT /api/chains/{id} — update chain
    @PutMapping("/{id}")
    public ResponseEntity<?> updateChain(@PathVariable Integer id,
                                          @RequestBody ChainRequest request) {
        return chainService.updateChain(id, request);
    }

    // DELETE /api/chains/{id} — soft delete chain
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChain(@PathVariable Integer id) {
        return chainService.deleteChain(id);
    }
}
