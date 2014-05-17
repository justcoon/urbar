package sk.c.urbar.scene.history;

import javafx.scene.Parent;

/**
 * {@link sk.c.urbar.scene.history.IHistoryItem} implementation
 *
 * @author coon
 */
public class HistoryItem implements IHistoryItem {

    Parent content;
    Object controller;


    public HistoryItem(Parent content, Object controller) {
        this.content = content;
        this.controller = controller;
    }

    @Override
    public <T> T getController() {
        return (T) controller;
    }

    @Override
    public Parent getContent() {
        return content;
    }
}
