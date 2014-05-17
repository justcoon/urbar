package sk.c.urbar.data.ie;

import org.apache.commons.beanutils.PropertyUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@link sk.c.urbar.data.ie.IExporter} abstract
 * @author coon
 */
public abstract class AExporter<T> implements IExporter<T> {
    protected List<String> properties;
    protected Collection<? extends Object> values;

    public AExporter(List<String> properties, Collection<? extends Object> values) {
        this.properties = properties;
        this.values = values;

    }

    @Override
    public List<String> getProperties() {

        return getObjectProperties();
    }

    protected List<String> getObjectProperties() {
        return properties;
    }

    @Override
    public List<List<T>> getValues() throws Exception {

        return getValues(values);
    }

    @Override
    public List<List<T>> getValues(Collection<?> values) throws Exception {
        List<List<T>> retVal = null;

        if (values != null) {
            List<String> properties = getObjectProperties();
            retVal = new ArrayList<List<T>>();
            List<T> v;
            for (Object value : values) {
                v = getValue(properties, value);
                retVal.add(v);
            }
        }


        return retVal;
    }

    /**
     * get values of <code>properties</code>
     *
     * @param properties
     * @param value
     * @return
     * @throws Exception
     * @see #getValue(String, Object)
     */
    protected List<T> getValue(List<String> properties, Object value) throws Exception {
        List<T> retVal = null;

        if (value != null) {


            retVal = new ArrayList<T>(properties.size());
            T v;
            for (String p : properties) {
                v = getValue(p, value);
                retVal.add(v);
            }
        }

        return retVal;
    }

    /**
     * get value for property
     *
     * @param property
     * @param value
     * @return
     * @throws Exception
     * @see #getConvertedPropertyValue(String, Class, Object)
     * @see #getPropertyValue(String, Object)
     */
    protected T getValue(String property, Object value) throws Exception {

        Object pv = getPropertyValue(property, value);

        return getConvertedPropertyValue(property, PropertyUtils.getPropertyType(value, property), pv);
    }

    /**
     * get value of property
     *
     * @param property
     * @param value
     * @return
     * @throws Exception
     */
    protected Object getPropertyValue(String property, Object value) throws Exception {
        return PropertyUtils.getProperty(value, property);
    }

    /**
     * get converted property value
     *
     * @param property
     * @param type
     * @param propertyValue
     * @return
     * @throws Exception
     */
    protected abstract T getConvertedPropertyValue(String property, Class<?> type, Object propertyValue) throws Exception;

}
