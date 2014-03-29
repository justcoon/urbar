package sk.c.urbar.data;

import java.util.List;

/**
 * controller
 *
 * @param <T> data type
 * @author coon
 */
public interface IController<T> {

    /**
     * load values
     *
     * @param values
     */
    public void load(List<T> values);

    /**
     * get values
     *
     * @return
     */
    public List<T> getValues();
}
