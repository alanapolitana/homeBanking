package com.mindhub.homebanking.services;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.dtos.AccountDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {
  List<AccountDTO> getAccounts();
  AccountDTO getAccountById(Long id);

    Account findByNumber(String fromAccountNumber);

    Long cont();
  void saveAccount(Account newAccount);
}
