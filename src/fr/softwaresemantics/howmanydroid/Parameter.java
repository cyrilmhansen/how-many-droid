package fr.softwaresemantics.howmanydroid;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Christophe Goessen on 30/12/13.
 */


public class Parameter implements Serializable {
    @DatabaseField(generatedId = true)
    private int parameterID;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @DatabaseField(foreign = true)
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

    @DatabaseField
    private String name;
    @DatabaseField
    private String value_type;
    @DatabaseField(foreign = true, columnName = "expressionID", canBeNull = false, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Expression expr;

    private Object value;
}
