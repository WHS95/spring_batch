package com.batch.Trending;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public  class TrendingObject {

    public TrendingMovie[] getResults() {
        return results;
    }

    private TrendingMovie[] results;



    @Builder
    public TrendingObject(TrendingMovie[] results) {
        this.results = results;
    }
}
