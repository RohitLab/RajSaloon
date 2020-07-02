package com.raj.app.service.impl;

import com.raj.app.service.NewUserService;
import com.raj.app.domain.NewUser;
import com.raj.app.repository.NewUserRepository;
import com.raj.app.service.dto.NewUserDTO;
import com.raj.app.service.mapper.NewUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NewUser}.
 */
@Service
@Transactional
public class NewUserServiceImpl implements NewUserService {

    private final Logger log = LoggerFactory.getLogger(NewUserServiceImpl.class);

    private final NewUserRepository newUserRepository;

    private final NewUserMapper newUserMapper;

    public NewUserServiceImpl(NewUserRepository newUserRepository, NewUserMapper newUserMapper) {
        this.newUserRepository = newUserRepository;
        this.newUserMapper = newUserMapper;
    }

    @Override
    public NewUserDTO save(NewUserDTO newUserDTO) {
        log.debug("Request to save NewUser : {}", newUserDTO);
        NewUser newUser = newUserMapper.toEntity(newUserDTO);
        newUser = newUserRepository.save(newUser);
        return newUserMapper.toDto(newUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NewUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NewUsers");
        return newUserRepository.findAll(pageable)
            .map(newUserMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NewUserDTO> findOne(Long id) {
        log.debug("Request to get NewUser : {}", id);
        return newUserRepository.findById(id)
            .map(newUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NewUser : {}", id);
        newUserRepository.deleteById(id);
    }
}
