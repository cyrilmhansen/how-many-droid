/**
 * Created by christophe Goessen on 30/12/13.
 */
package fr.softwaresemantics.howmanydroid;

import android.database.sqlite.SQLiteDatabase;

public class SQLiteDAO {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private String lang;
    private int langID;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public SQLiteDAO() {
    }

    public void close() {

        db.close();
    }
}
