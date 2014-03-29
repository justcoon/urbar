package sk.c.urbar.data.entity;

import org.apache.commons.beanutils.PropertyUtils;
import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;
import sk.c.urbar.data.RateController;
import sk.c.urbar.data.ShareUtils;
import sk.c.urbar.data.ie.AExporter;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * {@link sk.c.urbar.data.ie.IExporter} for {@link sk.c.urbar.data.entity.Person}
 */
public class PersonExporter extends AExporter<String> {

    private static final List<String> PERSON_PROPERTIES = Arrays.asList("firstName", "surName", "address", "identification","birthDate", "registeredFrom", "registeredTo", "email", "bankAccount", "notice", "document", "part", "votes");
    //    private static final List<String> PERSON_PROPERTIES = Arrays.asList("firstName", "surName", "address", "birthDate", "registeredFrom", "registeredTo", "email", "bankAccount", "notice", "document");
    protected Map<Class<?>, CellProcessor> typeProcessors;
    protected CellProcessor typeProcessorDefault;
    protected Map<String, CellProcessor> propertyProcessors;


    public PersonExporter(Collection<Person> values) {
        this(PERSON_PROPERTIES, values);
    }

    public PersonExporter(List<String> properties, Collection<Person> values) {
        super(properties, values);
        typeProcessors = new HashMap<>();
        typeProcessors.put(java.util.Date.class, new ConvertNullTo("", new FmtDate("dd.MM.yyyy")));
        typeProcessorDefault = new ConvertNullTo("");

        propertyProcessors = new HashMap<String, CellProcessor>();
        propertyProcessors.put("part", new SharePartCellProcessor());
        propertyProcessors.put("votes", new ShareVotesCellProcessor());

    }

    @Override
    protected String getValue(String property, Object value) throws Exception {
        PropertyDescriptor pd = null;

        try {
            pd = PropertyUtils.getPropertyDescriptor(value, property);
        } catch (Exception e) {
            ;
        }

        Object pv = null;

        if (pd != null) {

            pv = getPropertyValue(property, value);

        } else {
            pv = value;
        }

        return getConvertedPropertyValue(property, pd != null ? pd.getPropertyType() : null, pv);
    }

    @Override
    protected String getConvertedPropertyValue(String property, Class<?> type, Object propertyValue) throws Exception {
        String retVal = null;
        CellProcessor cp = getProcessor(property, type);

        Object converted = cp.execute(propertyValue, null);
        retVal = String.valueOf(converted);
        return retVal;
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
     * {@link sk.c.urbar.data.entity.Share} part cell processor
     */
    public class SharePartCellProcessor implements CellProcessor {
        @Override
        public Object execute(Object value, CsvContext context) {
            Integer retVal = null;

            if (value instanceof Person) {
                Person person = (Person) value;
                int partSum = 0;

                for (Share s : person.getShares()) {
                    if (s.getPart() != null) {
                        partSum += s.getPart();
                    }
                }
                retVal = partSum;
            }

            return retVal;
        }
    }

    /**
     * {@link sk.c.urbar.data.entity.Share} votes cell processor
     *
     * @see sk.c.urbar.data.ShareUtils#getVotesSum(java.util.Collection, java.util.Collection)
     */
    public class ShareVotesCellProcessor implements CellProcessor {
        @Override
        public Object execute(Object value, CsvContext context) {
            Integer retVal = null;

            if (value instanceof Person) {
                Person person = (Person) value;

                retVal = ShareUtils.getVotesSum(person.getShares(), RateController.getInstance().getValues());
            }

            return retVal;
        }
    }
}


