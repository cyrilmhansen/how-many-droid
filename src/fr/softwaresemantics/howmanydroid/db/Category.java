package fr.softwaresemantics.howmanydroid.db;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public Category getParent() {
        return parent;
    }

    @JsonIgnore
    @DatabaseField(generatedId = true)
    int categoryID;

    @JsonProperty("calculi")
    @ForeignCollectionField(eager = false)
    Collection<Calculus> Calculi;

    public void setChildren(Collection<Category> children) {
        this.children=(children==null)?Collections.EMPTY_LIST:children;
        for(Category child:this.children)
            child.setParent(this);
    }

    @JsonProperty("children")
    @ForeignCollectionField(eager = true,  columnName =  "parent_id")
    Collection<Category> children;

    @JsonIgnore
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName =  "parent_id",canBeNull = true)
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
        name=_name;
        description=_description;
        this.Calculi=(_Calculi==null)?Collections.EMPTY_LIST:_Calculi;
        for(Calculus cal:Calculi)
            cal.setCategory(this);
        this.children=(_children==null)?Collections.EMPTY_LIST:_children;
        for(Category child:children)
            child.setParent(this);

    }
    public Collection<Calculus> getCalculi() {
        return Calculi;
    }

    public Collection<Category> getChildren() {
        return children;
    }

    public void setCalculi(Collection<Calculus> calculi) {
        this.Calculi=(Calculi==null)?Collections.EMPTY_LIST:Calculi;
        for(Calculus cal:this.Calculi)
            cal.setCategory(this);
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
