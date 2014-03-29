package sk.c.urbar.settings;

/**
 * settings property
 */
public enum SettingProperty {
    /**
     * locale
     */
    LOCALE("urbar.locale", "sk"),

    /**
     * data file name
     */
    DATA_FILE_NAME("urbar.data.filename", "urbar.xml");
    //--
    final String propertyName;
    final Object defaultValue;

    SettingProperty(String propertyName, Object defaultValue) {
        this.defaultValue = defaultValue;
        this.propertyName = propertyName;
    }

    public String propertyName() {
        return propertyName;


    }

    public Object defaultValue() {
        return defaultValue;
    }
}
