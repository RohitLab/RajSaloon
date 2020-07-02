package com.raj.app.web.rest;

import com.raj.app.RajsaloonApp;
import com.raj.app.domain.BookingSlot;
import com.raj.app.repository.BookingSlotRepository;
import com.raj.app.service.BookingSlotService;
import com.raj.app.service.dto.BookingSlotDTO;
import com.raj.app.service.mapper.BookingSlotMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BookingSlotResource} REST controller.
 */
@SpringBootTest(classes = RajsaloonApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BookingSlotResourceIT {

    private static final Long DEFAULT_BOOKING_ID = 1L;
    private static final Long UPDATED_BOOKING_ID = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BOOKING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BOOKING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SLOT_STARTING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SLOT_STARTING = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SLOT_ENDING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SLOT_ENDING = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BookingSlotRepository bookingSlotRepository;

    @Autowired
    private BookingSlotMapper bookingSlotMapper;

    @Autowired
    private BookingSlotService bookingSlotService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookingSlotMockMvc;

    private BookingSlot bookingSlot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookingSlot createEntity(EntityManager em) {
        BookingSlot bookingSlot = new BookingSlot()
            .bookingId(DEFAULT_BOOKING_ID)
            .description(DEFAULT_DESCRIPTION)
            .bookingDate(DEFAULT_BOOKING_DATE)
            .slotStarting(DEFAULT_SLOT_STARTING)
            .slotEnding(DEFAULT_SLOT_ENDING);
        return bookingSlot;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookingSlot createUpdatedEntity(EntityManager em) {
        BookingSlot bookingSlot = new BookingSlot()
            .bookingId(UPDATED_BOOKING_ID)
            .description(UPDATED_DESCRIPTION)
            .bookingDate(UPDATED_BOOKING_DATE)
            .slotStarting(UPDATED_SLOT_STARTING)
            .slotEnding(UPDATED_SLOT_ENDING);
        return bookingSlot;
    }

    @BeforeEach
    public void initTest() {
        bookingSlot = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookingSlot() throws Exception {
        int databaseSizeBeforeCreate = bookingSlotRepository.findAll().size();
        // Create the BookingSlot
        BookingSlotDTO bookingSlotDTO = bookingSlotMapper.toDto(bookingSlot);
        restBookingSlotMockMvc.perform(post("/api/booking-slots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookingSlotDTO)))
            .andExpect(status().isCreated());

        // Validate the BookingSlot in the database
        List<BookingSlot> bookingSlotList = bookingSlotRepository.findAll();
        assertThat(bookingSlotList).hasSize(databaseSizeBeforeCreate + 1);
        BookingSlot testBookingSlot = bookingSlotList.get(bookingSlotList.size() - 1);
        assertThat(testBookingSlot.getBookingId()).isEqualTo(DEFAULT_BOOKING_ID);
        assertThat(testBookingSlot.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBookingSlot.getBookingDate()).isEqualTo(DEFAULT_BOOKING_DATE);
        assertThat(testBookingSlot.getSlotStarting()).isEqualTo(DEFAULT_SLOT_STARTING);
        assertThat(testBookingSlot.getSlotEnding()).isEqualTo(DEFAULT_SLOT_ENDING);
    }

    @Test
    @Transactional
    public void createBookingSlotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookingSlotRepository.findAll().size();

        // Create the BookingSlot with an existing ID
        bookingSlot.setId(1L);
        BookingSlotDTO bookingSlotDTO = bookingSlotMapper.toDto(bookingSlot);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingSlotMockMvc.perform(post("/api/booking-slots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookingSlotDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookingSlot in the database
        List<BookingSlot> bookingSlotList = bookingSlotRepository.findAll();
        assertThat(bookingSlotList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBookingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingSlotRepository.findAll().size();
        // set the field null
        bookingSlot.setBookingDate(null);

        // Create the BookingSlot, which fails.
        BookingSlotDTO bookingSlotDTO = bookingSlotMapper.toDto(bookingSlot);


        restBookingSlotMockMvc.perform(post("/api/booking-slots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookingSlotDTO)))
            .andExpect(status().isBadRequest());

        List<BookingSlot> bookingSlotList = bookingSlotRepository.findAll();
        assertThat(bookingSlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBookingSlots() throws Exception {
        // Initialize the database
        bookingSlotRepository.saveAndFlush(bookingSlot);

        // Get all the bookingSlotList
        restBookingSlotMockMvc.perform(get("/api/booking-slots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookingSlot.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookingId").value(hasItem(DEFAULT_BOOKING_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].bookingDate").value(hasItem(DEFAULT_BOOKING_DATE.toString())))
            .andExpect(jsonPath("$.[*].slotStarting").value(hasItem(DEFAULT_SLOT_STARTING.toString())))
            .andExpect(jsonPath("$.[*].slotEnding").value(hasItem(DEFAULT_SLOT_ENDING.toString())));
    }
    
    @Test
    @Transactional
    public void getBookingSlot() throws Exception {
        // Initialize the database
        bookingSlotRepository.saveAndFlush(bookingSlot);

        // Get the bookingSlot
        restBookingSlotMockMvc.perform(get("/api/booking-slots/{id}", bookingSlot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookingSlot.getId().intValue()))
            .andExpect(jsonPath("$.bookingId").value(DEFAULT_BOOKING_ID.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.bookingDate").value(DEFAULT_BOOKING_DATE.toString()))
            .andExpect(jsonPath("$.slotStarting").value(DEFAULT_SLOT_STARTING.toString()))
            .andExpect(jsonPath("$.slotEnding").value(DEFAULT_SLOT_ENDING.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBookingSlot() throws Exception {
        // Get the bookingSlot
        restBookingSlotMockMvc.perform(get("/api/booking-slots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookingSlot() throws Exception {
        // Initialize the database
        bookingSlotRepository.saveAndFlush(bookingSlot);

        int databaseSizeBeforeUpdate = bookingSlotRepository.findAll().size();

        // Update the bookingSlot
        BookingSlot updatedBookingSlot = bookingSlotRepository.findById(bookingSlot.getId()).get();
        // Disconnect from session so that the updates on updatedBookingSlot are not directly saved in db
        em.detach(updatedBookingSlot);
        updatedBookingSlot
            .bookingId(UPDATED_BOOKING_ID)
            .description(UPDATED_DESCRIPTION)
            .bookingDate(UPDATED_BOOKING_DATE)
            .slotStarting(UPDATED_SLOT_STARTING)
            .slotEnding(UPDATED_SLOT_ENDING);
        BookingSlotDTO bookingSlotDTO = bookingSlotMapper.toDto(updatedBookingSlot);

        restBookingSlotMockMvc.perform(put("/api/booking-slots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookingSlotDTO)))
            .andExpect(status().isOk());

        // Validate the BookingSlot in the database
        List<BookingSlot> bookingSlotList = bookingSlotRepository.findAll();
        assertThat(bookingSlotList).hasSize(databaseSizeBeforeUpdate);
        BookingSlot testBookingSlot = bookingSlotList.get(bookingSlotList.size() - 1);
        assertThat(testBookingSlot.getBookingId()).isEqualTo(UPDATED_BOOKING_ID);
        assertThat(testBookingSlot.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBookingSlot.getBookingDate()).isEqualTo(UPDATED_BOOKING_DATE);
        assertThat(testBookingSlot.getSlotStarting()).isEqualTo(UPDATED_SLOT_STARTING);
        assertThat(testBookingSlot.getSlotEnding()).isEqualTo(UPDATED_SLOT_ENDING);
    }

    @Test
    @Transactional
    public void updateNonExistingBookingSlot() throws Exception {
        int databaseSizeBeforeUpdate = bookingSlotRepository.findAll().size();

        // Create the BookingSlot
        BookingSlotDTO bookingSlotDTO = bookingSlotMapper.toDto(bookingSlot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingSlotMockMvc.perform(put("/api/booking-slots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookingSlotDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookingSlot in the database
        List<BookingSlot> bookingSlotList = bookingSlotRepository.findAll();
        assertThat(bookingSlotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBookingSlot() throws Exception {
        // Initialize the database
        bookingSlotRepository.saveAndFlush(bookingSlot);

        int databaseSizeBeforeDelete = bookingSlotRepository.findAll().size();

        // Delete the bookingSlot
        restBookingSlotMockMvc.perform(delete("/api/booking-slots/{id}", bookingSlot.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookingSlot> bookingSlotList = bookingSlotRepository.findAll();
        assertThat(bookingSlotList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
