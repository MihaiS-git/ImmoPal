package ubb.graduation24.immopal.auction_service.service;

import ubb.graduation24.immopal.auction_service.domain.Participant;


public interface IParticipantService {

    Participant getParticipantByEmail(String email);

    Participant getParticipantById(String participantId);

}

