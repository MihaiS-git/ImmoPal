package ubb.graduation24.immopal.person_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ubb.graduation24.immopal.person_service.domain.Person;
import ubb.graduation24.immopal.person_service.repository.PersonRepository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMQReceiver {

    private final PersonRepository personRepository;

    @RabbitListener(queues = "person.new.appointment.queue", concurrency = "1")
    public void receivePersonNewAppointmentNotification(String message) {
        log.info("Received appointment notification: {}", message);
        String[] parts = message.split(":");
        Long personId = Long.parseLong(parts[0]);
        log.info("Received appointment notification personId: {}", personId);
        Long appointmentId = Long.parseLong(parts[1]);
        log.info("Received appointment notification appointmentId: {}", appointmentId);
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isEmpty()) {
            log.error("Person not found: {}", personId);
            return;
        }
        Person person = personOptional.get();

        if (person.getAppointmentIds().contains(appointmentId)) {
            log.info("Appointment Id {} already exists for person with Id: {}", appointmentId, person.getId());
            return;
        }

        person.getAppointmentIds().add(appointmentId);
        personRepository.save(person);
        log.info("Appointment Id {} added to the person with Id: {}", appointmentId, person.getId());
    }

    @RabbitListener(queues = "person.delete.appointment.queue")
    public void receivePersonDeleteAppointmentNotification(String message) {
        log.info("Received delete appointment notification: {}", message);
        String[] parts = message.split(":");
        Long personId = Long.parseLong(parts[0]);
        log.info("Received delete appointment notification personId: {}", personId);
        Long appointmentId = Long.parseLong(parts[1]);
        log.info("Received delete appointment notification appointmentId: {}", appointmentId);
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isEmpty()) {
            log.error("Person not found: {}", personId);
            return;
        }
        Person person = personOptional.get();

        person.getAppointmentIds().remove(appointmentId);
        personRepository.save(person);
        log.info("Appointment Id {} deleted from the person with Id: {}", appointmentId, person.getId());
    }

    @RabbitListener(queues = "person.bid.queue")
    public void receiveNewBidNotification(String message) {
        log.info("Received new bid notification: {}", message);
        String[] parts = message.split(":");
        Long personId = Long.parseLong(parts[0]);
        String bidId = parts[1];
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isEmpty()) {
            log.error("Person not found: {}", personId);
            return;
        }
        Person person = personOptional.get();
        person.getBidIds().add(bidId);
        personRepository.save(person);
        log.info("Bid Id {} added to the person with Id: {}", bidId, person.getId());
    }
}
