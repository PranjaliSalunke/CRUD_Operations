package com.example.Project_CRUD.repositories;

import com.example.Project_CRUD.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
    User findByEmail(String email);


   
}
