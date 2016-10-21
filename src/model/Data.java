package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * @author          :   Sandro Guerotto
 * Created          :   23.09.2016
 * Project          :   cloud
 * Package          :   model
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   Class for all data object. Used for GUI
 */

public class Data {

    private StringProperty data_name;
    private StringProperty data_type;
    private StringProperty data_size;
    private StringProperty data_create;
    private StringProperty data_last;

    public Data() {
    }

    //name
    public void setdata_name(String value) {
        this.data_nameProperty().set(value);
    }

    public String getdata_name() {
        return this.data_nameProperty().get();
    }

    private StringProperty data_nameProperty() {
        if (this.data_name == null) {
            this.data_name = new SimpleStringProperty(this, "data_name");
        }

        return this.data_name;
    }

    //type
    public void setdata_type(String value) {
        this.data_typeProperty().set(value);
    }

    public String getdata_type() {
        return this.data_typeProperty().get();
    }

    private StringProperty data_typeProperty() {
        if (this.data_type == null) {
            this.data_type = new SimpleStringProperty(this, "data_type");
        }

        return this.data_type;
    }

    //Create
    public void setdatacreate(String value) {
        this.data_createProperty().set(value);
    }

    public String getdata_create() {
        return this.data_createProperty().get();
    }

    private StringProperty data_createProperty() {
        if (this.data_create == null) {
            this.data_create = new SimpleStringProperty(this, "data_create");
        }

        return this.data_create;
    }

    //last
    public void setdata_last(String value) {
        this.data_lastProperty().set(value);
    }

    public String getdata_last() {
        return this.data_lastProperty().get();
    }

    private StringProperty data_lastProperty() {
        if (this.data_last == null) {
            this.data_last = new SimpleStringProperty(this, "data_last");
        }

        return this.data_last;
    }

    //size
    public void setdata_size(String value) {
        this.data_sizeProperty().set(value);
    }

    public String getdata_size() {
        return this.data_sizeProperty().get();
    }

    private StringProperty data_sizeProperty() {
        if (this.data_size == null) {
            this.data_size = new SimpleStringProperty(this, "data_size");
        }

        return this.data_size;
    }


}
