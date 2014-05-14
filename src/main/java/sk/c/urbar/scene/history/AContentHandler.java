package sk.c.urbar.scene.history;

import javafx.beans.value.WritableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import sk.c.urbar.scene.ISceneController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * {@link IContentHandler} abstract
 */
public abstract class AContentHandler implements IContentHandler {


    /**
     * get resource bundle
     *
     * @return
     */
    protected abstract ResourceBundle getResourceBundle();

    /**
     * get fxml loader
     *
     * @param fxml
     * @return
     */
    protected FXMLLoader getLoader(String fxml) {


        return new FXMLLoader(getURL(fxml), getResourceBundle());
    }

    protected abstract URL getURL(String fxml);

    /**
     * set content
     *
     * @param node
     */
    protected abstract void setContent(Node node);

    @Override
    public <T> IHistoryItem change(String fxml, WritableValue<T> value) throws Exception {
        FXMLLoader loader = getLoader(fxml);

        Parent p = (Parent) loader.load();


        Object controller = loader.getController();

        if (controller instanceof ISceneController) {

            if (value != null) {
                ((ISceneController<T>) controller).setValue(value);
            }

            ((ISceneController<T>) controller).setContentHandler(this);

        }

        setContent(p);

        return new HistoryItem(p, controller);
    }


}
