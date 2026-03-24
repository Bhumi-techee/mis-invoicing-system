package com.codeb.mis.controller;

import com.codeb.mis.dto.GroupRequest;
import com.codeb.mis.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public ResponseEntity<?> getAllGroups() {
        return groupService.getAllGroups();
    }

    @PostMapping
    public ResponseEntity<?> addGroup(@RequestBody GroupRequest request) {
        return groupService.addGroup(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable Integer id,
                                          @RequestBody GroupRequest request) {
        return groupService.updateGroup(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Integer id) {
        return groupService.deleteGroup(id);
    }
}
