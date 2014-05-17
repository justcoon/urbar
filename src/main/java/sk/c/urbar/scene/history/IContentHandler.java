package sk.c.urbar.scene.history;

import javafx.beans.value.WritableValue;

/**
 * content handler
 * @author coon
 */
public interface IContentHandler {


    /**
     * change content
     * @param fxml
     * @param value
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> IHistoryItem change(String fxml, WritableValue<T> value) throws Exception;

    /**
     * go back (previous content)
     * @return
     * @throws Exception
     */
    public IHistoryItem back() throws Exception;
}
