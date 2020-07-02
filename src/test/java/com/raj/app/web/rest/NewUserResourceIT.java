package com.raj.app.web.rest;

import com.raj.app.RajsaloonApp;
import com.raj.app.domain.NewUser;
import com.raj.app.repository.NewUserRepository;
import com.raj.app.service.NewUserService;
import com.raj.app.service.dto.NewUserDTO;
import com.raj.app.service.mapper.NewUserMapper;

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
 * Integration tests for the {@link NewUserResource} REST controller.
 */
@SpringBootTest(classes = RajsaloonApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NewUserResourceIT {

    private static final UUID DEFAULT_USER_ID = UUID.randomUUID();
    private static final UUID UPDATED_USER_ID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private NewUserRepository newUserRepository;

    @Autowired
    private NewUserMapper newUserMapper;

    @Autowired
    private NewUserService newUserService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNewUserMockMvc;

    private NewUser newUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewUser createEntity(EntityManager em) {
        NewUser newUser = new NewUser()
            .userId(DEFAULT_USER_ID)
            .name(DEFAULT_NAME)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return newUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewUser createUpdatedEntity(EntityManager em) {
        NewUser newUser = new NewUser()
            .userId(UPDATED_USER_ID)
            .name(UPDATED_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return newUser;
    }

    @BeforeEach
    public void initTest() {
        newUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createNewUser() throws Exception {
        int databaseSizeBeforeCreate = newUserRepository.findAll().size();
        // Create the NewUser
        NewUserDTO newUserDTO = newUserMapper.toDto(newUser);
        restNewUserMockMvc.perform(post("/api/new-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newUserDTO)))
            .andExpect(status().isCreated());

        // Validate the NewUser in the database
        List<NewUser> newUserList = newUserRepository.findAll();
        assertThat(newUserList).hasSize(databaseSizeBeforeCreate + 1);
        NewUser testNewUser = newUserList.get(newUserList.size() - 1);
        assertThat(testNewUser.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testNewUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNewUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createNewUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = newUserRepository.findAll().size();

        // Create the NewUser with an existing ID
        newUser.setId(1L);
        NewUserDTO newUserDTO = newUserMapper.toDto(newUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewUserMockMvc.perform(post("/api/new-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NewUser in the database
        List<NewUser> newUserList = newUserRepository.findAll();
        assertThat(newUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = newUserRepository.findAll().size();
        // set the field null
        newUser.setUserId(null);

        // Create the NewUser, which fails.
        NewUserDTO newUserDTO = newUserMapper.toDto(newUser);


        restNewUserMockMvc.perform(post("/api/new-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newUserDTO)))
            .andExpect(status().isBadRequest());

        List<NewUser> newUserList = newUserRepository.findAll();
        assertThat(newUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNewUsers() throws Exception {
        // Initialize the database
        newUserRepository.saveAndFlush(newUser);

        // Get all the newUserList
        restNewUserMockMvc.perform(get("/api/new-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getNewUser() throws Exception {
        // Initialize the database
        newUserRepository.saveAndFlush(newUser);

        // Get the newUser
        restNewUserMockMvc.perform(get("/api/new-users/{id}", newUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(newUser.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingNewUser() throws Exception {
        // Get the newUser
        restNewUserMockMvc.perform(get("/api/new-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNewUser() throws Exception {
        // Initialize the database
        newUserRepository.saveAndFlush(newUser);

        int databaseSizeBeforeUpdate = newUserRepository.findAll().size();

        // Update the newUser
        NewUser updatedNewUser = newUserRepository.findById(newUser.getId()).get();
        // Disconnect from session so that the updates on updatedNewUser are not directly saved in db
        em.detach(updatedNewUser);
        updatedNewUser
            .userId(UPDATED_USER_ID)
            .name(UPDATED_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        NewUserDTO newUserDTO = newUserMapper.toDto(updatedNewUser);

        restNewUserMockMvc.perform(put("/api/new-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newUserDTO)))
            .andExpect(status().isOk());

        // Validate the NewUser in the database
        List<NewUser> newUserList = newUserRepository.findAll();
        assertThat(newUserList).hasSize(databaseSizeBeforeUpdate);
        NewUser testNewUser = newUserList.get(newUserList.size() - 1);
        assertThat(testNewUser.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testNewUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNewUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingNewUser() throws Exception {
        int databaseSizeBeforeUpdate = newUserRepository.findAll().size();

        // Create the NewUser
        NewUserDTO newUserDTO = newUserMapper.toDto(newUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewUserMockMvc.perform(put("/api/new-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NewUser in the database
        List<NewUser> newUserList = newUserRepository.findAll();
        assertThat(newUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNewUser() throws Exception {
        // Initialize the database
        newUserRepository.saveAndFlush(newUser);

        int databaseSizeBeforeDelete = newUserRepository.findAll().size();

        // Delete the newUser
        restNewUserMockMvc.perform(delete("/api/new-users/{id}", newUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NewUser> newUserList = newUserRepository.findAll();
        assertThat(newUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
