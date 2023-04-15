package com.batch.Job;


import com.batch.Trending.TrendingMovie;
import com.batch.Trending.TrendingObject;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class OpenApiReader implements ItemReader<Mono<TrendingMovie[]>> {

    @Value("${movie.openApi.trending.uri}")
    private String TRENDING_MOVIE_URL;

    @Value("${movie.openApi.apiKey}")
    private String API_KEY;

    @Autowired
    private WebClient.Builder wcBuilder;

    private int cnt = 0;

    public OpenApiReader() {
    }

    @Override
    public Mono<TrendingMovie[]> read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        cnt++;
        return cnt == 1
        ? wcBuilder.build().get()
                .uri(TRENDING_MOVIE_URL+"/trending/movie/day?api_key={API_KEY}",API_KEY)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TrendingObject.class)
                .map(trendingObject -> trendingObject.getResults())
                : null;
    }
}