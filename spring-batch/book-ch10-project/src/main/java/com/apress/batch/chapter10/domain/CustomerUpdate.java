package com.apress.batch.chapter10.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CustomerUpdate {

	protected final long customerId;
}
