package sk.c.urbar.scene.history;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by coon on 12/25/13.
 */
public class ContentHistory implements IContentHistory {


    protected ReentrantLock changeLock = new ReentrantLock();
    protected IHistoryItem previous;
    protected IHistoryItem current;

    @Override
    public IHistoryItem getPrevious() {
        return previous;
    }

    @Override
    public IHistoryItem getCurrent() {
        return current;
    }

    public void setCurrent(IHistoryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Missing required value");
        }
        changeLock.lock();
        try {
            previous = current;
            current = item;
            fireCurrent();

        } finally {
            changeLock.unlock();
        }
    }

    @Override
    public boolean goBack() {

        boolean retVal = false;

        changeLock.lock();

        try {

            if (previous != null) {
                current = previous;

                previous = null;

                fireGoBack();

                retVal = true;
            }


        } finally {
            changeLock.unlock();
        }

        return retVal;
    }

    public void fireCurrent() {

    }

    public void fireGoBack() {

    }
}
