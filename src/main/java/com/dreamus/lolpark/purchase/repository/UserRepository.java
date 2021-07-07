package com.dreamus.lolpark.purchase.repository;

import com.dreamus.lolpark.purchase.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
