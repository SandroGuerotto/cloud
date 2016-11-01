package thread;


public interface IWorkThread {

    void onWorkStart(String msg, int size, int current);

    void onWorkEnd(String msg, int size);

    void onWorkError(Exception e);
}
