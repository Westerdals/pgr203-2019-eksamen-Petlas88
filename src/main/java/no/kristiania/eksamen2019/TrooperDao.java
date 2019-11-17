package no.kristiania.eksamen2019;

import jdk.dynalink.Operation;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class TrooperDao extends AbstractDao<Trooper> {

    public TrooperDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    protected void insertMember(Trooper member, PreparedStatement statement) throws SQLException {
        statement.setString(1, member.getName());
        statement.setString(2, member.getEmail());

    }

    @Override
    protected Trooper readObject(ResultSet resultSet) throws SQLException {
        Trooper member = new Trooper();

        member.setId(resultSet.getInt(1));
        member.setName(resultSet.getString(2));
        member.setEmail(resultSet.getString(3));
        return member;
    }


    public long insert(Trooper Trooper) throws SQLException {
        long id = insert(Trooper, "insert into Troopers (name,email) values (?,?)");
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
