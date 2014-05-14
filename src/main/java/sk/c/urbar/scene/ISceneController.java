package sk.c.urbar.scene;

import javafx.beans.value.WritableValue;
import sk.c.urbar.scene.history.IContentHandler;

/**
 * scene controller
 *
 * @author coon
 */
public interface ISceneController<T> {


    public String getName();

    public WritableValue<T> getValue();

    public void setValue(WritableValue<T> value);

    public boolean isChanged();

    public void setContentHandler(IContentHandler handler);

}
