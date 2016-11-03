package thread;

import exception.CloudException;

public interface IConnectorThread {

    void onWorkStart();

    void onWorkEnd();

    void onWorkError(CloudException e);
}
