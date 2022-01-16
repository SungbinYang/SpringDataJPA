package me.sungbin.querdsldemo.account;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void crud_테스트() {
        QAccount account = QAccount.account;
        Predicate predicate = QAccount.account.firstName.containsIgnoreCase("sungbin")
                .and(account.lastName.startsWith("yang"));

        Optional<Account> one = accountRepository.findOne(predicate);
        assertThat(one).isEmpty();
    }
}