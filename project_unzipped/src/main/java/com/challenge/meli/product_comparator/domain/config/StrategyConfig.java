package com.challenge.meli.product_comparator.domain.config;

import com.challenge.meli.product_comparator.domain.policy.ComparisonStrategy;
import com.challenge.meli.product_comparator.domain.policy.impl.BestPriceStrategy;
import com.challenge.meli.product_comparator.domain.policy.impl.BestRatingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrategyConfig {

    @Bean(name = "PRICE")
    public ComparisonStrategy priceStrategy() {
        return new BestPriceStrategy();
    }

    @Bean(name = "RATING")
    public ComparisonStrategy ratingStrategy() {
        return new BestRatingStrategy();
    }
}