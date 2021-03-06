package com.springboilerplate.app.passwordResetToken;

import com.springboilerplate.app.user.UserStubs;
import com.springboilerplate.app.passwordRestToken.PasswordResetToken;
import com.springboilerplate.app.passwordRestToken.PasswordResetTokenRepository;
import com.springboilerplate.app.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PasswordResetTokenRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PasswordResetTokenRepository resetTokenRepository;

    private PasswordResetToken passwordToken;
    private User user;

    @Before
    public void setUp() throws Exception {
        user = UserStubs.generateUserWithNoRole();
        entityManager.persistAndFlush(user);
        passwordToken = PasswordResetTokenStubs.generatePasswordResetToken();
    }

    @Test
    public void findByTokenWhenTokenIsValidShouldRetrieveToken() throws Exception {
        passwordToken.setUser(user);
        entityManager.persistAndFlush(passwordToken);
        String tokenValue = passwordToken.getToken();

        Optional<PasswordResetToken> retrivedOptionalToken = resetTokenRepository.findByToken(tokenValue);

        assertThat(retrivedOptionalToken.isPresent()).isTrue();
    }

}