package fr.softwaresemantics.howmanydroid.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by christophe Goessen on 23/01/14.
 */
public class Assignment implements Serializable {
    @JsonIgnore
    @DatabaseField(generatedId = true)
    int assignmentID;
    @JsonIgnore
    @DatabaseField(foreign = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    History history;

    public Assignment() {
    }

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

    @JsonProperty
    @DatabaseField(foreign = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    Parameter parameter;
    @JsonProperty
    @DatabaseField(canBeNull = false)
    String value;
}
