package sk.c.urbar.data.ie;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;
import sk.c.urbar.data.PersonController;
import sk.c.urbar.data.entity.Person;
import sk.c.urbar.settings.ISettingsManager;
import sk.c.urbar.settings.SettingProperty;
import sk.c.urbar.settings.SettingsManager;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.util.*;

/**
 * @author coon
 */
public class CsvImportExportManager implements IImportExportManager {

    /**
     * singelton instance
     */
    private static final CsvImportExportManager INSTANCE = new CsvImportExportManager();
    /**
     * person properties
     */
//    private static final String[] PERSON_PROPERTIES = new String[]{"firstName", "surName", "address", "birthDate", "registeredFrom", "registeredTo", "email", "bankAccount", "notice", "document", "shares.part", "shares.votes"};
    private static final String[] PERSON_PROPERTIES = new String[]{"firstName", "surName", "address", "birthDate", "registeredFrom", "registeredTo", "email", "bankAccount", "notice", "document"};
    //---
    //---
    protected Map<Class<?>, CellProcessor> toCsvTypeProcessors;
    protected CellProcessor toCsvTypeProcessorDefault;
    protected Map<Class<?>, CellProcessor> fromCsvTypeProcessors;
    protected CellProcessor fromCsvTypeProcessorDefault;
    protected Map<String, CellProcessor> toCsvPropertyProcessors;
    protected Map<String, CellProcessor> fromCsvPropertyProcessors;
    /**
     * logger
     */
    private Log log = LogFactory.getLog(CsvImportExportManager.class);

    private CsvImportExportManager() {
        toCsvTypeProcessors = new HashMap<Class<?>, CellProcessor>();
        toCsvTypeProcessors.put(java.util.Date.class, new ConvertNullTo("", new FmtDate("dd.MM.yyyy")));
        toCsvTypeProcessorDefault = new Optional();

        fromCsvTypeProcessors = new HashMap<Class<?>, CellProcessor>();
        fromCsvTypeProcessors.put(java.util.Date.class, new ConvertNullTo(null, new ParseDate("dd.MM.yyyy")));
        fromCsvTypeProcessorDefault = new Optional();


        toCsvPropertyProcessors = new HashMap<String, CellProcessor>();
//        toCsvPropertyProcessors.put("shares.part", null);

        fromCsvPropertyProcessors = new HashMap<String, CellProcessor>();

    }

    /**
     * get {@link sk.c.urbar.data.IStoreManager} instance
     *
     * @return
     */
    public static IImportExportManager getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean save(File file, IExporter<?> exporter) {

        boolean retVal = false;

        if (file != null) {
            log.debug("save export file " + file.getName());

            String encoding = SettingsManager.getInstance().getValue(SettingProperty.ENCODING);

            Writer fw = null;
            FileOutputStream fos = null;
            try {

                fos = new FileOutputStream(file);
                fw = new OutputStreamWriter(fos, encoding);

//                fw = new FileWriter(file);

                setValues(exporter, CsvPreference.STANDARD_PREFERENCE, fw);

                retVal = true;

            } catch (Exception e) {
                log.error("save export file " + file.getName() + " error:", e);
            }


            try {
                if (fw != null) {
                    fw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                log.error("fw close error:", e);
            }

        }
        return retVal;
    }

    /**
     * get values from controllers
     *
     * @return
     */
    private List<Person> getControllerValues() {

        return PersonController.getInstance().getValues();
    }

    /**
     * load controllers
     *
     * @param values value for controllers
     * @return
     */
    private boolean loadController(List<Person> values) {

        boolean retVal = false;

        if (values != null) {

            PersonController.getInstance().getValues().addAll(values);

            retVal = true;

        }

        return retVal;
    }

    @Override
    public boolean load(File file, IImporter<?> importer) {

        boolean retVal = false;

        if (file != null && file.exists()) {

            log.debug("load import file " + file.getName());
            FileReader fr = null;

            try {
                fr = new FileReader(file);

//                List<Person> values = getValues(Person.class, fr);
//
//                retVal = loadController(values);

                loadValues(importer, CsvPreference.STANDARD_PREFERENCE, fr);

                retVal = true;
            } catch (Exception e) {
                log.error("load import file " + file.getName() + " error:", e);
            }


            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                log.error("fr close error:", e);
            }

        }

        return retVal;
    }

    /**
     * set values to writer
     *
     * @param exporter
     * @param writer
     * @throws Exception
     */
    protected void setValues(IExporter<?> exporter, CsvPreference preference, Writer writer) throws Exception {

        if (exporter != null && writer != null) {

            List<String> prop = exporter.getProperties();

            String[] properties = prop.toArray(new String[prop.size()]);

            List<List<String>> values = ((IExporter<String>) exporter).getValues();

            CsvListWriter w = new CsvListWriter(writer, preference);
            w.writeHeader(properties);

            Map<String, String> v;
            for (List<String> value : values) {
//                v = getMap(prop, value);
//                w.write(v, properties);
                w.write(value);
            }


            w.close();


        }
    }

    /**
     * get values form reader
     *
     * @param reader
     * @return
     * @throws Exception
     */
    protected void loadValues(IImporter<?> importer, CsvPreference preference, Reader reader) throws Exception {

        if (importer != null && reader != null) {


            CsvListReader r = new CsvListReader(reader, preference);

            String[] header = r.getHeader(true);

            importer.setProperties(Arrays.asList(header));

            List<String> v;
            while ((v = r.read()) != null) {
                importer.addValue(v);
            }

            r.close();
        }

    }

    protected Map<String, String> getMap(List<String> properties, List<String> values) {
        Map<String, String> retVal = null;
        if (properties != null && values != null) {
            retVal = new LinkedHashMap<String, String>();
            for (int i = 0; i < properties.size(); i++) {
                retVal.put(properties.get(i), values.get(i));
            }
        }
        return retVal;
    }

    /**
     * get values form reader
     *
     * @param reader
     * @return
     * @throws Exception
     */
    protected <T> List<T> getValues(Class<T> type, Reader reader) throws Exception {
        List<T> retVal = null;

        if (reader != null) {
            retVal = new ArrayList<T>();


            CsvBeanReader r = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

            String[] header = r.getHeader(true);

            CellProcessor[] processors = getFromCsvProcessors(type, header);
            T p;

            while ((p = r.read(type, header, processors)) != null) {
                retVal.add(p);
            }

            r.close();
        }
        return retVal;
    }

    protected CellProcessor[] getFromCsvProcessors(Class<?> type, String[] properties) throws Exception {
        CellProcessor[] retVal = null;

        if (type != null && properties != null) {

            Object bean = type.newInstance();
            retVal = new CellProcessor[properties.length];

            PropertyDescriptor pd;
            CellProcessor cp;
            for (int i = 0; i < properties.length; i++) {

                pd = PropertyUtils.getPropertyDescriptor(bean, properties[i]);

                cp = getFromCsvProcessor(properties[i], pd != null ? pd.getPropertyType() : null);

                retVal[i] = cp;
            }
        }

        return retVal;
    }

    protected CellProcessor getFromCsvProcessor(String property, Class<?> type) {
        CellProcessor retVal = null;

        if (type != null) {
            if (property != null) {
                retVal = fromCsvPropertyProcessors.get(property);
            }
            if (retVal == null) {
                retVal = fromCsvTypeProcessors.get(type);
            }
            if (retVal == null) {
                retVal = fromCsvTypeProcessorDefault;
            }
        }
        return retVal;
    }

    protected ISettingsManager getSettingsManager() {
        return SettingsManager.getInstance();
    }
}
