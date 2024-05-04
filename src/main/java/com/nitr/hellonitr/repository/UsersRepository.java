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

    //Case-insensitive searches for name, department, and designation
    @Query("SELECT u FROM Users u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Users> findByName(String name, Pageable pageable);

    @Query("SELECT u FROM Users u WHERE LOWER(u.department) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Users> findByDepartment(String department, Pageable pageable);

    @Query("SELECT u FROM Users u WHERE LOWER(u.designation) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Users> findByDesignation(String designation, Pageable pageable);


}
