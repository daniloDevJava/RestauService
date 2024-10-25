package com.projet.foodGo.service.impl;

import com.projet.foodGo.dto.ClientDto;
import com.projet.foodGo.mapper.ClientConverter;
import com.projet.foodGo.model.Client;
import com.projet.foodGo.repository.ClientRepository;
import com.projet.foodGo.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientConverter clientConverter;
    private final ClientRepository clientRepository;
    /**
     * @param clientDto 
     * @return the client
     */
    @Override
    public ClientDto createClient(ClientDto clientDto) {
        Client client= clientRepository.save(clientConverter.toEntity(clientDto));
        return clientConverter.toDto(client);
    }

    /**
     * @return the client
     */
    @Override
    public List<ClientDto> getClients() {
        List<Client> listClient=clientRepository.findByDeleteAtIsNull();
        return listClient.stream()
                .map(clientConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * @param id of client
     * @return the found client
     */
    @Override
    public ClientDto getClient(UUID id) {
        Optional<Client> optionalClient=clientRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalClient.isPresent()){
            Client client= optionalClient.get();
            return clientConverter.toDto(client);
        }
        else
            return null;
    }

    /**
     * @param nom et prenom
     * @return the found client
     */
    @Override
    public ClientDto getClient(String nom) {
        Optional<Client> optionalClient=clientRepository.findByNomPrenom(nom);
        if(optionalClient.isPresent()){
            Client client= optionalClient.get();
            return clientConverter.toDto(client);
        }
        else
            return null;
    }

    /**
     * @param clientDto
     * @param client_id
     * @return
     */
    @Override
    public ClientDto updateClient(ClientDto clientDto, UUID client_id) {
        Optional<Client> optionalClient=clientRepository.findByIdAndDeleteAtIsNull(client_id);
        if(optionalClient.isPresent()){
            Client client= optionalClient.get();
            client.setAdresse(clientDto.getAdresse());
            client.setNomPrenom(clientDto.getNom());
            client.setMotDePasse(clientDto.getMotDePasse());
            client.setNumeroCNI(clientDto.getNumeroCNI());
            client.setAdresseMail(clientDto.getAdresseMail());
            client.setDateOfBirth(clientDto.getDateOfBirth());
            return clientConverter.toDto(clientRepository.save(client));
        }
        else
            return null;
    }

    /**
     * @param client_id 
     * @param clientDto
     * @return
     */
    @Override
    public ClientDto updateAdresseMail(UUID client_id, ClientDto clientDto) {
        Optional<Client> optionalClient=clientRepository.findByIdAndDeleteAtIsNull(client_id);
        if(optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setAdresseMail(clientDto.getAdresseMail());
            return clientConverter.toDto(clientRepository.save(client));
        }
        else
            return null;
    }

    /**
     * @param client_id 
     * @param clientDto
     * @return
     */
    @Override
    public ClientDto updateAdresse(UUID client_id, ClientDto clientDto) {
        Optional<Client> optionalClient=clientRepository.findByIdAndDeleteAtIsNull(client_id);
        if(optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setAdresse(clientDto.getAdresse());
            return clientConverter.toDto(clientRepository.save(client));
        }
        else
            return null;
    }

    /**
     * @param client_id of client
     * @param clientDto object
     * @return new cliendto
     */
    @Override
    public ClientDto updateDateOfBirth(UUID client_id, ClientDto clientDto) {
        Optional<Client> optionalClient=clientRepository.findByIdAndDeleteAtIsNull(client_id);
        if(optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setDateOfBirth(clientDto.getDateOfBirth());
            return clientConverter.toDto(clientRepository.save(client));
        }
        else
            return null;
    }

    /**
     * @param id of client
     * @return
     */
    @Override
    public boolean deleteClient(UUID id) {
        Optional<Client> optionalClient=clientRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setDeleteAt(LocalDateTime.now());
            clientRepository.save(client);
            return true;
        }
        else
            return false;
    }
}
