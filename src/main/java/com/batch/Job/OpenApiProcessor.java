package com.batch.Job;


import com.batch.Trending.TrendingMovie;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

// exception이 발생하였을 때 Roll Back
// 적용된 범위에서는 트랜잭션 기능이 포함된 프록시 객체가 생성되어 자동으로 commit 혹은 rollback을 진행해준다.
@Transactional(rollbackFor = Exception.class)
public class OpenApiProcessor implements ItemProcessor<Mono<TrendingMovie[]>, TrendingMovie[]> {

    @Override
    public TrendingMovie[] process(Mono<TrendingMovie[]> item) throws Exception {
        return item.block();
    }

}