package no.kristiania.eksamen2019;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ObjectiveTrooperDao extends AbstractDao<ObjectiveTrooper> {
    public ObjectiveTrooperDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    protected void insertMember(ObjectiveTrooper objectiveTrooper, PreparedStatement statement) throws SQLException {
        statement.setString(1, objectiveTrooper.getName());
        statement.setString(2, objectiveTrooper.getEmail());

    }

    @Override
    protected ObjectiveTrooper readObject(ResultSet resultSet) throws SQLException {
        ObjectiveTrooper objectiveTrooper = new ObjectiveTrooper();

        objectiveTrooper.setId(resultSet.getInt(1));
        objectiveTrooper.setName(resultSet.getString(2));
        objectiveTrooper.setEmail(resultSet.getString(3));
        return objectiveTrooper;
    }


    public long insert(ObjectiveTrooper ObjectiveTrooper) throws SQLException {
        long id = insert(ObjectiveTrooper, "insert into ObjectiveTroopers (name,email) values (?,?)");
        ObjectiveTrooper.setId(id);
        return id;
    }

    public List<ObjectiveTrooper> listAll() throws SQLException {
        return listAll("select * from ObjectiveTroopers");
    }

    public ObjectiveTrooper retrieve(long id) throws SQLException{
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from ObjectiveTroopers where id = ?")) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        return (readObject(resultSet));
                    } else {
                        return null;
                    }
                }
            }
        }
    }



}
