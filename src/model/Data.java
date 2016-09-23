package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * Name			: Data.java
 * Author		: Sandro Guerotto
 * Describtion	: Class for all data object. Used for GUI
 * Create on 	: 23.09.2016
 * Last modify  : dd.mm.yyyy name reason
 */
public class Data {

    private StringProperty data_name;
    private StringProperty data_type;
    private StringProperty data_create;

    public Data() {
    }


    public void setFirstName(String value) {
        this.data_nameProperty().set(value);
    }

    public String getdata_name() {
        return (String) this.data_nameProperty().get();
    }

    public StringProperty data_nameProperty() {
        if (this.data_name == null) {
            this.data_name = new SimpleStringProperty(this, "data_name");
        }

        return this.data_name;
    }

    public void setdata_type(String value) {
        this.data_typeProperty().set(value);
    }

    public String getdata_type() {
        return (String) this.data_typeProperty().get();
    }

    public StringProperty data_typeProperty() {
        if (this.data_type == null) {
            this.data_type = new SimpleStringProperty(this, "data_type");
        }

        return this.data_type;
    }

    public void setDataCreate(String value) {
        this.data_createProperty().set(value);
    }

    public String getdata_create() {
        return (String) this.data_createProperty().get();
    }

    public StringProperty data_createProperty() {
        if (this.data_create == null) {
            this.data_create = new SimpleStringProperty(this, "data_create");
        }

        return this.data_create;
    }

}
