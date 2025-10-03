package io.spring.batch.configuration;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;


@Configuration
@AllArgsConstructor
public class SplitConfiguration {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

//    @Bean
//    public Step myStep1() {
//        return stepBuilderFactory.get("myStep1")
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println("myStep was executed");
//                    return RepeatStatus.FINISHED;
//                }).build();
//    }
//
//    @Bean
//    public Step myStep2() {
//        return stepBuilderFactory.get("myStep2")
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println("myStep was executed");
//                    return RepeatStatus.FINISHED;
//                }).build();
//    }
//
//    @Bean
//    public Job splitJob(@Qualifier("foo") Flow foo, @Qualifier("foo") Flow bar) {
//        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("split");
//
//        Flow flow = flowBuilder.split(new SimpleAsyncTaskExecutor())
//                .add(foo, bar)
//                .end();
//
//        return jobBuilderFactory.get("splitJob")
//                .start(myStep1())
//                .next(myStep2())
//                .on("COMPLETED").to(flow)
//                .end()
//                .build();
//    }
}
