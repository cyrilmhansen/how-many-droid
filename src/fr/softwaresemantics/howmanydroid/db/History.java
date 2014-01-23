package fr.softwaresemantics.howmanydroid.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by christophe Goessen on 23/01/14.
 */
public class History implements Serializable {
    @DatabaseField(generatedId = true)
    int historyID;

    public Collection<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Collection<Assignment> assignments) {
        this.assignments = assignments;
    }

    @ForeignCollectionField(eager = true)

    Collection<Assignment> assignments;
}
