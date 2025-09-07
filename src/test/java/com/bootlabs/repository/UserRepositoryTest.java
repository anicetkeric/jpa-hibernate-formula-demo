package com.bootlabs.repository;

import com.bootlabs.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCalculateAgeCorrectly() {
        // Given
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(LocalDate.of(1990, 5, 15));
        user.setSalary(new BigDecimal("50000"));

        entityManager.persistAndFlush(user);

        // ❗ Clear persistence context so Hibernate will re-query the DB
        entityManager.clear();

        // When
        User savedUser = userRepository.findById(user.getId()).orElseThrow();

        // Then
        assertThat(savedUser.getFullName()).isEqualTo("John Doe");
        assertThat(savedUser.getAge()).isGreaterThan(30);
        assertThat(savedUser.getTaxAmount()).isEqualByComparingTo(new BigDecimal("5000.00"));
    }



}