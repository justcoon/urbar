package sk.c.urbar.scene.history;

import javafx.beans.value.WritableValue;

/**
 * Created by coon on 12/25/13.
 */
public interface IContentHandler {


    public <T> IHistoryItem change(String fxml, WritableValue<T> value) throws Exception;

    public IHistoryItem back() throws Exception;
}
