package com.wipro.training3.authserver.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.training3.authserver.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
}
