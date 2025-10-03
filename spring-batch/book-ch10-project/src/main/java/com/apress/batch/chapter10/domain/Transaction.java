package com.apress.batch.chapter10.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.apress.batch.chapter10.batch.JaxbDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "transaction")
public class Transaction {

	private long transactionId;

	private long accountId;

	private String description;

	private BigDecimal credit;

	private BigDecimal debit;

	private Date timestamp;

	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getTransactionAmount() {
		if(credit != null) {
			if(debit != null) {
				return credit.add(debit);
			}
			else {
				return credit;
			}
		}
		else if(debit != null) {
			return debit;
		}
		else {
			return new BigDecimal(0);
		}
	}
}
