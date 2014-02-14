package fr.softwaresemantics.howmanydroid.db;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by christophe Goessen on 23/01/14.
 */
public class History implements Serializable {
    @JsonIgnore
    @DatabaseField(generatedId = true)
    int historyID;

    public Collection<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Collection<Assignment> assignments) {
        this.assignments = assignments;
    }

    @JsonProperty
    @ForeignCollectionField(eager = true)
    Collection<Assignment> assignments;

    public History(){}
    @JsonCreator
    public History(/*@JsonProperty("historyID") int _historyID,*/@JsonProperty("assignments") Collection<Assignment> _assignments)
    {
       /* historyID=_historyID;*/
        assignments=_assignments;
        for (Assignment ass:assignments)//:)
            ass.setHistory(this);
    }
}
