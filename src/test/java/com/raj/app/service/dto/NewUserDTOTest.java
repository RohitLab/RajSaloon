package com.raj.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.raj.app.web.rest.TestUtil;

public class NewUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewUserDTO.class);
        NewUserDTO newUserDTO1 = new NewUserDTO();
        newUserDTO1.setId(1L);
        NewUserDTO newUserDTO2 = new NewUserDTO();
        assertThat(newUserDTO1).isNotEqualTo(newUserDTO2);
        newUserDTO2.setId(newUserDTO1.getId());
        assertThat(newUserDTO1).isEqualTo(newUserDTO2);
        newUserDTO2.setId(2L);
        assertThat(newUserDTO1).isNotEqualTo(newUserDTO2);
        newUserDTO1.setId(null);
        assertThat(newUserDTO1).isNotEqualTo(newUserDTO2);
    }
}
