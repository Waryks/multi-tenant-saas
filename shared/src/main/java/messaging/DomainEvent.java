package messaging;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredAt();
    String eventName();
}
