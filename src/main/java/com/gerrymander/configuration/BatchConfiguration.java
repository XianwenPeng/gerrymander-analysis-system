package com.gerrymander.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gerrymander.beans.District;
import com.gerrymander.beans.Votes;
import com.gerrymander.constant.ControllerAttributes;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public FlatFileItemReader<Votes> readerVotes() {
        FlatFileItemReader<Votes> reader = new FlatFileItemReader<Votes>();
        reader.setResource(new ClassPathResource(ControllerAttributes.CSV_VOTES_URL));
        reader.setLineMapper(new DefaultLineMapper<Votes>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "stateName", "districtId", "year", "demoVotes", "repubVotes" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Votes>() {{
                setTargetType(Votes.class); 
            }});
        }});
        return reader;
    }

    @Bean
    public VotesItemProcessor votesProcessor() {
        return new VotesItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Votes> writerVotes() {
        JdbcBatchItemWriter<Votes> writer = new JdbcBatchItemWriter<Votes>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Votes>());
        writer.setSql("INSERT INTO votes (state_name, district_id, year, demo_votes, repub_votes)"
        		+ " VALUES (:stateName, :districtId, :year, :demoVotes, :repubVotes)");
        writer.setDataSource(dataSource);
        return writer;
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[] 
    @Bean
    public Job importUserJob(JobCompletionListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Votes, Votes> chunk(10)
                .reader(readerVotes())
                .processor(votesProcessor())
                .writer(writerVotes())
                .build();
    }
    
}