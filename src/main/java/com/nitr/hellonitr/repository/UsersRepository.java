package com.nitr.hellonitr.repository;

import com.nitr.hellonitr.entity.Users;
import com.nitr.hellonitr.enums.DepartmentEnum;
import com.nitr.hellonitr.enums.DesignationEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface UsersRepository extends JpaRepository<Users, UUID> {

    List<Users> findByName(String name);

    List<Users> findByDepartment(DepartmentEnum department);

    List<Users> findByDesignation(DesignationEnum designation);


}
