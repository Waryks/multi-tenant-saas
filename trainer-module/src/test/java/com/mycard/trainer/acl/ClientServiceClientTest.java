package com.mycard.trainer.acl;

import com.mycard.trainer.acl.dto.ClientDTO;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@QuarkusTestResource(ClientServiceMockResource.class)
class ClientServiceClientTest {

    @Inject
    @RestClient
    ClientServiceClient clientServiceClient;

    @Test
    void shouldFetchClientsForTrainer() {
        UUID trainerId = UUID.fromString("00000000-0000-0000-0000-000000000123");

        List<ClientDTO> result = clientServiceClient.getClientsByTrainer(trainerId);

        assertThat(result, is(not(empty())));
        assertThat(result.get(0).getEmail(), is("client1@example.com"));
    }
}
