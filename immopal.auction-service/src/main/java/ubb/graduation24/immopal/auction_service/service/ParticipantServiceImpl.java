package ubb.graduation24.immopal.auction_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.auction_service.domain.Participant;
import ubb.graduation24.immopal.auction_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.auction_service.repository.ParticipantRepository;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements IParticipantService {

    private final ParticipantRepository participantRepository;

    @Override
    public Participant getParticipantByEmail(String email) {
        log.info("getParticipantByEmail --- method entered");
        Participant participant = participantRepository.findByEmail(email);
        if (participant == null) {
            throw new ResourceNotFoundException("Participant not found");
        }
        return participant;
    }

    @Override
    public Participant getParticipantById(String participantId) {
        log.info("getParticipantById --- method entered");
        Optional<Participant> participantOptional = participantRepository.findById(participantId);
        if(participantOptional.isEmpty()){
            throw new ResourceNotFoundException("Participant not found");
        }
        return participantOptional.get();
    }
}
