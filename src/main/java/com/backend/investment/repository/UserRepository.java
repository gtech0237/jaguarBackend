package com.backend.investment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.investment.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
       Optional<User> findByPhone(String phone);

       Optional<User> findByMyReferralCode(String myReferralCode);
       // UserId
       List<User> findByReferrerId(Long referrerId);

       long countByReferrerId(Long referrerId);

}
