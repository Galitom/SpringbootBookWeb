package org.example.firstwebauth.Controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PersonaForm {
    @NotNull
    @Size(min=2, max=30)
    String name;

    @NotNull
    @Size(min=2, max=30)
    String surname;

    @NotNull
    @Size(min=2, max=30)
    String username;
    @NotNull
    @Size(min=6, max=20)
    String password;
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return this.surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "PersonaForm{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}