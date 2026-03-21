package com.codeb.mis.repository;

import com.codeb.mis.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findByIsActiveTrue();
    boolean existsByGroupName(String groupName);
    boolean existsByGroupNameAndGroupIdNot(String groupName, Integer groupId);
}
