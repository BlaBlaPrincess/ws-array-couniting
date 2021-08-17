package com.github.blablaprincess.arraycounting.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArrayCountingAlgorithmsPresenterDto {
    private Map<String, Double> counts;
}
