package com.srikar.tally.dto.group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupRequestDto {
    @NotBlank(message = "Group Name cannot be empty")
    @Size(max=100, message = "Group Name cannot exceed 100 characters")
    private String groupName;
    @NotBlank(message = "Group Description cannot be empty")
    @Size(max = 200, message = "Group Description cannot exceed 200 characters")
    private String groupDescription;
}
