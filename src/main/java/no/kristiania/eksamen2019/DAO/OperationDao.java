package no.kristiania.eksamen2019.DAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OperationDao extends AbstractDao<Operation> {
    public OperationDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void insertMember(Operation operation, PreparedStatement statement) throws SQLException {
        statement.setString(1, operation.getName());

    }

    @Override
    protected Operation readObject(ResultSet resultSet) throws SQLException {
        Operation operation = new Operation();
        operation.setName(resultSet.getString(2));
        operation.setId(resultSet.getInt(1));
        return operation;
    }



    public long insert(Operation operation) throws SQLException{
        long id = insert(operation, "insert into operations (operation_name) values (?)");
        operation.setId((int)id);
        return id;
    }

    public List<Operation> listAll() throws SQLException {
        return listAll("select * from operations");
    }
    public Operation retrieve(long id) throws SQLException{
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from operations where id = ?")) {
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
