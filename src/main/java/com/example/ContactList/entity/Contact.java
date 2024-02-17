package com.example.ContactList.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @NotNull
    @JsonProperty("contactName")
    String contactName;
    @JsonProperty("contactEmail")
    String contactEmail;
    @JsonProperty("phoneOne")
    String phoneOne;
    @JsonProperty("phoneTwo")
    String phoneTwo;

}
