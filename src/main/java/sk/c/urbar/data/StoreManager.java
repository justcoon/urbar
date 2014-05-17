package sk.c.urbar.data;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sk.c.urbar.data.entity.Person;
import sk.c.urbar.data.entity.Rate;
import sk.c.urbar.data.entity.Share;
import sk.c.urbar.settings.ISettingsManager;
import sk.c.urbar.settings.SettingProperty;
import sk.c.urbar.settings.SettingsManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link sk.c.urbar.data.IStoreManager} impl
 *
 * @author coon
 */
public class StoreManager implements IStoreManager {


    private static final StoreManager INSTANCE = new StoreManager();
    private Log log = LogFactory.getLog(StoreManager.class);
    private XStream xstream;
    private Map<String, IController<? extends Object>> controllers;

    private StoreManager() {

        xstream = new XStream();
        xstream.alias("person", Person.class);
        xstream.alias("share", Share.class);
        xstream.alias("rate", Rate.class);

        controllers = new LinkedHashMap<String, IController<?>>();
        controllers.put("person", PersonController.getInstance());
        controllers.put("rate", RateController.getInstance());
    }

    /**
     * get {@link sk.c.urbar.data.IStoreManager} instance
     *
     * @return
     */
    public static IStoreManager getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean save(File file) {

        boolean retVal = false;

        if (file != null) {
            log.debug("saving file " + file.getName());

            FileWriter fw = null;

            try {
                fw = new FileWriter(file);
                getSettingsManager().setValue(SettingProperty.DATA_FILE_NAME, file.getPath());

                xstream.toXML(getControllersValues(), fw);

                retVal = true;

            } catch (Exception e) {


                log.error("saving file " + file.getName() + " error:", e);
            }


            try {
                if (fw != null) {
                    fw.close();
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
    private Map<String, List<?>> getControllersValues() {
        Map<String, List<?>> retVal = new LinkedHashMap<String, List<?>>();

        for (Map.Entry<String, IController<?>> e : controllers.entrySet()) {
            retVal.put(e.getKey(), e.getValue().getValues());
        }
        return retVal;
    }

    /**
     * load controllers
     *
     * @param v value for controllers
     * @return
     */
    private boolean loadControllers(Object v) {

        boolean retVal = false;

        if (v != null && v instanceof Map<?, ?>) {

            Map<String, List<?>> values = (Map<String, List<?>>) v;

            List<Object> cvs;

            for (Map.Entry<String, IController<?>> e : controllers.entrySet()) {
                cvs = (List<Object>) values.get(e.getKey());
                ((IController<Object>) e.getValue()).load(cvs);
            }

            retVal = true;


        }
        //kompatibilita
        else if (v != null && v instanceof List<?>) {
            List<Object> values = (List<Object>) v;
            ((IController<Object>) controllers.get("person")).load(values);

            retVal = true;
        }

        return retVal;
    }

    @Override
    public boolean load(File file) {

        boolean retVal = false;

        if (file != null && file.exists()) {
            log.debug("loading file " + file.getName());
            FileReader fr = null;

            try {
                fr = new FileReader(file);
                getSettingsManager().setValue(SettingProperty.DATA_FILE_NAME, file.getPath());

                Object v = xstream.fromXML(fr);

                retVal = loadControllers(v);
            } catch (Exception e) {

                log.error("saving file " + file.getName() + " error:", e);
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
     * get settings manager
     *
     * @return
     */
    protected ISettingsManager getSettingsManager() {
        return SettingsManager.getInstance();
    }
}
