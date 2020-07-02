package com.raj.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminUserMapperTest {

    private AdminUserMapper adminUserMapper;

    @BeforeEach
    public void setUp() {
        adminUserMapper = new AdminUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adminUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adminUserMapper.fromId(null)).isNull();
    }
}
