package fr.softwaresemantics.howmanydroid;

/**
 * Created by Christophe Goessen on 30/12/13.
 */


public class Parameter {
    public String getName() {
        return name;
    }

    public Parameter(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {

        return type;
    }

    public Object getValue() {
        return value;
    }

    private String name;
    private String type;
    private Object value;
}
