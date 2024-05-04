package com.nitr.hellonitr.serviceImpl;

import com.nitr.hellonitr.dtos.UsersDTO;
import com.nitr.hellonitr.entity.Users;
import com.nitr.hellonitr.enums.DepartmentEnum;
import com.nitr.hellonitr.exception.ResourceNotFoundException;
import com.nitr.hellonitr.repository.UsersRepository;
import com.nitr.hellonitr.service.UsersService;
import com.nitr.hellonitr.utility.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    EntityMapper entityMapper;

    @Override
    public UsersDTO getUserById(String id) {
        Users user = usersRepository.findById(UUID.fromString(id)).orElse(null);
        return entityMapper.toDto(user, UsersDTO.class);
    }

    @Override
    public Page<UsersDTO> getAllUsers(int page, int size) {
        return usersRepository.findAll(PageRequest.of(page, size))
                .map(user -> entityMapper.toDto(user, UsersDTO.class));
    }

    @Override
    public UsersDTO createUser(UsersDTO userDto) {
        Users user = entityMapper.toEntity(userDto, Users.class);
        user = usersRepository.save(user);
        return entityMapper.toDto(user, UsersDTO.class);
    }

    @Override
    public UsersDTO updateUser(String id, UsersDTO userDto) {
        Users user = usersRepository.findById(UUID.fromString(id)).orElse(null);
        if (user != null && user.getId().equals(UUID.fromString(id))) {
            user = entityMapper.toEntity(userDto, Users.class);
            user.setId(UUID.fromString(id));
            user = usersRepository.save(user);
            return entityMapper.toDto(user, UsersDTO.class);
        } else {
            throw new ResourceNotFoundException("User not found with id " + id);
        }

    }

    @Override
    public void deleteUser(String id) {
        Users user = usersRepository.findById(UUID.fromString(id)).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        usersRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public Page<UsersDTO> searchByName(String name, int page, int size) {
        return usersRepository.findByName(name, PageRequest.of(page, size))
                .map(user -> entityMapper.toDto(user, UsersDTO.class));
    }

    @Override
    public Page<UsersDTO> searchByDepartment(String department, int page, int size) {
        DepartmentEnum departmentEnum = DepartmentEnum.valueOf(department);
        return usersRepository.findByDepartment(String.valueOf(departmentEnum), PageRequest.of(page, size))
                .map(user -> entityMapper.toDto(user, UsersDTO.class));
    }

    @Override
    public Page<UsersDTO> searchByDesignation(String designation, int page, int size) {
        return usersRepository.findByDesignation(designation, PageRequest.of(page, size))
                .map(user -> entityMapper.toDto(user, UsersDTO.class));
    }

}
