package com.batch.Job;


import com.batch.Trending.TrendingMovie;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class OpenApiJob {

    private  JobBuilderFactory jobBuilderFactory;
    private  StepBuilderFactory stepBuilderFactory;

    // Job build 및 순서 정의
    @Bean
    public Job trendingMovieJob() {

        Job trendingMovieJob = jobBuilderFactory.get("trendingMovieJob")
                .start(openApiFristStep())    // 데이터를 한번만 받으면 되기 때문에 단일 스텝으로 구성
                .build();

        return trendingMovieJob;
    }

    @Bean
    @JobScope    // JobParameter를 보내므로 설정
    public Step openApiFristStep() {
        return stepBuilderFactory.get("openApiFristStep")
                .<Mono<TrendingMovie[]>, TrendingMovie[]>chunk(1) // Input, Output, chunk 사이즈
                .reader(openApiReader())
                .processor(dataEditProcessor())
                .writer(dataInsertWrite())
                .build();
    }

    // 데이터를 읽어오는 ItemReader 인터페이스의 커스텀 구현체
    @Bean
    @StepScope
    public OpenApiReader openApiReader() {
        return new OpenApiReader();
    }

    // 읽어온 데이터를 가공 후 반환하는 ItemProcessor 인터페이스의 커스텀 구현체
    @Bean
    @StepScope
    public OpenApiProcessor dataEditProcessor() {
        return new OpenApiProcessor();
    }

    // 가공 되어진 데이터들(Chunk)를 DB 혹은 특정 파일에 작성하는 ItemWriter 인터페이스의 커스텀 구현체
    @Bean
    @StepScope
    public OpenApiWriter dataInsertWrite() {
        return new OpenApiWriter();
    }
}