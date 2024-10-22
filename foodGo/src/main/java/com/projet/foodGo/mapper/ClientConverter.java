package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.ClientDto;
import com.projet.foodGo.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter {
    public Client toEntity(ClientDto clientDto){
        Client client=new Client();
        client.setAdresseMail(clientDto.getAdresseMail());
        client.setAdresse(clientDto.getAdresse());
        client.setNumeroCNI(clientDto.getNumeroCNI());
        client.setDateOfBirth(clientDto.getDateOfBirth());
        client.setNomPrenom(clientDto.getNom()+" "+clientDto.getPrenom());
        return client;
    }

    public ClientDto toDto(Client client){
        ClientDto clientDto=new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setNom(client.getNomPrenom());
        clientDto.setAdresse(client.getAdresse());
        clientDto.setAdresseMail(client.getAdresseMail());
        clientDto.setDateOfBirth(client.getDateOfBirth());
        clientDto.setNumeroCNI(client.getNumeroCNI());
        clientDto.setCreateAt(client.getCreateAt());
        clientDto.setUpdateAt(client.getUpdateAt());
        return clientDto;
    }
}
