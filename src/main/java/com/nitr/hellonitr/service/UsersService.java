package com.nitr.hellonitr.service;

import com.nitr.hellonitr.dtos.UsersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {

    UsersDTO getUserById(String id);

    Page<UsersDTO> getAllUsers(Pageable pageable);

    UsersDTO createUser(UsersDTO userDto);

    UsersDTO updateUser(String id, UsersDTO userDto);

    void deleteUser(String id);

    List<UsersDTO> searchByName(String name);

    List<UsersDTO> searchByDepartment(String department);

    List<UsersDTO> searchByDesignation(String designation);

}
