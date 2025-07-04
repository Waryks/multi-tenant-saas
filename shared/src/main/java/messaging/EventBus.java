package messaging;

public interface EventBus {
    <T extends DomainEvent> void publish(String topic, T event);
}