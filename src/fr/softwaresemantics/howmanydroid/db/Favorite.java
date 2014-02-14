package fr.softwaresemantics.howmanydroid.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by christophe Goessen on 23/01/14.
 */
public class Favorite implements Serializable {
    @JsonIgnore
    @DatabaseField(generatedId = true)
    int favoriteID;
    @JsonProperty
    @DatabaseField(foreign = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    Calculus calculus;

    public Favorite() {
    }

    public int getFavoriteID() {
        return favoriteID;
    }

    public void setFavoriteID(int favoriteID) {
        this.favoriteID = favoriteID;
    }

    public Calculus getCalculus() {
        return calculus;
    }

    public void setCalculus(Calculus calculus) {
        this.calculus = calculus;
    }
}
