package sk.c.urbar;

import com.google.common.eventbus.Subscribe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sk.c.urbar.component.SimpleObjectValue;
import sk.c.urbar.data.RateController;
import sk.c.urbar.data.RateUtils;
import sk.c.urbar.data.entity.Share;
import sk.c.urbar.scene.ISceneController;
import sk.c.urbar.scene.history.IContentHandler;

import javax.swing.event.ChangeEvent;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * share form controller
 *
 * @author coon
 */
public class ShareFormController implements Initializable, ISceneController<Share> {
    ResourceBundle resourceBundle;
    IContentHandler handler;
    @FXML
    javafx.scene.control.TableView tableView;
    @FXML
    private TextField part;
    @FXML
    private Label votes;
    /**
     * share data
     */
    private WritableValue<Share> share;
    /**
     * edit or add ?
     */
    private boolean edit = false;
    private boolean okClicked = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;


        part.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                firePartValueChanged();
            }
        });
        setValue(new SimpleObjectValue<Share>(new Share()), false);
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {


        if (isInputValid()) {

            setValueFromComponents();

            okClicked = true;


            try {
                handler.back();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void setValueFromComponents() {
        share.getValue().setPart(Integer.valueOf(part.getText()));
//        share.getValue().setVotes(Integer.valueOf(votes.getText()));
        share.getValue().setVotes(null);
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {

        try {

            handler.back();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (errorMessage.length() == 0) {
            return true;
        } else {
//            // Show the error message
//            Dialogs.showErrorDialog(dialogStage, errorMessage,
//                    "Please correct invalid fields", "Invalid Fields");
            return false;
        }
    }

    /**
     * set person helper method
     *
     * @param share
     */
    protected void setValueToComponents(Share share) {

        this.edit = edit;
        part.setText(share != null && share.getPart() != null ? share.getPart().toString() : "");


//        votes.setText(share != null && share.getVotes() != null ? share.getVotes().toString() : "");

//        fireValueChanged();
    }

    @Override
    public WritableValue<Share> getValue() {
        return share;
    }

    @Override
    public void setValue(WritableValue<Share> value) {

        if (value == null) {
            throw new IllegalArgumentException("Missing required value");
        }

        this.share = value;

        setValueToComponents(value.getValue());
    }

    protected void setValue(WritableValue<Share> value, boolean edit) {

        if (value == null) {
            throw new IllegalArgumentException("Missing required value");
        }

        this.share = value;
        this.edit = edit;
        setValueToComponents(value.getValue());
    }

    @Override
    public boolean isChanged() {
        return okClicked;
    }

    @Override
    public void setContentHandler(IContentHandler handler) {
        this.handler = handler;
    }

    @Override
    public String getName() {
        return resourceBundle.getString("share");
    }

    protected void firePartValueChanged() {
        String p = part.getText();

        Integer part = null;

        try {
            part = Integer.valueOf(p);
        } catch (NumberFormatException e) {
            part = null;
        }

        Integer v = RateUtils.getVotes(part, RateController.getInstance().getValues());

        votes.setText(v != null ? String.valueOf(v) : "");
    }

}
