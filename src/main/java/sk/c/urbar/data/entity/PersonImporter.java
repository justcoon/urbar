package sk.c.urbar.data.entity;

import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;
import sk.c.urbar.data.ie.AImporter;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link sk.c.urbar.data.ie.IImporter} for {@link sk.c.urbar.data.entity.Person}
 */
public class PersonImporter extends AImporter<Person> {
    protected Map<Class<?>, CellProcessor> typeProcessors;
    protected CellProcessor typeProcessorDefault;
    protected Map<String, CellProcessor> propertyProcessors;
    protected Map<String, IValueSetter<Person>> propertyValueSetters;

    public PersonImporter() {
        typeProcessors = new HashMap<>();
        typeProcessors.put(java.util.Date.class, new ConvertNullTo(null, new ParseDate("dd.MM.yyyy")));
        typeProcessorDefault = new Optional();

        propertyProcessors = new HashMap<String, CellProcessor>();
        propertyProcessors.put("part", new ConvertNullTo(null, new ParseInt()));
//        propertyProcessors.put("shares.votes", new VotesCellProcessor());

        propertyValueSetters = new HashMap<>();
        propertyValueSetters.put("part", new SharePartSetter());
        propertyValueSetters.put("votes", new NoValueSetter<Person>());
    }

    @Override
    protected Person createInstance() throws Exception {
        return new Person();
    }

    @Override
    protected IValueSetter<Person> getValueSetter(String property, Class<?> type) {

        IValueSetter<Person> retVal = null;

        if (property != null) {
            retVal = propertyValueSetters.get(property);
        }

        if (retVal == null) {
            retVal = super.getValueSetter(property, type);
        }

        return retVal;
    }

    @Override
    protected Object getConvertedPropertyValue(String property, Class<?> type, Object value) throws Exception {

        CellProcessor cp = getProcessor(property, type);

        return cp.execute(value, null);
    }

    /**
     * get processor
     *
     * @param property
     * @param type
     * @return
     */
    protected CellProcessor getProcessor(String property, Class<?> type) {
        CellProcessor retVal = null;

        if (property != null || type != null) {

            if (property != null) {
                retVal = propertyProcessors.get(property);
            }
            if (retVal == null) {
                retVal = typeProcessors.get(type);
            }
            if (retVal == null) {
                retVal = typeProcessorDefault;
            }
        }
        return retVal;
    }

    /**
     * {@link sk.c.urbar.data.ie.AImporter.IValueSetter} for {@link sk.c.urbar.data.entity.Share} part
     */
    public static class SharePartSetter implements IValueSetter<Person> {

        @Override
        public void setValue(String property, Object propertyValue, Person object) throws Exception {
            Share s = new Share();
            s.setPart((Integer) propertyValue);
            object.getShares().add(s);
        }
    }

}
