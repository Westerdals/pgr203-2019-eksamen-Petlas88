package no.kristiania.eksamen2019.DAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ObjectiveStatusDao extends AbstractDao<ObjectiveStatus> {
    public ObjectiveStatusDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void insertMember(ObjectiveStatus status, PreparedStatement statement) throws SQLException {

        statement.setString(1, status.getName());


    }

    @Override
    protected ObjectiveStatus readObject(ResultSet resultSet) throws SQLException {
        ObjectiveStatus status = new ObjectiveStatus();
        status.setName(resultSet.getString(2));
        status.setId(resultSet.getInt(1));
        return status;
    }


    public List<ObjectiveStatus> listAll() throws SQLException {
        return listAll("select * from status");
    }


    public long insert(ObjectiveStatus status) throws SQLException {
        long id = insert(status, "insert into status (name) values (?)");
        status.setId((int) id);
        return id;
    }

    public ObjectiveStatus retrieve(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from Status where id = ?")) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return (readObject(resultSet));
                    } else {
                        return null;
                    }
                }
            }
        }
    }
}