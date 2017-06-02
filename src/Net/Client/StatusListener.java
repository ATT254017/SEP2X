package Net.Client;

import Net.Client.ServerResponse;

import java.util.EventListener;

/**
 * Created by Afonso on 5/23/2017.
 */
public interface StatusListener {
    void statusChangeEvent(ServerResponse sender);
}
