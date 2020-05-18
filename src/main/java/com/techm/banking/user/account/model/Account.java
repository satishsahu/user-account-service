package com.techm.banking.user.account.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Beneficiary> beneficiaries;

	public Account() {
	}

	public Account(long id, double balance, double debit, double credit, long userId, String type, Date transactionDate,
			Date creationDate, List<Beneficiary> beneficiaries) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id != other.id)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

}
