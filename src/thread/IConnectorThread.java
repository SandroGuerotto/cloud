package thread;


public interface IConnectorThread {

    void onWorkStart();

    void onWorkEnd();

    void onWorkError(Exception e);
}
