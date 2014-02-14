package fr.softwaresemantics.howmanydroid.db;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.Collection;

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
    @JsonProperty
    @DatabaseField(foreign = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    I18n name;
    @JsonProperty
    @DatabaseField(foreign = true, canBeNull = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    I18n description;

    public Category(){}

    @JsonCreator
    public Category(@JsonProperty("categoryID") int _categoryID,@JsonProperty("calculi") Collection<Calculus> _Calculi,@JsonProperty("name") I18n _name,@JsonProperty("description") I18n _description)
    {
        categoryID=_categoryID;
        Calculi=_Calculi;
        name=_name;
        description=_description;
        for(Calculus cal:Calculi)
            cal.setCategory(this);
    }
    public Collection<Calculus> getCalculi() {
        return Calculi;
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
}
