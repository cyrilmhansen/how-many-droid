package fr.softwaresemantics.howmanydroid.db;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Christophe Goessen on 30/12/13.
 */

//@DatabaseTable(tableName = "expression")
public class Expression implements Serializable {

    @DatabaseField(generatedId = true)
    private int expressionID;
    @ForeignCollectionField(eager = false)
    Collection<Parameter> parameterList;

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @DatabaseField(foreign = true)
    Unit unit;
    public Calculus getCalculus() {
        return calculus;
    }

    public void setCalculus(Calculus calculus) {
        this.calculus = calculus;
    }

    @DatabaseField(foreign = true)
    Calculus calculus;
    @DatabaseField
    String expression;
    @DatabaseField
    String value_type;

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

    public void setParameterList(ForeignCollection<Parameter> parameterList) {
        this.parameterList = parameterList;
    }
}