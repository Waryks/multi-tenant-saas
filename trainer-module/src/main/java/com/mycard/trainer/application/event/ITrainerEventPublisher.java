package com.mycard.trainer.application.event;

import com.mycard.trainer.domain.event.TrainerCreatedEvent;


public interface ITrainerEventPublisher {
    void publish(TrainerCreatedEvent event);
}
