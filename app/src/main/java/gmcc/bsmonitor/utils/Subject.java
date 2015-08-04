package gmcc.bsmonitor.utils;

/**
 * Created by lijun on 15/7/29.
 */
public interface Subject {
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers();
}
