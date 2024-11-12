package org.gu.taskmanager.repository;

import org.gu.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 根據使用者名稱尋找使用者
    User findByUsername(String username);

    // 檢查使用者名稱是否存在
    boolean existsByUsername(String username);

    // 檢查信箱是否存在
    boolean existsByEmail(String email);

}

