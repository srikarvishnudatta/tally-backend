package com.srikar.tally.dto.invite;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InviteRequestDto {
    @NotNull(message = "group id cannot be null")
    private Integer groupId;
    @NotBlank(message = "receiver email cannot be empty")
    private String receiverEmail;
}
