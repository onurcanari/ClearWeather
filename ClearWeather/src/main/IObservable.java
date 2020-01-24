package main;

public interface IObservable {

    void notifyListeners();
    void setState() throws Exception;
    void registerObserver(IObserver observer);
    void unregisterObserver(IObserver observer);
}
