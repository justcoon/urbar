package sk.c.urbar.component.table;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * Created by coon on 3/22/14.
 */
public class EditingCheckBoxCell<S, T> extends TableCell<S, T> {
    private CheckBox checkBox;

    protected Class<T> type;

    public EditingCheckBoxCell(Class<T> type) {
        this.type = type;
        checkBox = new CheckBox();
        checkBox.setDisable(true);
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(isEditing())        {
                    commitEdit(getTypeValue(checkBox.isSelected()));
                }
            }
        });
        this.setGraphic(checkBox);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setEditable(true);
    }


    protected Class<T> getType() {
        return type;
    }


    @Override
    public void startEdit() {
        super.startEdit();
        if (isEmpty()) {
            return;
        }
        checkBox.setDisable(false);
        checkBox.requestFocus();
    }
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        checkBox.setDisable(true);
    }
    public void commitEdit(T value) {
        super.commitEdit(value);
        checkBox.setDisable(true);
    }
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (!isEmpty()) {
            checkBox.setSelected(getBooleanValue(item));
        }
    }
    /**
     * get type value
     *
     * @param value
     * @return
     */
    protected T getTypeValue(Boolean value) {
        return (T) ConvertUtils.convert(value, getType());
    }
    /**
     * get string value
     *
     * @param value
     * @return
     */
    protected boolean getBooleanValue(T value) {
        if(value instanceof  Boolean) {
           return (Boolean) value;
        }
        else {
            return value != null;
        }

    }
}
