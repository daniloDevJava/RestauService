package com.projet.foodGo.service;

import com.projet.foodGo.dto.ClientDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The interface Client service.
 */
@Service
public interface ClientService {
    /**
     * Create client client dto.
     *
     * @param clientDto the client dto
     * @return the client dto
     */
    ClientDto createClient(ClientDto clientDto);

    /**
     * Gets clients.
     *
     * @return the clients
     */
    List<ClientDto> getClients();

    /**
     * Gets client.
     *
     * @param id the id
     * @return the client
     */
    ClientDto getClient(UUID id);

    /**
     * Gets client.
     *
     * @param nom the nom
     * @return the client
     */
    ClientDto getClient(String nom);

    /**
     * Update client client dto.
     *
     * @param clientDto the client dto
     * @param client_id the client id
     * @return the client dto
     */
    ClientDto updateClient(ClientDto clientDto, UUID client_id);

    /**
     * Update adresse mail client dto.
     *
     * @param client_id the client id
     * @param clientDto the client dto
     * @return the client dto
     */
    ClientDto updateAdresseMail(UUID client_id,ClientDto clientDto);

    /**
     * Update adresse client dto.
     *
     * @param client_id the client id
     * @param clientDto the client dto
     * @return the client dto
     */
    ClientDto updateAdresse(UUID client_id,ClientDto clientDto);

    /**
     * Update date of birth client dto.
     *
     * @param client_id the client id
     * @param clientDto the client dto
     * @return the client dto
     */
    ClientDto updateDateOfBirth(UUID client_id,ClientDto clientDto);

    /**
     * Delete client boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteClient(UUID id);

}
