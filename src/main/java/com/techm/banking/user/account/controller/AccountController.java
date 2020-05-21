package com.techm.banking.user.account.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techm.banking.user.account.dto.AccountDto;
import com.techm.banking.user.account.model.Account;
import com.techm.banking.user.account.model.Beneficiary;
import com.techm.banking.user.account.service.AccountService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping
	public ResponseEntity<List<Account>> getAccounts() {
		return new ResponseEntity<List<Account>>(accountService.getAccounts(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable long id) {
		return new ResponseEntity<AccountDto>(accountService.getAccountById(id), HttpStatus.OK);
	}

	@PutMapping("/beneficiaries/id/{id}")
	public ResponseEntity<AccountDto> addBeneficiary(@PathVariable long id,
			@RequestBody List<Beneficiary> beneficiaries) {
		return new ResponseEntity<AccountDto>(accountService.addBeneficiary(id, beneficiaries), HttpStatus.OK);
	}

	@GetMapping("/others/account/{userId}")
	public ResponseEntity<List<Account>> getAccountsOfOthers(@PathVariable long userId) {
		return new ResponseEntity<List<Account>>(accountService.getAccountsOfOthers(userId), HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<AccountDto>> getAccountsByUserId(@PathVariable long userId) {
		return new ResponseEntity<List<AccountDto>>(accountService.getAccountsByUserId(userId), HttpStatus.OK);
	}

	@GetMapping("/deposit/id/{id}/amount/{amount}")
	public ResponseEntity<Account> depositAmount(@PathVariable long id, @PathVariable double amount)
			throws ParseException {
		return new ResponseEntity<Account>(accountService.depositAmount(id, amount), HttpStatus.OK);
	}

	@GetMapping("/withdraw/id/{id}/amount/{amount}")
	public ResponseEntity<Account> withdrawAmount(@PathVariable long id, @PathVariable double amount)
			throws ParseException {
		return new ResponseEntity<Account>(accountService.withdrawAmount(id, amount), HttpStatus.OK);
	}

	@GetMapping("/transfer/from/{sid}/to/{did}/amount/{amount}")
	public ResponseEntity<Account> transferAmount(@PathVariable long sid, @PathVariable long did,
			@PathVariable double amount) throws ParseException {
		return new ResponseEntity<Account>(accountService.transferAmount(sid, did, amount), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Account> createAccount(@RequestBody Account account) throws ParseException {
		return new ResponseEntity<Account>(accountService.createAccount(account), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable long id, @RequestBody Account account)
			throws ParseException {
		return new ResponseEntity<Account>(accountService.updateAccount(id, account), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void deleteAccount(@PathVariable long id) {
		accountService.deleteAccount(id);
	}

}
