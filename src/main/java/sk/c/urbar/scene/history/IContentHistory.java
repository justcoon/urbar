package sk.c.urbar.scene.history;

/**
 * Created by coon on 12/25/13.
 */
public interface IContentHistory {

    public IHistoryItem getPrevious();

    public IHistoryItem getCurrent();

    public boolean goBack();
}
