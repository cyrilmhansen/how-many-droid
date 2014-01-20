package fr.softwaresemantics.howmanydroid;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by christophe goessen on 19/01/14.
 */
public class Locale implements Serializable {
    @DatabaseField(generatedId = true)
    private int localeId;
    @DatabaseField(canBeNull = false,index = true)
    String name;
    @DatabaseField(canBeNull = false)
    String description;

    public Locale() {
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
