package com.work.taskmanager.repository;

import com.work.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    @Query(nativeQuery = true, value = """
        SELECT u.* FROM user AS u, task AS t, user_task AS ut
        WHERE u.user_id = ut.user_id AND
              t.task_id = ut.task_id AND
              t.task_id = %:taskId%
    """)
    List<User> findByTask(Long taskId);

}
