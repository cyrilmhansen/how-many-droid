package fr.softwaresemantics.howmanydroid.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Christophe Goessen on 30/12/13.
 */


public class Parameter implements Serializable {
    @JsonIgnore
    @DatabaseField(generatedId = true)
    private int parameterID;


    public Expression getExpression() {
        return expression;
    }

    @JsonProperty
    @DatabaseField(foreign = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    Unit unit;

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    //avoid infinite recursion
    @JsonIgnore
    @DatabaseField(foreign = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    Expression expression;

    public String getName() {
        return name;
    }

    public Parameter() {
    }

    public Parameter(String name, String type) {
        this.name = name;
        this.value_type = type;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.value_type = type;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {

        return value_type;
    }

    public Object getValue() {
        return value;
    }

    @JsonProperty
    @DatabaseField
    private String name;
    @JsonProperty
    @DatabaseField
    private String value_type;

    //private Expression expr;

    private Object value;
}
