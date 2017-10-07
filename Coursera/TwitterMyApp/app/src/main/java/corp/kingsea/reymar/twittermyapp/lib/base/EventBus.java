package corp.kingsea.reymar.twittermyapp.lib.base;

/**
 * Created by reyma on 2/07/2016.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
