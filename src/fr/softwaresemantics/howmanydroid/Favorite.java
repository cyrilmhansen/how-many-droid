package fr.softwaresemantics.howmanydroid;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by christophe Goessen on 23/01/14.
 */
public class Favorite implements Serializable {
@DatabaseField(generatedId = true)
    int favoriteID;
    @DatabaseField(foreign = true)
    Calculus calculus;

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
