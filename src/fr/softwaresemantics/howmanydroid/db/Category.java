package fr.softwaresemantics.howmanydroid.db;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by christophe goessen on 01/02/14.
 */
public class Category {

    @JsonIgnore
    @DatabaseField(generatedId = true)
    int categoryID;
    @JsonProperty("calculi")
    @ForeignCollectionField(eager = false)
    Collection<Calculus> Calculi;

    @JsonProperty("children")
    @ForeignCollectionField(eager = true,  columnName =  "parent_id")
    Collection<Category> children;

    @JsonIgnore
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName =  "parent_id")
    Category parent;

    @JsonProperty
    @DatabaseField(foreign = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    I18n name;

    @JsonProperty
    @DatabaseField(foreign = true, canBeNull = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    I18n description;

    public Category(){}

    @JsonCreator
    public Category(@JsonProperty("categoryID") int _categoryID,@JsonProperty("calculi") Collection<Calculus> _Calculi, @JsonProperty("children") Collection<Category> _children, @JsonProperty("name") I18n _name,@JsonProperty("description") I18n _description)
    {
        categoryID=_categoryID;
        Calculi=_Calculi;
        children=_children;
        if (_children ==null) {
            _children = Collections.EMPTY_LIST;
        }
        // collections must be not null
        name=_name;
        description=_description;
        for(Calculus cal:Calculi)
            cal.setCategory(this);

        for(Category child:children) {
            Log.d("Category", "parent association for " + child.getName());
            child.setParent(this);
        }
    }
    public Collection<Calculus> getCalculi() {
        return Calculi;
    }

    public Collection<Category> getChildren() {
        return children;
    }

    public void setCalculi(Collection<Calculus> calculi) {
        Calculi = calculi;
    }

    public I18n getName() {
        return name;
    }

    public void setName(I18n name) {
        this.name = name;
    }

    public I18n getDescription() {
        return description;
    }

    public void setDescription(I18n description) {
        this.description = description;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
}
