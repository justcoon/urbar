package sk.c.urbar.scene.history;

import javafx.scene.Parent;

/**
 * history item
 *
 * @author coon
 */
public interface IHistoryItem {

    /**
     * get controller
     *
     * @param <T>
     * @return
     */
    public <T> T getController();

    /**
     * get content/component
     *
     * @return
     */
    public Parent getContent();
}
