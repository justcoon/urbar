package sk.c.urbar.settings;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * @author coon on 12/23/13.
 */
public class SettingsManager implements ISettingsManager {
    //--
    public static final String SETTINGS_FILE_NAME = "urbar.properties";
    public static final String SETTINGS_DIRECTORY = ".urbar";
    /**
     * singleton instance
     */
    private static final SettingsManager INSTANCE = new SettingsManager();
    /**
     * logger
     */
    private Log log = LogFactory.getLog(SettingsManager.class);
    /**
     * settings
     */
    private Properties settings = null;


    private SettingsManager() {

    }

    /**
     * get instance
     *
     * @return
     */
    public static ISettingsManager getInstance() {
        return INSTANCE;
    }

    /**
     * get settings directory
     *
     * @return
     */
    protected static String getSettingsDirectory() {
        return System.getProperty("user.home") + File.separator + SETTINGS_DIRECTORY + File.separator;
    }

    /**
     * get settings file name (with path)
     *
     * @return
     * @see #getSettingsDirectory()
     */
    protected static String getSettingsFileName() {
        return getSettingsDirectory() + SETTINGS_FILE_NAME;
    }

    /**
     * ensure settings loaded
     *
     * @see #loadSettings()
     */
    private synchronized void ensureSettingsLoaded() {
        if (settings == null) {
            settings = loadSettings();
        }
    }

    @Override
    public String getValue(SettingProperty p) {
        String retVal = null;

        if (p != null) {
            ensureSettingsLoaded();

            retVal = settings.getProperty(p.propertyName());

            if (retVal == null) {
                retVal = (String) p.defaultValue();
            }
        }

        return retVal;
    }

    @Override
    public <T> T getValue(Class<T> type, SettingProperty p) {

        T retVal = null;

        if (p != null) {
            ensureSettingsLoaded();

            Object v = settings.getProperty(p.propertyName());

            if (type != null) {
                retVal = (T) ConvertUtils.convert(v, type);
            } else {
                retVal = (T) v;
            }

            if (retVal == null) {
                retVal = (T) p.defaultValue();
            }
        }

        return retVal;

    }

    @Override
    public void setValue(SettingProperty p, Object value) {
        if (p != null) {
            ensureSettingsLoaded();

            settings.setProperty(p.propertyName(), value != null ? value.toString() : "");
        }
    }

    @Override
    public void saveSettings() {

        if (settings != null) {
            storeSettings(settings);
        }
    }

    /**
     * store settings
     *
     * @param settings
     */
    private void storeSettings(Properties settings) {

        if (settings != null) {


            FileOutputStream fos = null;


            String fileName = getSettingsFileName();
            try {


                log.debug("store settings to file " + fileName + " settings " + settings);


                //create directory for settings, if not exists
                File dir = new File(getSettingsDirectory());

                if (!dir.exists()) {
                    dir.mkdir();
                }


                fos = new FileOutputStream(fileName);

            } catch (FileNotFoundException e) {
                log.error("store settings to file " + fileName + " error:", e);

            }


            if (fos != null) {

                try {
                    settings.store(fos, null);
                } catch (Exception e) {
                    log.error("store settings to file " + fileName + " error:", e);
                }

                try {
                    fos.close();
                } catch (Exception e) {
                    log.error("fos close error:", e);
                }
            }


        }
    }

    /**
     * load settings
     *
     * @return
     */
    private Properties loadSettings() {
        Properties retVal = null;


        retVal = new Properties();

        String fileName = getSettingsFileName();

        FileInputStream fis = null;

        try {

            fis = new FileInputStream(fileName);

        } catch (FileNotFoundException e) {
            // neexistuje settings file, neriesi sa
            ;
        }

        log.debug("load settings from file " + fileName + ", file exists " + (fis != null));

        if (fis != null) {

            try {
                retVal.load(fis);
            } catch (Exception e) {
                log.error("load settings from file " + fileName + " error:", e);
            }

            try {
                fis.close();
            } catch (Exception e) {
                log.error("fis close error:", e);
            }
        }


        return retVal;
    }

}
