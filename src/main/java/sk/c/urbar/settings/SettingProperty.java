package sk.c.urbar.settings;

/**
 * settings property
 *
 * @author coon
 */
public enum SettingProperty {
    /**
     * locale
     */
    LOCALE("urbar.locale", "sk"),

    /**
     * data file name
     */
    DATA_FILE_NAME("urbar.data.filename", "urbar.xml"),

    /**
     * encoding
     */
    ENCODING("urbar.encoding", "UTF-8");

    //--
    final String propertyName;
    final Object defaultValue;

    SettingProperty(String propertyName, Object defaultValue) {
        this.defaultValue = defaultValue;
        this.propertyName = propertyName;
    }

    /**
     * get property name
     *
     * @return
     */
    public String propertyName() {
        return propertyName;
    }

    /**
     * get default value
     *
     * @return
     */
    public Object defaultValue() {
        return defaultValue;
    }
}
