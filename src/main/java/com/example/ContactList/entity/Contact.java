package com.example.ContactList.entity;

import com.example.ContactList.validator.EnumContactTypesPattern;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @JsonProperty("contact")
    String contact;
    //@EnumContactTypesPattern(enumClass = EnumContactTypes.class, message = "Invalid contact type")
    @JsonProperty("type")
    EnumContactTypes type;
    @JsonProperty("contactName")
    String contactName;

}
