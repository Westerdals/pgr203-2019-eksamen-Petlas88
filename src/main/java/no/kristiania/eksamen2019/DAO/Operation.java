package no.kristiania.eksamen2019.DAO;

import java.util.Objects;

public class Operation {

    private String name;
    private String description;
    private long id;

    public Operation (){}

    public Operation(String name, String description, Integer id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return id == operation.id &&
                Objects.equals(name, operation.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "Operation Name='" + name + '\'' +
                "Operation Description='" + description + '\'' +
                "Operation ID=" + id +
                '}';
    }
}
