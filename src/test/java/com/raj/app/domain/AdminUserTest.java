package com.raj.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.raj.app.web.rest.TestUtil;

public class AdminUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminUser.class);
        AdminUser adminUser1 = new AdminUser();
        adminUser1.setId(1L);
        AdminUser adminUser2 = new AdminUser();
        adminUser2.setId(adminUser1.getId());
        assertThat(adminUser1).isEqualTo(adminUser2);
        adminUser2.setId(2L);
        assertThat(adminUser1).isNotEqualTo(adminUser2);
        adminUser1.setId(null);
        assertThat(adminUser1).isNotEqualTo(adminUser2);
    }
}
