package no.kristiania.eksamen2019;

import java.util.Objects;

public class Trooper {


    private long id;
    private String name;
    private String email;
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }



    public Trooper() {


    }

    public Trooper(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trooper trooper = (Trooper) o;
        return id == trooper.id &&
                Objects.equals(name, trooper.name) &&
                Objects.equals(email, trooper.email) &&
                Objects.equals(role, trooper.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, role);
    }

    @Override
    public String toString() {
        return "Trooper{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
