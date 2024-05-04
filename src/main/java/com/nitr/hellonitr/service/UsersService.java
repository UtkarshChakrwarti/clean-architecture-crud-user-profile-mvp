package com.nitr.hellonitr.service;

import com.nitr.hellonitr.dtos.UsersDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UsersService {

    UsersDTO getUserById(String id);

    Page<UsersDTO> getAllUsers(int page, int size);

    UsersDTO createUser(UsersDTO userDto);

    UsersDTO updateUser(String id, UsersDTO userDto);

    void deleteUser(String id);

    Page<UsersDTO> searchByName(String name, int page, int size);

    Page<UsersDTO> searchByDepartment(String department, int page, int size);

    Page<UsersDTO> searchByDesignation(String designation, int page, int size);

}
