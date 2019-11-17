package no.kristiania.eksamen2019.DAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ObjectiveTrooperDao extends AbstractDao<ObjectiveTrooper> {
    public ObjectiveTrooperDao(DataSource dataSource) {
        super( dataSource );
    }

    @Override
    protected void insertMember(ObjectiveTrooper objectiveTrooper, PreparedStatement statement) throws SQLException {
        statement.setInt( 1, objectiveTrooper.getTrooperId() );
        statement.setInt( 2, objectiveTrooper.getObjectiveId() );
    }

    @Override
    protected ObjectiveTrooper readObject(ResultSet resultSet) throws SQLException {
        ObjectiveTrooper objectiveTrooper = new ObjectiveTrooper();
        objectiveTrooper.setObjectiveId(resultSet.getInt( 2 ) );
        objectiveTrooper.setTrooperId( resultSet.getInt( 1 ) );
        return objectiveTrooper;
    }

    public List<ObjectiveTrooper> listAll() throws SQLException {
        return listAll( "select * from objectivetroopers" );
    }

    public void insert(ObjectiveTrooper objectiveTrooper) throws SQLException {
        //checks if object already exist in database before inserting
        if (this.listAll().contains( objectiveTrooper )) {
            System.out.println( "FINNES ALEREDE!" );
            return;
        }
        insert( objectiveTrooper, "insert into objectivetroopers (member_id,task_id) values (?,?)" );
    }
}
