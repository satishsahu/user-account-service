package com.techm.banking.user.account.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

import com.techm.banking.user.account.model.Beneficiary;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDto {

	private long id;
	@Column(name = "balance", columnDefinition = "Decimal(10,2) default '0.00'")
	private double balance;
	@Column(name = "debit", columnDefinition = "Decimal(10,2) default '0.00'")
	private double debit;
	@Column(name = "credit", columnDefinition = "Decimal(10,2) default '0.00'")
	private double credit;
	private long userId;
	private String type;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date transactionDate;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date creationDate;
	private List<Beneficiary> beneficiaries;

	public AccountDto() {
	}

	public AccountDto(long id, double balance, double debit, double credit, long userId, String type,
			Date transactionDate, Date creationDate, List<Beneficiary> beneficiaries) {
		super();
		this.id = id;
		this.balance = balance;
		this.debit = debit;
		this.credit = credit;
		this.userId = userId;
		this.type = type;
		this.transactionDate = transactionDate;
		this.creationDate = creationDate;
		this.beneficiaries = beneficiaries;
	}

}
