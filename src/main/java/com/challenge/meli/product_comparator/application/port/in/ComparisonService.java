package com.challenge.meli.product_comparator.application.port.in;

import com.challenge.meli.product_comparator.api.dto.CompareResponse;
import com.challenge.meli.product_comparator.domain.policy.StrategyType;

import java.util.List;

public interface ComparisonService {
    CompareResponse compare(List<String> ids);
    CompareResponse compare(List<String> ids, StrategyType strategy);
}
