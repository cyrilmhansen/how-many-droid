package fr.softwaresemantics.howmanydroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by christophe Goessen on 28/12/13.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "howmanydroid_db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE locale (localeID INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR NOT NULL, description VARCHAR);");
        db.execSQL("CREATE TABLE i18n (i18nID INTEGER PRIMARY KEY AUTOINCREMENT, msgID INTEGER NOT NULL, msgText VARCHAR, localeID INTEGER NOT NULL, FOREIGN KEY (localeID) REFERENCES locale (localeID));");
        db.execSQL("CREATE TABLE expression ( " +
                "expressionID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "expression VARCHAR, " +
                "value_type VARCHAR); ");

        db.execSQL(" CREATE TABLE unit (" +
                "unitID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "value_type VARCHAR NOT NULL," +
                "unit_name VARCHAR NOT NULL," +
                "unit_symbol VARCHAR NULL," +
                "isReference BOOL NULL," +
                "expressionID INTEGER NOT NULL," +
                //syntax below not accepted for single col key
                // "PRIMARY KEY(unitID)," + //removed value type from key
                "FOREIGN KEY(expressionID)" +
                "REFERENCES expression(expressionID));");

        db.execSQL("CREATE TABLE calculus (" +
                "calculusID INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "idMsg INTEGER  NOT NULL," +
                "name VARCHAR NULL," +
                "description VARCHAR NULL," +
                //"PRIMARY KEY(calculusID)," +
                "FOREIGN KEY(idMsg)" +
                "REFERENCES i18n(idMsg));"); //changed from i18nID

        db.execSQL("CREATE TABLE category (" +
                "categoryID INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "idMsg INTEGER  NOT NULL," +
                "name VARCHAR NULL," +
                //"PRIMARY KEY(categoryID)," +
                "FOREIGN KEY(idMsg)" +
                "REFERENCES i18n(idMsg));");

        db.execSQL("CREATE TABLE parameter (" +
                "parameterID INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "idMsg INTEGER  NOT NULL," +
                "expressionID INTEGER UNSIGNED NOT NULL," +
                "name VARCHAR NULL," +
                "value_type VARCHAR NULL," +
                //"PRIMARY KEY(parametersID),"+
                "FOREIGN KEY(expressionID)" +
                "REFERENCES expression(expressionID)" +
                "FOREIGN KEY(idMsg)" +
                "REFERENCES i18n(idMsg));");

        db.execSQL("CREATE TABLE calculus_has_category ( " +
                "calculusID INTEGER  NOT NULL ," +
                "categoryID INTEGER  NOT NULL ," +
                "PRIMARY KEY(calculusID, categoryID)," +
                "FOREIGN KEY(calculusID)" +
                "REFERENCES calculus(calculusID)" +
                "FOREIGN KEY(categoryID)" +
                "REFERENCES category(categoryID));");

        db.execSQL("CREATE TABLE calculus_has_expression (" +
                "calculusID INTEGER  NOT NULL ," +
                "expressionID INTEGER  NOT NULL ," +
                "PRIMARY KEY(calculusID, expressionID)," +
                "FOREIGN KEY(expressionID)" +
                "REFERENCES expression(expressionID)" +
                "FOREIGN KEY(calculusID)" +
                "REFERENCES calculus(calculusID));");

        db.execSQL("CREATE TABLE history (" +
                "historyID INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "parametersID INTEGER  NOT NULL," +
                "value VARCHAR NULL," +
                // "PRIMARY KEY(historyID),"+
                "FOREIGN KEY(parametersID)" +
                "REFERENCES parameters(parametersID));");

        db.execSQL("CREATE TABLE favorite (" +
                "favoriteID INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "calculusID INTEGER UNSIGNED NOT NULL," +
                //"PRIMARY KEY(idFavorite),"+
                "FOREIGN KEY(calculusID)" +
                "REFERENCES calculus(calculusID));");

        ContentValues cv = new ContentValues();
        cv.put("name", "en_EN");
        cv.put("description", "english (UK)");
        db.insert("locale", null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS locale");
        db.execSQL("DROP TABLE IF EXISTS i18n");
        db.execSQL("DROP TABLE IF EXISTS expression");
        db.execSQL("DROP TABLE IF EXISTS calculus");
        db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS history");
        db.execSQL("DROP TABLE IF EXISTS favorite");
        db.execSQL("DROP TABLE IF EXISTS parameter");
        db.execSQL("DROP TABLE IF EXISTS unit");
        db.execSQL("DROP TABLE IF EXISTS calculus_has_expression");
        db.execSQL("DROP TABLE IF EXISTS calculus_has_category");
        onCreate(db);


    }
}
