package com.nitr.hellonitr.serviceImpl;

import com.nitr.hellonitr.dtos.UsersDTO;
import com.nitr.hellonitr.entity.Users;
import com.nitr.hellonitr.enums.DepartmentEnum;
import com.nitr.hellonitr.enums.DesignationEnum;
import com.nitr.hellonitr.repository.UsersRepository;
import com.nitr.hellonitr.service.UsersService;
import com.nitr.hellonitr.utility.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public Page<UsersDTO> getAllUsers(Pageable pageable) {
        return usersRepository.findAll(pageable).map(user -> entityMapper.toDto(user, UsersDTO.class));
    }

    @Override
    public UsersDTO createUser(UsersDTO userDto) {
        Users user = entityMapper.toDto(userDto, Users.class);
        user = usersRepository.save(user);
        return entityMapper.toDto(user, UsersDTO.class);
    }

    @Override
    public UsersDTO updateUser(String id, UsersDTO userDto) {
        Users user = usersRepository.findById(UUID.fromString(id)).orElse(null);
        if (user != null) {
            entityMapper.toDto(user, UsersDTO.class);
            user = usersRepository.save(user);
        }
        return entityMapper.toDto(user, UsersDTO.class);
    }

    @Override
    public void deleteUser(String id) {
        usersRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public List<UsersDTO> searchByName(String name) {
        return usersRepository.findByName(name).stream()
                .map(user -> entityMapper.toDto(user, UsersDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<UsersDTO> searchByDepartment(String department) {
        DepartmentEnum departmentEnum = DepartmentEnum.valueOf(department);
        return usersRepository.findByDepartment(departmentEnum).stream()
                .map(user -> entityMapper.toDto(user, UsersDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<UsersDTO> searchByDesignation(String designation) {
        DesignationEnum designationEnum = DesignationEnum.valueOf(designation);
        return usersRepository.findByDesignation(designationEnum).stream()
                .map(user -> entityMapper.toDto(user, UsersDTO.class)).collect(Collectors.toList());
    }

}
