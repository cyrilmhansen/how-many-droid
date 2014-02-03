package fr.softwaresemantics.howmanydroid.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by Christophe Goessen on 30/12/13.
 */
public class Calculus implements Serializable {
    @JsonProperty
    @DatabaseField(generatedId = true)
    int calculusID;
    @JsonProperty
    @ForeignCollectionField(eager = false)
    Collection<Expression> expressionList;
    @JsonProperty
    @DatabaseField(foreign = true)
    I18n name;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonIgnore
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

    @JsonProperty
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
