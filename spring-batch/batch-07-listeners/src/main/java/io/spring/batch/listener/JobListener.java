package io.spring.batch.listener;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

// using the JobExecutionListenerInterface
// it will send emails before or after the job
@AllArgsConstructor
public class JobListener implements JobExecutionListener {

	private final JavaMailSender mailSender;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		String jobName = jobExecution.getJobInstance().getJobName();

		SimpleMailMessage mail =
				getSimpleMailMessage(String.format("%s is starting", jobName),
						String.format("Per your request, we are informing you that %s is starting", jobName));

		mailSender.send(mail);
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		String jobName = jobExecution.getJobInstance().getJobName();

		SimpleMailMessage mail =
				getSimpleMailMessage(String.format("%s has completed", jobName),
						String.format("Per your request, we are informing you that %s has completed", jobName));

		mailSender.send(mail);
	}

	private SimpleMailMessage getSimpleMailMessage(String subject, String text) {
		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setTo("springbatch@mail.com");
		mail.setSubject(subject);
		mail.setText(text);
		return mail;
	}
}

