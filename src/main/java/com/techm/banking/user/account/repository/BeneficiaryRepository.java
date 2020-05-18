package com.techm.banking.user.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techm.banking.user.account.model.Beneficiary;

@Repository
public interface BeneficiaryRepository extends CrudRepository<Beneficiary, Long>{

	@Query(value = "select b from Beneficiary b where b.account.id=:accountId")
	public List<Beneficiary> getBeneficiariesForAccount(@Param("accountId") long accountId);
}
