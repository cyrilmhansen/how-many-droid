package fr.softwaresemantics.howmanydroid.db;

/**
 * Created by christophe goessen on 19/01/14.
 */


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Iterables;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.io.Serializable;
import java.util.Collection;

public class I18n implements Serializable {
    @JsonProperty("msgID")
    @DatabaseField(id = true)
    String msgID;
    @JsonProperty("translations")
    @ForeignCollectionField(eager = true)
    Collection<Translation> translations;

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getValue(Locale loc) {
        return Iterables.find(translations, new Translation.FindByLocalePredicate(loc)).getValue();
    }

    public Collection<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(Collection<Translation> translations) {
        this.translations = translations;
    }

    public I18n() {
    }


}
