package com.raj.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NewUserMapperTest {

    private NewUserMapper newUserMapper;

    @BeforeEach
    public void setUp() {
        newUserMapper = new NewUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(newUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(newUserMapper.fromId(null)).isNull();
    }
}
