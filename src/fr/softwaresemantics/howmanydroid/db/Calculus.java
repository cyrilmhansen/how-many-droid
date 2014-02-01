package fr.softwaresemantics.howmanydroid.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

/**
 * Created by Christophe Goessen on 30/12/13.
 */
public class Calculus implements Serializable {

    @DatabaseField(generatedId = true)
    int calculusID;
    @ForeignCollectionField(eager = false)
    Collection<Expression> expressionList;
    @DatabaseField(foreign = true)
    I18n name;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @DatabaseField(foreign = true)
    Category category;

    public I18n getDescription() {
        return description;
    }

    public void setDescription(I18n description) {
        this.description = description;
    }

    public I18n getName() {
        return name;
    }

    public void setName(I18n name) {
        this.name = name;
    }

    @DatabaseField(foreign = true)
    I18n description;


    public Calculus() {
    }


    public Collection<Expression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }


}
