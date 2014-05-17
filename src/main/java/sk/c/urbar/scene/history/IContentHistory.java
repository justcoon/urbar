package sk.c.urbar.scene.history;

/**
 * content history
 *
 * @author coon
 */
public interface IContentHistory {

    /**
     * previous item
     *
     * @return
     */
    public IHistoryItem getPrevious();

    /**
     * current item
     *
     * @return
     */
    public IHistoryItem getCurrent();

    /**
     * go back
     *
     * @return
     */
    public boolean goBack();
}
