package com.work.taskmanager.repository;

import com.work.taskmanager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(nativeQuery = true, value = """
            SELECT p.* FROM project AS p, user AS u , project_user AS pu
            WHERE p.project_id = pu.project_id AND u.user_id = pu.user_id
            AND u.user_id = %:userId%""")
    List<Project> findByUserId(Long userId);

}
