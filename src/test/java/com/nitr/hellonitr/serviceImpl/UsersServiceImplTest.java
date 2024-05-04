package com.nitr.hellonitr.serviceImpl;

import com.nitr.hellonitr.dtos.UsersDTO;
import com.nitr.hellonitr.entity.Users;
import com.nitr.hellonitr.repository.UsersRepository;
import com.nitr.hellonitr.utility.EntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersServiceImplTest {

    @InjectMocks
    private UsersServiceImpl usersService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private EntityMapper entityMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById() {
        String id = UUID.randomUUID().toString();
        Users user = new Users();
        UsersDTO userDTO = new UsersDTO();

        when(usersRepository.findById(UUID.fromString(id))).thenReturn(java.util.Optional.of(user));
        when(entityMapper.toDto(user, UsersDTO.class)).thenReturn(userDTO);

        UsersDTO result = usersService.getUserById(id);

        assertEquals(userDTO, result);
        verify(usersRepository, times(1)).findById(UUID.fromString(id));
        verify(entityMapper, times(1)).toDto(user, UsersDTO.class);
    }

    @Test
    void getAllUsers() {
        Users user = new Users();
        UsersDTO userDTO = new UsersDTO();
        Page<Users> usersPage = new PageImpl<>(Collections.singletonList(user));
        Page<UsersDTO> usersDTOPage = new PageImpl<>(Collections.singletonList(userDTO));

        when(usersRepository.findAll(PageRequest.of(0, 10))).thenReturn(usersPage);
        when(entityMapper.toDto(user, UsersDTO.class)).thenReturn(userDTO);

        Page<UsersDTO> result = usersService.getAllUsers(0, 10);

        assertEquals(usersDTOPage.getContent(), result.getContent());
        verify(usersRepository, times(1)).findAll(PageRequest.of(0, 10));
        verify(entityMapper, times(1)).toDto(user, UsersDTO.class);
    }

    @Test
    void createUser() {
        Users user = new Users();
        UsersDTO userDTO = new UsersDTO();

        when(entityMapper.toEntity(userDTO, Users.class)).thenReturn(user);
        when(usersRepository.save(user)).thenReturn(user);
        when(entityMapper.toDto(user, UsersDTO.class)).thenReturn(userDTO);

        UsersDTO result = usersService.createUser(userDTO);

        assertEquals(userDTO, result);
        verify(entityMapper, times(1)).toEntity(userDTO, Users.class);
        verify(usersRepository, times(1)).save(user);
        verify(entityMapper, times(1)).toDto(user, UsersDTO.class);
    }

    @Test
    void updateUser() {
        String id = UUID.randomUUID().toString();
        Users user = new Users();
        user.setId(UUID.fromString(id)); // Set the id here
        UsersDTO userDTO = new UsersDTO();

        when(usersRepository.findById(UUID.fromString(id))).thenReturn(java.util.Optional.of(user));
        when(entityMapper.toEntity(userDTO, Users.class)).thenReturn(user);
        when(usersRepository.save(user)).thenReturn(user);
        when(entityMapper.toDto(user, UsersDTO.class)).thenReturn(userDTO);

        UsersDTO result = usersService.updateUser(id, userDTO);

        assertEquals(userDTO, result);
        verify(usersRepository, times(1)).findById(UUID.fromString(id));
        verify(entityMapper, times(1)).toEntity(userDTO, Users.class);
        verify(usersRepository, times(1)).save(user);
        verify(entityMapper, times(1)).toDto(user, UsersDTO.class);
    }

    @Test
    void deleteUser() {
        String id = UUID.randomUUID().toString();
        Users user = new Users();

        when(usersRepository.findById(UUID.fromString(id))).thenReturn(java.util.Optional.of(user));

        usersService.deleteUser(id);

        verify(usersRepository, times(1)).findById(UUID.fromString(id));
        verify(usersRepository, times(1)).deleteById(UUID.fromString(id));
    }

    @Test
    void searchByName() {
        String name = "John Doe";
        Users user = new Users();
        UsersDTO userDTO = new UsersDTO();
        Page<Users> usersPage = new PageImpl<>(Collections.singletonList(user));
        Page<UsersDTO> usersDTOPage = new PageImpl<>(Collections.singletonList(userDTO));

        when(usersRepository.findByName(name, PageRequest.of(0, 10))).thenReturn(usersPage);
        when(entityMapper.toDto(user, UsersDTO.class)).thenReturn(userDTO);

        Page<UsersDTO> result = usersService.searchByName(name, 0, 10);

        assertEquals(usersDTOPage.getContent(), result.getContent());
        verify(usersRepository, times(1)).findByName(name, PageRequest.of(0, 10));
        verify(entityMapper, times(1)).toDto(user, UsersDTO.class);
    }

    @Test
    void searchByDepartment() {
        String department = "IT";
        Users user = new Users();
        UsersDTO userDTO = new UsersDTO();
        Page<Users> usersPage = new PageImpl<>(Collections.singletonList(user));
        Page<UsersDTO> usersDTOPage = new PageImpl<>(Collections.singletonList(userDTO));

        when(usersRepository.findByDepartment(department, PageRequest.of(0, 10))).thenReturn(usersPage);
        when(entityMapper.toDto(user, UsersDTO.class)).thenReturn(userDTO);

        Page<UsersDTO> result = usersService.searchByDepartment(department, 0, 10);

        assertEquals(usersDTOPage.getContent(), result.getContent());
        verify(usersRepository, times(1)).findByDepartment(department, PageRequest.of(0, 10));
        verify(entityMapper, times(1)).toDto(user, UsersDTO.class);
    }

    @Test
    void searchByDesignation() {
        String designation = "Software Engineer";
        Users user = new Users();
        UsersDTO userDTO = new UsersDTO();
        Page<Users> usersPage = new PageImpl<>(Collections.singletonList(user));
        Page<UsersDTO> usersDTOPage = new PageImpl<>(Collections.singletonList(userDTO));

        when(usersRepository.findByDesignation(designation, PageRequest.of(0, 10))).thenReturn(usersPage);
        when(entityMapper.toDto(user, UsersDTO.class)).thenReturn(userDTO);

        Page<UsersDTO> result = usersService.searchByDesignation(designation, 0, 10);

        assertEquals(usersDTOPage.getContent(), result.getContent());
        verify(usersRepository, times(1)).findByDesignation(designation, PageRequest.of(0, 10));
        verify(entityMapper, times(1)).toDto(user, UsersDTO.class);
    }
    // Add more tests as needed

}
