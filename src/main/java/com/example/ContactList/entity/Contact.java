package com.example.ContactList.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @JsonProperty("contactId")
    int contactId;
    @NotNull
    @NotEmpty
    @JsonProperty("contactName")
    String contactName;
    @JsonProperty("contactEmail")
    String contactEmail;
    @JsonProperty("phoneOne")
    String phoneOne;
    @JsonProperty("phoneTwo")
    String phoneTwo;

}
