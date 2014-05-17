package sk.c.urbar.data.ie;

import org.apache.commons.beanutils.PropertyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link sk.c.urbar.data.ie.IImporter} abstract
 * @author coon
 */
public abstract class AImporter<T> implements IImporter<T> {
    protected List<List<?>> values = new ArrayList<>();
    protected List<String> properties;
    protected IValueSetter<T> valueSetter = new ValueSetter<T>();

    protected List<String> getObjectProperties() {
        return properties;
    }

    @Override
    public void setProperties(List<String> properties) {
        this.properties = properties;
    }

    @Override
    public void addValue(List<?> value) {
        if (value != null) {
            values.add(value);
        }
    }

    @Override
    public List<T> getValues() throws Exception {

        List<T> retVal = null;

        if (values != null) {
            retVal = new ArrayList<>(values.size());
            List<String> properties = getObjectProperties();
            T v;
            for (List<?> value : values) {
                v = getValue(properties, value);

                retVal.add(v);

            }
        }


        return retVal;
    }

    /**
     * create object instance
     *
     * @return
     * @throws Exception
     */
    protected abstract T createInstance() throws Exception;

    /**
     * get <code>T</code> value
     *
     * @param properties
     * @param value
     * @return
     * @throws Exception
     * @see #createInstance()
     * @see #setValue(String, Object, Object)
     */
    protected T getValue(List<String> properties, List<?> value) throws Exception {
        T retVal = null;
        if (value != null) {
            retVal = createInstance();

            for (int i = 0; i < properties.size(); i++) {
                setValue(properties.get(i), value.get(i), retVal);
            }
        }

        return retVal;
    }

    /**
     * set  <code>value</code> for <code>property</code> to <code>object</code>
     *
     * @param property
     * @param value
     * @param object
     * @throws Exception
     * @see #getValueSetter(String, Class)
     * @see #getConvertedPropertyValue(String, Class, Object)
     */
    protected void setValue(String property, Object value, T object) throws Exception {
        Class<?> type = PropertyUtils.getPropertyType(object, property);

        IValueSetter<T> vs = getValueSetter(property, type);
//        if (vs != null) {
        Object pv = getConvertedPropertyValue(property, type, value);

        vs.setValue(property, pv, object);
//        }
    }

    /**
     * get value setter
     *
     * @param property
     * @param type
     * @return
     */
    protected IValueSetter<T> getValueSetter(String property, Class<?> type) {
        return valueSetter;
    }

    /**
     * get converted value
     *
     * @param property
     * @param type
     * @param value
     * @return
     * @throws Exception
     */
    protected abstract Object getConvertedPropertyValue(String property, Class<?> type, Object value) throws Exception;

    /**
     * set value of property to object
     *
     * @param <T>
     */
    public static interface IValueSetter<T> {
        public void setValue(String property, Object propertyValue, T object) throws Exception;
    }

    /**
     * default  {@link sk.c.urbar.data.ie.AImporter.IValueSetter} implementation
     *
     * @param <T>
     * @see org.apache.commons.beanutils.PropertyUtils#setProperty(Object, String, Object)
     */
    public static class ValueSetter<T> implements IValueSetter<T> {

        @Override
        public void setValue(String property, Object propertyValue, T object) throws Exception {
            PropertyUtils.setProperty(object, property, propertyValue);
        }
    }

    /**
     * no value setting  {@link sk.c.urbar.data.ie.AImporter.IValueSetter} implementation
     *
     * @param <T>
     * @see org.apache.commons.beanutils.PropertyUtils#setProperty(Object, String, Object)
     */
    public static class NoValueSetter<T> implements IValueSetter<T> {

        @Override
        public void setValue(String property, Object propertyValue, T object) throws Exception {

        }
    }
}
