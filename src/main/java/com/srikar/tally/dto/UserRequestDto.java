package com.srikar.tally.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "First name is required")
    @Size(max=100, message="First name cannot exceed 100 characters")
    private String firstName;
    @NotBlank(message = "First name is required")
    @Size(max=100, message="First name cannot exceed 100 characters")
    private String lastName;
}
