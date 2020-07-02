package com.raj.app.service.impl;

import com.raj.app.service.AdminUserService;
import com.raj.app.domain.AdminUser;
import com.raj.app.repository.AdminUserRepository;
import com.raj.app.service.dto.AdminUserDTO;
import com.raj.app.service.mapper.AdminUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AdminUser}.
 */
@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

    private final Logger log = LoggerFactory.getLogger(AdminUserServiceImpl.class);

    private final AdminUserRepository adminUserRepository;

    private final AdminUserMapper adminUserMapper;

    public AdminUserServiceImpl(AdminUserRepository adminUserRepository, AdminUserMapper adminUserMapper) {
        this.adminUserRepository = adminUserRepository;
        this.adminUserMapper = adminUserMapper;
    }

    @Override
    public AdminUserDTO save(AdminUserDTO adminUserDTO) {
        log.debug("Request to save AdminUser : {}", adminUserDTO);
        AdminUser adminUser = adminUserMapper.toEntity(adminUserDTO);
        adminUser = adminUserRepository.save(adminUser);
        return adminUserMapper.toDto(adminUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminUserDTO> findAll() {
        log.debug("Request to get all AdminUsers");
        return adminUserRepository.findAll().stream()
            .map(adminUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AdminUserDTO> findOne(Long id) {
        log.debug("Request to get AdminUser : {}", id);
        return adminUserRepository.findById(id)
            .map(adminUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdminUser : {}", id);
        adminUserRepository.deleteById(id);
    }
}
