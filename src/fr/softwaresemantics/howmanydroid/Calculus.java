package fr.softwaresemantics.howmanydroid;

import java.util.List;

/**
 * Created by Christophe Goessen on 30/12/13.
 */
public class Calculus {

    List<Expression> expressionList;
    String name;
    String description;

    public Calculus() {
    }


    public List<Expression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
