package com.nextitproject.libraryapi.backend.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;


public class Borrowed {
    private String firstName;
    private String lastName;
    private String from;

    @JsonCreator
    public Borrowed(@JsonProperty("firstName") String firstName,
                    @JsonProperty("lastName") String lastName,
                    @JsonProperty("from") String from) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.from = from;
    }

    public Borrowed() {

    }

    // Getters and setters

    @JsonProperty("FirstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("FirstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("LastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("LastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("From")
    public String getFrom() {
        return from;
    }

    @JsonProperty("From")
    public void setFrom(String from) {
        this.from = from;
    }
}
