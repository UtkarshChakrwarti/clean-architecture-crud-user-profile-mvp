package com.nitr.hellonitr.repository;

import com.nitr.hellonitr.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    //Case-insensitive searches for name, email, department, phone number or any other field write query for it nested in the repository
    @Query("SELECT u FROM Users u WHERE lower(u.name) LIKE lower(concat('%', ?1, '%')) OR lower(u.email) LIKE lower(concat('%', ?1, '%')) OR lower(u.department) LIKE lower(concat('%', ?1, '%')) OR lower(u.mobile) LIKE lower(concat('%', ?1, '%'))")
    Page<Users> findByName(String name, Pageable pageable);

}
