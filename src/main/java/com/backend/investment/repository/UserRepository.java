package com.backend.investment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.investment.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
       Optional<User> findByPhone(String phone);

}
