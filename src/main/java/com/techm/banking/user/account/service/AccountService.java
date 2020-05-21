package com.techm.banking.user.account.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techm.banking.user.account.dto.AccountDto;
import com.techm.banking.user.account.exception.ValidationException;
import com.techm.banking.user.account.model.Account;
import com.techm.banking.user.account.model.Beneficiary;
import com.techm.banking.user.account.repository.AccountRepository;
import com.techm.banking.user.account.repository.BeneficiaryRepository;

@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	BeneficiaryRepository beneficiaryRepository;

	public List<Account> getAccounts() {
		return (List<Account>) accountRepository.findAll();
	}

	public AccountDto getAccountById(long id) {
		Account account = (Account) accountRepository.findById(id).get();
		AccountDto accountDto = new AccountDto(account.getId(), account.getBalance(), account.getDebit(),
				account.getCredit(), account.getUserId(), account.getType(), account.getTransactionDate(),
				account.getCreationDate(), beneficiaryRepository.getBeneficiariesForAccount(account.getId()));
		return accountDto;
	}

	public Account createAccount(Account account) throws ParseException {
		account.setTransactionDate(changeDateFormat(new Date()));
		account.setCreationDate(changeDateFormat(new Date()));
		if (account.getBalance() > 0)
			account.setCredit(account.getBalance());
		return accountRepository.save(account);
	}

	private Date changeDateFormat(Date date) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
		return formatter1.parse(formatter1.format(formatter.parse(date.toString())));
	}

	public Account updateAccount(long id, Account account) throws ParseException {
		Account accountDetached = accountRepository.findById(id).get();
		accountDetached.setBalance(account.getBalance());
		accountDetached.setCredit(account.getCredit());
		accountDetached.setDebit(account.getDebit());
		accountDetached.setType(account.getType());
		accountDetached.setUserId(account.getUserId());
		accountDetached.setBeneficiaries(account.getBeneficiaries());
		account.setTransactionDate(changeDateFormat(new Date()));
		return accountRepository.save(accountDetached);
	}

	public void deleteAccount(long id) {
		accountRepository.deleteById(id);
	}

	public AccountDto addBeneficiary(long id, List<Beneficiary> beneficiaries) {
		Account accountDetach = accountRepository.findById(id).get();
		accountDetach.getBeneficiaries().addAll(beneficiaries);
		Account account = accountRepository.save(accountDetach);
		AccountDto accountDto = new AccountDto(account.getId(), account.getBalance(), account.getDebit(),
				account.getCredit(), account.getUserId(), account.getType(), account.getTransactionDate(),
				account.getCreationDate(), beneficiaryRepository.getBeneficiariesForAccount(account.getId()));
		return accountDto;
	}

	public List<Account> getAccountsOfOthers(long userId) {
		return accountRepository.getAccountsOfOthers(userId);
	}

	public List<AccountDto> getAccountsByUserId(long userId) {
		List<AccountDto> accountDtos = new ArrayList<>();
		for (Account account : accountRepository.getAccountsByUserId(userId)) {
			AccountDto accountDto = new AccountDto(account.getId(), account.getBalance(), account.getDebit(),
					account.getCredit(), account.getUserId(), account.getType(), account.getTransactionDate(),
					account.getCreationDate(), beneficiaryRepository.getBeneficiariesForAccount(account.getId()));
			accountDtos.add(accountDto);
		}
		return accountDtos;
	}

	public Account depositAmount(long id, double amount) throws ParseException {
		Account accountDetach = accountRepository.findById(id).get();
		accountDetach.setBalance(accountDetach.getBalance() + amount);
		accountDetach.setCredit(amount);
		accountDetach.setTransactionDate(changeDateFormat(new Date()));
		return accountRepository.save(accountDetach);
	}

	public Account withdrawAmount(long id, double amount) throws ParseException {
		Account accountDetach = accountRepository.findById(id).get();
		if (accountDetach.getBalance() < amount)
			throw new ValidationException("Insufficient balance for withdraw");
		accountDetach.setBalance(accountDetach.getBalance() - amount);
		accountDetach.setDebit(amount);
		accountDetach.setTransactionDate(changeDateFormat(new Date()));
		return accountRepository.save(accountDetach);
	}

	@Transactional
	public Account transferAmount(long sid, long did, double amount) throws ParseException {
		Account sourceAccount = accountRepository.findById(sid).get();
		AccountDto sourceAccountDto = getAccountById(sid);
		Account destinationAccount = accountRepository.findById(did).get();
		if (sourceAccount.getBalance() < amount)
			throw new ValidationException("Insufficient balance for transfer");
		else if (!containsBeneficiary(sourceAccountDto.getBeneficiaries(), destinationAccount.getId()))
			throw new ValidationException("Not a valid beneficiary");
		else {
			// transaction not yet applied
			destinationAccount.setCredit(amount);
			destinationAccount.setBalance(destinationAccount.getBalance() + amount);
			destinationAccount.setTransactionDate(changeDateFormat(new Date()));
			sourceAccount.setDebit(amount);
			sourceAccount.setBalance(sourceAccount.getBalance() - amount);
			sourceAccount.setTransactionDate(changeDateFormat(new Date()));
		}
		// transaction applied
		accountRepository.save(destinationAccount);
		return accountRepository.save(sourceAccount);
	}

	private boolean containsBeneficiary(final List<Beneficiary> list, final long accountId) {
		return list.stream().filter(o -> o.getBeneficiaryId() == accountId).findFirst().isPresent();
	}
}
