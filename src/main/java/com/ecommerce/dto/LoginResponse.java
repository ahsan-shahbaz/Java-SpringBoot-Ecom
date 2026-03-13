package com.ecommerce.dto;

import lombok.*;

/**
 * Matches Angular User interface: { id, email, firstName, lastName, token }
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LoginResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String token;
}
