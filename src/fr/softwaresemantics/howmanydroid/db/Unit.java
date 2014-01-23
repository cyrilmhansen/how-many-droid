package fr.softwaresemantics.howmanydroid.db;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by christophe Goessen on 23/01/14.
 */
public class Unit implements Serializable {
public Unit(){}
    @DatabaseField (generatedId = true)
    int unitID;
    @DatabaseField()
    String value_type;
    @DatabaseField(index = true)
    String unit_name;
    @DatabaseField()
    String symbol;

    public String getValue_type() {
        return value_type;
    }

    public void setValue_type(String value_type) {
        this.value_type = value_type;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
