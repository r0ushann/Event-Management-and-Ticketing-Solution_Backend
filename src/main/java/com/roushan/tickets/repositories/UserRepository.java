package com.roushan.tickets.repositories;


import java.util.UUID;

import com.roushan.tickets.domains.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}