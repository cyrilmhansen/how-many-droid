package fr.softwaresemantics.howmanydroid;

import java.util.List;

/**
 * Created by Christophe Goessen on 30/12/13.
 */
public class Expression {
    List<Parameter> parameterList;
    String expression;
    String type;

    public Expression(String expression, String type) {
        this.expression = expression;
        this.type = type;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public List<Parameter> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<Parameter> parameterList) {
        this.parameterList = parameterList;
    }
}
