package com.techm.banking.user.account.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Beneficiary {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	private long userId;
	private long beneficiaryId;

	public Beneficiary() {
	}

	public Beneficiary(long id, Account account, long userId, long beneficiaryId) {
		super();
		this.id = id;
		this.account = account;
		this.userId = userId;
		this.beneficiaryId = beneficiaryId;
	}
}
