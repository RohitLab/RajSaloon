package com.raj.app.web.rest;

import com.raj.app.RajsaloonApp;
import com.raj.app.domain.AdminUser;
import com.raj.app.repository.AdminUserRepository;
import com.raj.app.service.AdminUserService;
import com.raj.app.service.dto.AdminUserDTO;
import com.raj.app.service.mapper.AdminUserMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AdminUserResource} REST controller.
 */
@SpringBootTest(classes = RajsaloonApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdminUserResourceIT {

    private static final UUID DEFAULT_USER_ID = UUID.randomUUID();
    private static final UUID UPDATED_USER_ID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ENCRYPTED_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_ENCRYPTED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminUserMockMvc;

    private AdminUser adminUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminUser createEntity(EntityManager em) {
        AdminUser adminUser = new AdminUser()
            .userId(DEFAULT_USER_ID)
            .name(DEFAULT_NAME)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .encryptedPassword(DEFAULT_ENCRYPTED_PASSWORD);
        return adminUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminUser createUpdatedEntity(EntityManager em) {
        AdminUser adminUser = new AdminUser()
            .userId(UPDATED_USER_ID)
            .name(UPDATED_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .encryptedPassword(UPDATED_ENCRYPTED_PASSWORD);
        return adminUser;
    }

    @BeforeEach
    public void initTest() {
        adminUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdminUser() throws Exception {
        int databaseSizeBeforeCreate = adminUserRepository.findAll().size();
        // Create the AdminUser
        AdminUserDTO adminUserDTO = adminUserMapper.toDto(adminUser);
        restAdminUserMockMvc.perform(post("/api/admin-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adminUserDTO)))
            .andExpect(status().isCreated());

        // Validate the AdminUser in the database
        List<AdminUser> adminUserList = adminUserRepository.findAll();
        assertThat(adminUserList).hasSize(databaseSizeBeforeCreate + 1);
        AdminUser testAdminUser = adminUserList.get(adminUserList.size() - 1);
        assertThat(testAdminUser.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAdminUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdminUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testAdminUser.getEncryptedPassword()).isEqualTo(DEFAULT_ENCRYPTED_PASSWORD);
    }

    @Test
    @Transactional
    public void createAdminUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adminUserRepository.findAll().size();

        // Create the AdminUser with an existing ID
        adminUser.setId(1L);
        AdminUserDTO adminUserDTO = adminUserMapper.toDto(adminUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminUserMockMvc.perform(post("/api/admin-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adminUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdminUser in the database
        List<AdminUser> adminUserList = adminUserRepository.findAll();
        assertThat(adminUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEncryptedPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminUserRepository.findAll().size();
        // set the field null
        adminUser.setEncryptedPassword(null);

        // Create the AdminUser, which fails.
        AdminUserDTO adminUserDTO = adminUserMapper.toDto(adminUser);


        restAdminUserMockMvc.perform(post("/api/admin-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adminUserDTO)))
            .andExpect(status().isBadRequest());

        List<AdminUser> adminUserList = adminUserRepository.findAll();
        assertThat(adminUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdminUsers() throws Exception {
        // Initialize the database
        adminUserRepository.saveAndFlush(adminUser);

        // Get all the adminUserList
        restAdminUserMockMvc.perform(get("/api/admin-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].encryptedPassword").value(hasItem(DEFAULT_ENCRYPTED_PASSWORD)));
    }
    
    @Test
    @Transactional
    public void getAdminUser() throws Exception {
        // Initialize the database
        adminUserRepository.saveAndFlush(adminUser);

        // Get the adminUser
        restAdminUserMockMvc.perform(get("/api/admin-users/{id}", adminUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminUser.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.encryptedPassword").value(DEFAULT_ENCRYPTED_PASSWORD));
    }
    @Test
    @Transactional
    public void getNonExistingAdminUser() throws Exception {
        // Get the adminUser
        restAdminUserMockMvc.perform(get("/api/admin-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdminUser() throws Exception {
        // Initialize the database
        adminUserRepository.saveAndFlush(adminUser);

        int databaseSizeBeforeUpdate = adminUserRepository.findAll().size();

        // Update the adminUser
        AdminUser updatedAdminUser = adminUserRepository.findById(adminUser.getId()).get();
        // Disconnect from session so that the updates on updatedAdminUser are not directly saved in db
        em.detach(updatedAdminUser);
        updatedAdminUser
            .userId(UPDATED_USER_ID)
            .name(UPDATED_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .encryptedPassword(UPDATED_ENCRYPTED_PASSWORD);
        AdminUserDTO adminUserDTO = adminUserMapper.toDto(updatedAdminUser);

        restAdminUserMockMvc.perform(put("/api/admin-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adminUserDTO)))
            .andExpect(status().isOk());

        // Validate the AdminUser in the database
        List<AdminUser> adminUserList = adminUserRepository.findAll();
        assertThat(adminUserList).hasSize(databaseSizeBeforeUpdate);
        AdminUser testAdminUser = adminUserList.get(adminUserList.size() - 1);
        assertThat(testAdminUser.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAdminUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdminUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAdminUser.getEncryptedPassword()).isEqualTo(UPDATED_ENCRYPTED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingAdminUser() throws Exception {
        int databaseSizeBeforeUpdate = adminUserRepository.findAll().size();

        // Create the AdminUser
        AdminUserDTO adminUserDTO = adminUserMapper.toDto(adminUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminUserMockMvc.perform(put("/api/admin-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adminUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdminUser in the database
        List<AdminUser> adminUserList = adminUserRepository.findAll();
        assertThat(adminUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdminUser() throws Exception {
        // Initialize the database
        adminUserRepository.saveAndFlush(adminUser);

        int databaseSizeBeforeDelete = adminUserRepository.findAll().size();

        // Delete the adminUser
        restAdminUserMockMvc.perform(delete("/api/admin-users/{id}", adminUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminUser> adminUserList = adminUserRepository.findAll();
        assertThat(adminUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
