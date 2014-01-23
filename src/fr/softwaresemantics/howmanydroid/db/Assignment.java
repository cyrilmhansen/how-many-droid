package fr.softwaresemantics.howmanydroid.db;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by christophe Goessen on 23/01/14.
 */
public class Assignment implements Serializable {
    @DatabaseField(generatedId = true)
    int assignmentID;
    @DatabaseField(foreign = true)
    History history;
    public Parameter getParameter() {
        return parameter;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @DatabaseField(foreign = true)
    Parameter parameter;
    @DatabaseField(canBeNull = false)
    String value;
}
