package bus;
import bus.messages.Message;

/**
 * IMessageListener
 *
 * An interface for any message listeners.
 * All message listeners will have the ability to receive a Message m.
 *
 * @Author Sara Cagle
 * @Date 9/14/2016
 */
public interface IMessageListener {
    void receiveMessage(Message m);
}
