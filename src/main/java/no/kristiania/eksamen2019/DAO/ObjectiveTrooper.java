package no.kristiania.eksamen2019.DAO;

import java.util.Objects;

public class ObjectiveTrooper {

    private int trooperId;
    private int objectiveId;

    public int getTrooperId() {
        return trooperId;
    }
    public void setTrooperId(int trooperId) {
        this.trooperId = trooperId;
    }

    public int getObjectiveId() {
        return objectiveId;
    }
    public void setObjectiveId(int objectiveId) {
        this.objectiveId = objectiveId;
    }


    public ObjectiveTrooper() {
    }

    public ObjectiveTrooper(int objectId, int trooperId) {
        this.objectiveId = objectId;
        this.trooperId = trooperId;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectiveTrooper that = (ObjectiveTrooper) o;
        return trooperId == that.trooperId &&
                objectiveId == that.objectiveId;
    }
    @Override
    public int hashCode() {
        return Objects.hash(trooperId, objectiveId);
    }

    @Override
    public String toString() {
        return "ObjectiveTrooper{" +
                "trooperId=" + trooperId +
                ", objectiveId='" + objectiveId + '\'' +
                '}';
    }
}
