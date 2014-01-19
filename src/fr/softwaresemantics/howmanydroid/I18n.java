package fr.softwaresemantics.howmanydroid;

/**
 * Created by christophe goessen on 19/01/14.
 */

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class I18n implements Serializable {
    @DatabaseField(generatedId = true, canBeNull = false)
    private int i18nID;
    @DatabaseField(index = true, canBeNull = false)
    String msgID;
    @DatabaseField(foreign = true)
    Locale locale;

    public I18n() {
    }
}
