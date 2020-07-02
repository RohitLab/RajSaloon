package com.raj.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.raj.app.web.rest.TestUtil;

public class BookingSlotDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookingSlotDTO.class);
        BookingSlotDTO bookingSlotDTO1 = new BookingSlotDTO();
        bookingSlotDTO1.setId(1L);
        BookingSlotDTO bookingSlotDTO2 = new BookingSlotDTO();
        assertThat(bookingSlotDTO1).isNotEqualTo(bookingSlotDTO2);
        bookingSlotDTO2.setId(bookingSlotDTO1.getId());
        assertThat(bookingSlotDTO1).isEqualTo(bookingSlotDTO2);
        bookingSlotDTO2.setId(2L);
        assertThat(bookingSlotDTO1).isNotEqualTo(bookingSlotDTO2);
        bookingSlotDTO1.setId(null);
        assertThat(bookingSlotDTO1).isNotEqualTo(bookingSlotDTO2);
    }
}
