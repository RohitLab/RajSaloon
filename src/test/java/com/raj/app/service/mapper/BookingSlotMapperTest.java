package com.raj.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingSlotMapperTest {

    private BookingSlotMapper bookingSlotMapper;

    @BeforeEach
    public void setUp() {
        bookingSlotMapper = new BookingSlotMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bookingSlotMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bookingSlotMapper.fromId(null)).isNull();
    }
}
