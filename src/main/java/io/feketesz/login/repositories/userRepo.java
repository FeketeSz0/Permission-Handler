package io.feketesz.login.repositories;

import io.feketesz.login.model.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepo extends JpaRepository<user, Integer> {


    Optional<user> findByusername(String username);


}
