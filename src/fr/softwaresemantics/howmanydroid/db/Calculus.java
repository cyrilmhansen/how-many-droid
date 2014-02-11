package fr.softwaresemantics.howmanydroid.db;

import com.fasterxml.jackson.annotation.JsonCreator;
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
    @DatabaseField(foreign = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    I18n name;
    @JsonCreator
    public Calculus(@JsonProperty("calculusID") int _calculusID, @JsonProperty("expressionList") Collection<Expression> _expressionList,@JsonProperty("name") I18n _name,@JsonProperty("description") I18n _description)
    {
        calculusID=_calculusID;
        expressionList=_expressionList;
        name=_name;
        description=_description;
        //restore link to parent missing in json
        for(Expression expr:expressionList)
            expr.setCalculus(this);
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonIgnore
    @DatabaseField(foreign = true,foreignAutoCreate = true,foreignAutoRefresh = true)
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
    @DatabaseField(foreign = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    I18n description;


    public Calculus() {
    }


    public Collection<Expression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(Collection<Expression> expressionList) {
        this.expressionList = expressionList;
    }


}
