package no.kristiania.eksamen2019;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ObjectiveDao extends AbstractDao<Objective> {
    public ObjectiveDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void insertMember(Objective operationObjective, PreparedStatement statement) throws SQLException {
        statement.setString(1, operationObjective.getName());
        statement.setString(2, operationObjective.getDescription());
        statement.setString(3, operationObjective.getStatus());

    }

    @Override
    protected Objective readObject(ResultSet resultSet) throws SQLException {
        Objective operationObjective = new Objective();

        operationObjective.setId(resultSet.getInt(1));
        operationObjective.setName(resultSet.getString(2));
        operationObjective.setDescription(resultSet.getString(3));
        operationObjective.setStatus(resultSet.getString(4));
        return operationObjective;
    }



    public long insert(Objective operationObjective) throws SQLException{
        long id = insert(operationObjective, "insert into objectives (name,description,status) values (?,?,?)");
        operationObjective.setId((int)id);
        return id;
    }

    public List<Objective> listAll() throws SQLException {
        return listAll("select * from objectives");
    }
    public Objective retrieve(long id) throws SQLException{
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from objectives where id = ?")) {
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