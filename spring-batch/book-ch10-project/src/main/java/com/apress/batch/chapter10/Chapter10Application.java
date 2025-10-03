package com.apress.batch.chapter10;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class Chapter10Application {

	public static void main(String[] args) {
		String[] realArgs =
				{"customerUpdateFile=file:C:\\TEMP\\CODE\\book_ch10-project\\src\\main\\resources\\data\\customer_update_shuffled.csv",
				 "transactionFile=file:C:\\TEMP\\CODE\\book_ch10-project\\src\\main\\resources\\data\\transactions.xml",
				 "outputDirectory=file:C:\\TEMP\\CODE\\book_ch10-project\\src\\main\\resources\\data\\statements\\statements\\"};

		SpringApplication.run(Chapter10Application.class, realArgs);
	}
}
