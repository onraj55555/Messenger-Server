package org.messenger.User;

import java.time.LocalDate;
import java.util.Objects;


public class UnsafeUser {
    // Primary key
    private String username;
    private String password;
    private String email;
    private LocalDate birthdate;
    private LocalDate creationDate;
    private String sex;

    public UnsafeUser() {
        username = "";
        password = "";
        email = "";
        birthdate = LocalDate.now();
        creationDate = LocalDate.now();
        sex = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UnsafeUser user = (UnsafeUser) object;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
