package com.weridy.service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

/**
 * @author Weridy
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findOneByLogin(String login);
}
