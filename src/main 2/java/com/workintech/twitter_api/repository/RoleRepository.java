package com.workintech.twitter_api.repository;

import com.workintech.twitter_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.authority = :authrity")
    Role getByAuthority(@Param("authority") String authority);
}
