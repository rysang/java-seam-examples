package org.price.test.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.price.test.conversion.annotations.Store;

public class Contact {

    public static final String NAME = "Contact";

    private String             id;

    @NotEmpty
    @Length(min = 2, max = 50)
    @Store
    private String             firstname;

    @NotEmpty
    @Store
    private String             lastname;

    @NotEmpty
    @Store
    private String             email;

    @NotEmpty
    @Store
    private String             telephone;

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}