package no.kristiania.eksamen2019.DAO;

import java.util.Objects;

public class ObjectiveTrooper {

    private long id;
    private String name;
    private String email;

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


    public ObjectiveTrooper() {
    }

    public ObjectiveTrooper(String name, String email) {
        this.name = name;
        this.email = email;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectiveTrooper trooper = (ObjectiveTrooper) o;
        return id == trooper.id &&
                Objects.equals(name, trooper.name) &&
                Objects.equals(email, trooper.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    @Override
    public String toString() {
        return "ObjectiveTrooper{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
