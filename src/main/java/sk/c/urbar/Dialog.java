package sk.c.urbar;

import javafx.beans.value.WritableValue;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sk.c.urbar.scene.ISceneController;
import sk.c.urbar.scene.history.AContentHandler;
import sk.c.urbar.scene.history.IHistoryItem;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by coon on 12/26/13.
 */
public class Dialog {
    Stage dialog;
    DialogContentHandler contentHandler;

    public Dialog() {
        dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.initModality(Modality.APPLICATION_MODAL);

        contentHandler = new DialogContentHandler();
    }

    protected Locale getLocale() {
        return Locale.getDefault();
    }

    protected ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(Main.MESSAGE_BUNDLES, getLocale());
    }

    public Stage getStage() {
        return dialog;
    }

    public <T> T dialog(String fxml, WritableValue<?> value) throws Exception {


        IHistoryItem hi = contentHandler.change(fxml, value);

        if (hi.getController() instanceof ISceneController) {
            dialog.setTitle(((ISceneController<?>) hi.getController()).getName());
        } else {
            dialog.setTitle(getResourceBundle().getString("app"));
        }

        if (!dialog.isShowing()) {
            dialog.showAndWait();
        }

        return hi.getController();
    }

    /**
     * dialogf {@link sk.c.urbar.scene.history.IContentHandler}
     */
    protected class DialogContentHandler extends AContentHandler {
        @Override
        protected URL getURL(String fxml) {
            return Dialog.class.getResource(fxml);
        }

        @Override
        protected ResourceBundle getResourceBundle() {
            return Dialog.this.getResourceBundle();
        }

        @Override
        protected void setContent(Node node) {
            dialog.setScene(new Scene((Parent) node));
        }

        @Override
        public IHistoryItem back() throws Exception {
            if (dialog.isShowing()) {
                dialog.close();
            }
            return null;
        }
    }
}
