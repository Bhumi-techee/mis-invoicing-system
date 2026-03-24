package com.codeb.mis.service;

import com.codeb.mis.dto.GroupRequest;
import com.codeb.mis.model.Group;
import com.codeb.mis.repository.ChainRepository;
import com.codeb.mis.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ChainRepository chainRepository;

    public ResponseEntity<?> getAllGroups() {
        List<Group> groups = groupRepository.findByIsActiveTrue();
        return ResponseEntity.ok(groups);
    }

    public ResponseEntity<?> addGroup(GroupRequest request) {
        if (request.getGroupName() == null || request.getGroupName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Group name cannot be empty.");
        }
        if (groupRepository.existsByGroupName(request.getGroupName().trim())) {
            return ResponseEntity.badRequest().body("Group Already Exists!!!");
        }
        Group group = new Group();
        group.setGroupName(request.getGroupName().trim());
        group.setIsActive(true);
        groupRepository.save(group);
        return ResponseEntity.ok("Group added successfully.");
    }

    public ResponseEntity<?> updateGroup(Integer id, GroupRequest request) {
        Optional<Group> existing = groupRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (request.getGroupName() == null || request.getGroupName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Group name cannot be empty.");
        }
        if (groupRepository.existsByGroupNameAndGroupIdNot(request.getGroupName().trim(), id)) {
            return ResponseEntity.badRequest().body("Group Already Exists!!!");
        }
        Group group = existing.get();
        group.setGroupName(request.getGroupName().trim());
        groupRepository.save(group);
        return ResponseEntity.ok("Group updated successfully.");
    }

    public ResponseEntity<?> deleteGroup(Integer id) {
        Optional<Group> existing = groupRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Check if group is linked to any active chain
        if (chainRepository.existsByGroup_GroupIdAndIsActiveTrue(id)) {
            return ResponseEntity.badRequest().body("Cannot delete group linked to a Chain.");
        }
        Group group = existing.get();
        group.setIsActive(false);
        groupRepository.save(group);
        return ResponseEntity.ok("Group deleted successfully.");
    }
}
