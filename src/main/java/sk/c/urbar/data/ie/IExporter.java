package sk.c.urbar.data.ie;

import java.util.Collection;
import java.util.List;

/**
 * exporter
 */
public interface IExporter<T> {


    /**
     * get properties
     *
     * @return
     */
    public List<String> getProperties();

    /**
     * get values for export, in order of properties
     *
     * @return
     * @throws Exception
     * @see #getProperties()
     */
    public List<List<T>> getValues() throws Exception;

    /**
     * get values for export, in order of properties
     *
     * @param values
     * @return
     * @throws Exception
     * @see #getProperties()
     */
    public List<List<T>> getValues(Collection<? extends Object> values) throws Exception;
}
