package sk.c.urbar;

import com.google.common.eventbus.EventBus;
import javafx.application.Application;
import javafx.beans.value.WritableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sk.c.urbar.data.StoreManager;
import sk.c.urbar.scene.history.AContentHandler;
import sk.c.urbar.scene.history.IContentHandler;
import sk.c.urbar.scene.history.IHistoryItem;
import sk.c.urbar.settings.SettingProperty;
import sk.c.urbar.settings.SettingsManager;

import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * main application class
 *
 * @author coon
 */
public class Main extends Application {

    public static final String VERSION = "0.1";
    public static final String MESSAGE_BUNDLES = "MessageBundles";
    private static Main instance;
    Stage stage;
    BorderPane rootLayout;
    MainContentHandler contentChanger;
    EventBus eventBus = new EventBus();
    ResourceBundle.Control retsourceBundleControl = new UTF8Control();

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * get statge
     *
     * @return
     */
    public Stage getStage() {
        return stage;
    }

    protected Locale getLocale() {
        return Locale.getDefault();
    }

    protected ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(MESSAGE_BUNDLES, getLocale(), retsourceBundleControl);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Locale.setDefault(new Locale(SettingsManager.getInstance().getValue(SettingProperty.LOCALE)));
        ResourceBundle resourceBundle = getResourceBundle();

        primaryStage.setTitle(resourceBundle.getString("app"));

//        PersonController.getInstance().loadData(resourceBundle);

        String fn = SettingsManager.getInstance().getValue(SettingProperty.DATA_FILE_NAME);

        File file = new File(fn);

        if (file.exists()) {
            StoreManager.getInstance().load(file);
        }

        rootLayout = load("mainscreen.fxml");

        contentChanger = new MainContentHandler();

        Scene scene = new Scene(rootLayout, 700, 450);

        primaryStage.setScene(scene);


        contentChanger.change("personlist.fxml", null);
//        replaceSceneContent("personlist.fxml");

        primaryStage.show();


    }

    protected <T> T load(String fxml) throws Exception {

        URL r = getClass().getResource(fxml);

        FXMLLoader loader = new FXMLLoader(r, getResourceBundle());
        T c = (T) loader.load();

        return c;
    }

    /**
     * get content handler
     *
     * @return
     */
    public IContentHandler getContentHandler() {
        return contentChanger;
    }

    /**
     * get event bus
     *
     * @return
     */
    public EventBus getEventBus() {
        return eventBus;
    }

    protected void setPage(Parent page) {
        Scene scene = stage.getScene();

        if (scene == null) {
            scene = new Scene(page, 700, 450);

            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
    }

    /**
     * main {@link sk.c.urbar.scene.history.IContentHandler}
     */
    protected class MainContentHandler extends AContentHandler {
        IHistoryItem current;

        @Override
        protected URL getURL(String fxml) {
            return Main.class.getResource(fxml);
        }

        @Override
        protected ResourceBundle getResourceBundle() {
            return Main.this.getResourceBundle();
        }

        @Override
        protected void setContent(Node node) {

            rootLayout.setCenter(node);
        }

        @Override
        public synchronized <T> IHistoryItem change(String fxml, WritableValue<T> value) throws Exception {

            IHistoryItem previous = current;
            IHistoryItem hi = super.change(fxml, value);

            if (hi != null) {

                //unregister previous
                if (previous != null && previous.getController() != null) {
                    getEventBus().unregister(previous.getController());
                }

                //register current
                current = hi;

                if (current.getController() != null) {
                    getEventBus().register(current.getController());
                }
            }


            return hi;
        }

        @Override
        public IHistoryItem back() throws Exception {
            return null;
        }
    }

}
