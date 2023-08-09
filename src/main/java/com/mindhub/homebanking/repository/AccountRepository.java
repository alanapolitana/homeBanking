package com.mindhub.homebanking.repository;

import com.mindhub.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
