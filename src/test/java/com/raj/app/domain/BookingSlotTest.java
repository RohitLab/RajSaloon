package com.raj.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.raj.app.web.rest.TestUtil;

public class BookingSlotTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookingSlot.class);
        BookingSlot bookingSlot1 = new BookingSlot();
        bookingSlot1.setId(1L);
        BookingSlot bookingSlot2 = new BookingSlot();
        bookingSlot2.setId(bookingSlot1.getId());
        assertThat(bookingSlot1).isEqualTo(bookingSlot2);
        bookingSlot2.setId(2L);
        assertThat(bookingSlot1).isNotEqualTo(bookingSlot2);
        bookingSlot1.setId(null);
        assertThat(bookingSlot1).isNotEqualTo(bookingSlot2);
    }
}
