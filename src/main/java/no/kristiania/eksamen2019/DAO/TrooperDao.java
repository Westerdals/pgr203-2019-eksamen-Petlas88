package no.kristiania.eksamen2019.DAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TrooperDao extends AbstractDao<Trooper> {

    public TrooperDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    protected void insertMember(Trooper member, PreparedStatement statement) throws SQLException {
        statement.setString(1, member.getName());
        statement.setString(2, member.getEmail());
        statement.setString(3, member.getRole());
    }

    @Override
    protected Trooper readObject(ResultSet resultSet) throws SQLException {
        Trooper object = new Trooper();

        object.setId(resultSet.getInt(1));
        object.setName(resultSet.getString(2));
        object.setEmail(resultSet.getString(3));
        object.setRole(resultSet.getString(4));
        return object;
    }


    public long insert(Trooper Trooper) throws SQLException {
        long id = insert(Trooper, "insert into Troopers (name,email,role) values (?,?,?)");
        Trooper.setId(id);
        return id;
    }

    public List<Trooper> listAll() throws SQLException {
        return listAll("select * from Troopers");
    }

    public Trooper retrieve(long id) throws SQLException{
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from Troopers where id = ?")) {
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
