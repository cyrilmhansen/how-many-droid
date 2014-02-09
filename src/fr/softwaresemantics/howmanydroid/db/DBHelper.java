package fr.softwaresemantics.howmanydroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import fr.softwaresemantics.howmanydroid.R;

/**
 * Created by christophe Goessen on 28/12/13.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "howmanydroid.db";
    private static final int DATABASE_VERSION = 1;

    private RuntimeExceptionDao<Locale, Integer> localeRuntimeDao = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    private Dao<Locale, String> localeDao = null;
    private Dao<I18n, String> i18nDao = null;
    private Dao<Expression, Integer> expressionDao = null;
    private Dao<Calculus, Integer> calculusDao = null;
    private Dao<Translation, Integer> translationDao = null;
    private Dao<Unit, Integer> unitDao = null;
    private Dao<Favorite, Integer> favoriteDao = null;
    private Dao<Assignment, Integer> assignmentDao = null;
    private Dao<History, Integer> historyDao = null;
    private Dao<Category, Integer> categoryDao = null;

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {

        /*
        * Migration to ORMLite in progress, legacy SQLQuery code will be removed
        */

        try {
            Log.i(DBHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Parameter.class);
            TableUtils.createTable(connectionSource, Expression.class);
            TableUtils.createTable(connectionSource, Locale.class);
            TableUtils.createTable(connectionSource, I18n.class);
            TableUtils.createTable(connectionSource, Calculus.class);
            TableUtils.createTable(connectionSource, Translation.class);
            TableUtils.createTable(connectionSource, Unit.class);
            TableUtils.createTable(connectionSource, Assignment.class);
            TableUtils.createTable(connectionSource, History.class);
            TableUtils.createTable(connectionSource, Category.class);
        } catch (SQLException e) {
            Log.e(DBHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }


    }

    public Dao<Locale, String> getLocaleDao() throws SQLException {
        if (localeDao == null) {
            localeDao = getDao(Locale.class);
        }
        return localeDao;
    }

    public Dao<I18n, String> getI18nDao() throws SQLException {
        if (i18nDao == null) {
            i18nDao = getDao(I18n.class);
        }
        return i18nDao;
    }

    public Dao<Expression, Integer> getExpressionDao() throws SQLException {
        if (expressionDao == null) {
            expressionDao = getDao(Expression.class);
        }
        return expressionDao;
    }

    public Dao<Calculus, Integer> getCalculusDao() throws SQLException {
        if (calculusDao == null) {
            calculusDao = getDao(Calculus.class);
        }
        return calculusDao;
    }

    public Dao<Translation, Integer> getTranslationDao() throws SQLException {
        if (translationDao == null) {
            translationDao = getDao(Translation.class);
        }
        return translationDao;
    }

    public Dao<Unit, Integer> getUnitDao() throws SQLException {
        if (unitDao == null) {
            unitDao = getDao(Unit.class);
        }
        return unitDao;
    }

    public Dao<Favorite, Integer> getFavoriteDao() throws SQLException {
        if (favoriteDao == null) {
            favoriteDao = getDao(Favorite.class);
        }
        return favoriteDao;
    }

    public Dao<Assignment, Integer> getAssignmentDao() throws SQLException {
        if (assignmentDao == null) {
            assignmentDao = getDao(Assignment.class);
        }
        return assignmentDao;
    }

    public Dao<History, Integer> getHistoryDao() throws SQLException {
        if (historyDao == null) {
            historyDao = getDao(History.class);
        }
        return historyDao;
    }

    public Dao<Category, Integer> getCategoryDao() throws SQLException {
        if (categoryDao == null) {
            categoryDao = getDao(Category.class);
        }
        return categoryDao;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        /*
        * Migration to ORMLite in progress, legacy SQLQuery code will be removed
        */
        try {
            Log.i(DBHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Parameter.class, true);
            TableUtils.dropTable(connectionSource, Expression.class, true);
            TableUtils.dropTable(connectionSource, Locale.class, true);
            TableUtils.dropTable(connectionSource, I18n.class, true);
            TableUtils.dropTable(connectionSource, Calculus.class, true);
            TableUtils.dropTable(connectionSource, Translation.class, true);
            TableUtils.dropTable(connectionSource, Unit.class, true);
            TableUtils.dropTable(connectionSource, History.class, true);
            TableUtils.dropTable(connectionSource, Assignment.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DBHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
        onCreate(db);


    }
}
