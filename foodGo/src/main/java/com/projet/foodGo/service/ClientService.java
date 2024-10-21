package com.projet.foodGo.service;

import com.projet.foodGo.dto.ClientDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ClientService {
    ClientDto createClient(ClientDto clientDto);
    List<ClientDto> getClients();
    ClientDto getClient(UUID id);
    ClientDto getClient(String nom);
    ClientDto updateClient(ClientDto clientDto);
    boolean deleteClient(UUID id);
}
