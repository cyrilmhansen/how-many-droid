package fr.softwaresemantics.howmanydroid.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.Collection;

/**
 * Created by christophe goessen on 01/02/14.
 */
public class Category {

    @DatabaseField(generatedId = true)
    int categoryID;
    @ForeignCollectionField(eager=false)
    Collection<Calculus> Calculi;

    @DatabaseField(foreign = true)
    I18n name;

    @DatabaseField(foreign = true,canBeNull = true)
    I18n description;

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
