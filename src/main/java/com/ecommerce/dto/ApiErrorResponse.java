package com.ecommerce.dto;

import lombok.*;
import java.util.Map;

/**
 * Standard API error response body.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ApiErrorResponse {
    private int status;
    private String message;
    private Map<String, String> errors;
}
