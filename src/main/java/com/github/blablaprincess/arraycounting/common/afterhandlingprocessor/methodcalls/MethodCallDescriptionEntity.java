package com.github.blablaprincess.arraycounting.common.afterhandlingprocessor.methodcalls;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodCallDescriptionEntity {

    private String method;

    private String args;

    private boolean successful;

    private String response;

    private LocalDateTime timestamp;

}
