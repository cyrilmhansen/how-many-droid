package fr.softwaresemantics.howmanydroid.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Predicate;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by christophe Goessen on 22/01/14.
 */
public class Translation implements Serializable {
    @JsonProperty("TranslationID")
    @DatabaseField(generatedId = true, canBeNull = false)
    int TranslationID;

    public Locale getLocale() {
        return locale;
    }

    public Translation() {
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public I18n getI18n() {
        return i18n;
    }

    public void setI18n(I18n i18n) {
        this.i18n = i18n;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("locale")
    @DatabaseField(foreign = true, uniqueCombo = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    Locale locale;
    //not mapped in JSON to avoid loops
    @JsonIgnore
    @DatabaseField(foreign = true, uniqueCombo = true,foreignAutoCreate = true,foreignAutoRefresh = true)
    I18n i18n;
    @JsonProperty("value")
    @DatabaseField(canBeNull = true)
    String value;

    public static class FindByLocalePredicate implements Predicate<Translation> {
        private Locale target;

        public FindByLocalePredicate(Locale loc) {
            target = loc;
        }

        @Override
        public boolean apply(Translation tr) {
            return tr.locale.equals(target);
        }
    }
}
