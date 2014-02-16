package fr.softwaresemantics.howmanydroid.db;

/**
 * Created by christophe goessen on 19/01/14.
 */


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Iterables;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class I18n implements Serializable {
    @JsonProperty("msgID")
    @DatabaseField(id = true)
    String msgID;
    @JsonProperty("translations")
    @ForeignCollectionField(eager = true)
    Collection<Translation> translations;


    /*
        Json creator to work around loop/orphan dilemma due to ORMlite/Jackson
     */
    /*
    // delegate doesn't seam to work well with nested
    @JsonCreator
    public I18n(Map<String,Object> delegate)
    {
        msgID = (String) delegate.get("msgID");
        translations = (Collection<Translation>) delegate.get("translations");
        // link orphaned child
        for (Translation tr: translations)
            tr.setI18n(this);
    }*/
    @JsonCreator
    public I18n(@JsonProperty("msgID") String _msgID,@JsonProperty("translations") Collection<Translation> _translations)
    {
        msgID = _msgID;
        this.translations = (_translations==null)?Collections.EMPTY_LIST:_translations;
        for (Translation tr: this.translations)
            tr.setI18n(this);
    }

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

        this.translations = (translations==null)?Collections.EMPTY_LIST:translations;
        for (Translation tr: this.translations)
            tr.setI18n(this);
    }

    public I18n() {
    }


}
