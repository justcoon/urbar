package sk.c.urbar.component.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author coon
 */
public class PropertyValueFactory<T> implements Callback<TableColumn.CellDataFeatures<Object, String>, ObservableValue<String>> {

    protected String property;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Object, String> objectStringCellDataFeatures) {


        String retVal = null;

        Object data = objectStringCellDataFeatures.getValue();


        T v = getValue(data);

        retVal = getStringValue(v);


        return new ReadOnlyObjectWrapper<String>(retVal);
    }

    protected T getValue(Object data) {
        T v = null;
        if (data != null) {


            try {
                v = (T) PropertyUtils.getProperty(data, getProperty());
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return v;
    }

    protected String getStringValue(T value) {
        return value != null ? String.valueOf(value) : null;
    }
}
