package sk.c.urbar.settings;

/**
 * setting manager
 *
 * @see sk.c.urbar.settings.SettingProperty
 *
 * @author coon
 */
public interface ISettingsManager {

    /**
     * save settings
     */
    public void saveSettings();

    /**
     * get value fro property
     *
     * @param p
     * @return
     */
    public String getValue(SettingProperty p);

    /**
     * get vaule for property
     *
     * @param type
     * @param p
     * @param <T>
     * @return
     */
    public <T> T getValue(Class<T> type, SettingProperty p);

    /**
     * set value for property
     *
     * @param p
     * @param value
     */
    public void setValue(SettingProperty p, Object value);
}
