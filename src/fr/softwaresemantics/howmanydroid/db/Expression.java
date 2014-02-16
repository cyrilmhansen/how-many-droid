package fr.softwaresemantics.howmanydroid.db;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Christophe Goessen on 30/12/13.
 */

//@DatabaseTable(tableName = "expression")
public class Expression implements Serializable {
    @JsonIgnore
    @DatabaseField(generatedId = true)
    private int expressionID;
    @JsonProperty
    @ForeignCollectionField(eager = false)
    Collection<Parameter> parameterList;

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @JsonProperty
    @DatabaseField(foreign = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    Unit unit;

    public Calculus getCalculus() {
        return calculus;
    }

    public void setCalculus(Calculus calculus) {
        this.calculus = calculus;
    }

    @JsonIgnore
    @DatabaseField(foreign = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    Calculus calculus;
    @JsonProperty
    @DatabaseField
    String expression;
    @JsonProperty
    @DatabaseField
    String value_type;

    @JsonCreator
    public Expression(/*@JsonProperty("expressionID") int _expressionID,*/@JsonProperty("parameterList") Collection<Parameter> _parameterList,@JsonProperty("unit") Unit _unit, @JsonProperty("expression") String _expression,@JsonProperty("value_type") String _value_type)
    {
       /* expressionID=_expressionID;*/

        expression=_expression;
        unit=_unit;
        value_type=_value_type;
        this.parameterList=(_parameterList==null)?Collections.EMPTY_LIST:_parameterList;
        for (Parameter param: this.parameterList)
            param.setExpression(this);
    }

    public Expression(String expression, String type) {
        this.expression = expression;
        this.value_type = type;
    }

    public Expression() {
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getType() {
        return value_type;
    }

    public void setType(String type) {
        this.value_type = type;
    }


    public Collection<Parameter> getParameterList() {
        return parameterList;
    }

    public void setParameterList(Collection<Parameter> parameterList) {
        this.parameterList=(parameterList==null)?Collections.EMPTY_LIST:parameterList;
        for (Parameter param: this.parameterList)
            param.setExpression(this);
    }
}
