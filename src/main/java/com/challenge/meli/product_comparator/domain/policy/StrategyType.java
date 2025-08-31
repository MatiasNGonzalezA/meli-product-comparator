package com.challenge.meli.product_comparator.domain.policy;

public enum StrategyType {
    PRICE, RATING;

    public static StrategyType from(String raw) {
        if (raw == null) return PRICE;
        return StrategyType.valueOf(raw.trim().toUpperCase());
    }
}
