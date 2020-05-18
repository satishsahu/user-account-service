package com.techm.banking.user.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techm.banking.user.account.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

	@Query(value = "select a from Account a where a.userId=:userId")
	public List<Account> getAccountsByUserId(@Param("userId") long userId);
	
	@Query(value = "select a from Account a where a.id!=:accountId")
	public List<Account> getAccountsOfOthers(@Param("accountId") long accountId);
}
