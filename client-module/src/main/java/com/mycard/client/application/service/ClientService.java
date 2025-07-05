package com.mycard.client.application.service;

import com.mycard.client.application.command.RegisterClientCommand;
import com.mycard.client.application.event.IClientEventPublisher;
import com.mycard.client.domain.event.ClientRegisteredEvent;
import com.mycard.client.domain.model.Client;
import com.mycard.client.domain.model.ClientProfile;
import com.mycard.client.infrastructure.messaging.ClientEventPublisher;
import com.mycard.client.infrastructure.persistence.mapper.ClientMapper;
import com.mycard.client.infrastructure.persistence.ClientRepository;
import com.mycard.client.infrastructure.persistence.EmbeddedClientProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ClientService {

    private static final Logger LOG = Logger.getLogger(ClientService.class);

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final IClientEventPublisher eventPublisher;

    @Inject
    public ClientService(ClientRepository repository, ClientMapper mapper, ClientEventPublisher eventPublisher) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    public UUID registerClient(RegisterClientCommand command) {
        LOG.infof("Registering new client: %s", command.getEmail());

        ClientProfile profile = new ClientProfile(
                command.getAge(),
                command.getHeightCm(),
                command.getWeightKg(),
                command.getGoalDescription()
        );

        Client client = new Client(
                UUID.randomUUID(),
                command.getFullName(),
                command.getEmail(),
                command.getTrainerId(),
                profile
        );

        repository.persist(mapper.toEntity(client));

        ClientRegisteredEvent event = new ClientRegisteredEvent(client.getId(), client.getTrainerId());
        eventPublisher.publish(event);

        LOG.infof("Client registered and event published: %s", client.getId());

        return client.getId();
    }

    public Optional<Client> findById(UUID id) {
        return repository.findByIdOptional(id).map(mapper::toDomain);
    }

    public List<Client> findByTrainerId(UUID trainerId) {
        return repository.find("trainerId", trainerId).list()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    public boolean updateClient(UUID id, RegisterClientCommand command) {
        return repository.findByIdOptional(id).map(existing -> {
            existing.setFullName(command.getFullName());
            existing.setEmail(command.getEmail());
            existing.setTrainerId(command.getTrainerId());

            var profile = new EmbeddedClientProfile();
            profile.setAge(command.getAge());
            profile.setHeightCm(command.getHeightCm());
            profile.setWeightKg(command.getWeightKg());
            profile.setGoalDescription(command.getGoalDescription());
            existing.setProfile(profile);

            return true;
        }).orElse(false);
    }

    public boolean deleteClient(UUID id) {
        return repository.deleteById(id);
    }
}