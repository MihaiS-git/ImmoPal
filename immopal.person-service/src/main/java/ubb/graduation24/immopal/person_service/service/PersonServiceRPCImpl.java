package ubb.graduation24.immopal.person_service.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ubb.graduation24.immopal.person_service.domain.Person;
import ubb.graduation24.immopal.person_service.repository.PersonRepository;
import ubb.graduation24.immopal.grpc.PersonServiceRPCGrpc;
import ubb.graduation24.immopal.grpc.PersonOuterClass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@GRpcService
public class PersonServiceRPCImpl extends PersonServiceRPCGrpc.PersonServiceRPCImplBase {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void getPersons(
            PersonOuterClass.GetPersonsRequest request,
            StreamObserver<PersonOuterClass.GetPersonsResponse> responseObserver
    ) {
        List<Person> persons = personRepository.findAll();

        List<PersonOuterClass.Person> grpcPersons = persons.stream()
                .map(this::convertToGrpcPerson)
                .collect(Collectors.toList());

        PersonOuterClass.GetPersonsResponse response = PersonOuterClass.GetPersonsResponse.newBuilder()
                .addAllPerson(grpcPersons)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private PersonOuterClass.Person convertToGrpcPerson(Person person) {
        return PersonOuterClass.Person.newBuilder()
                .setId(person.getId())
                .setFirstName(person.getFirstName())
                .setLastName(person.getLastName())
                .setPhoneNumber(person.getPhoneNumber())
                .setDateOfBirth(person.getDateOfBirth().toString())
                .setAddress(person.getAddress())
                .setPictureUrl(person.getPictureUrl())
                .setUserId(person.getUserId())
                .addAllPropertyIds(person.getPropertyIds())
                .addAllAppointmentIds(person.getAppointmentIds())
                .addAllBidIds(person.getBidIds())
                .build();
    }

    @Override
    public void getPerson(
            PersonOuterClass.GetPersonRequest request,
            StreamObserver<PersonOuterClass.GetPersonResponse> responseObserver
    ) {
        Long personId = request.getPersonId();
        Optional<Person> personOptional = personRepository.findById(personId);

        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            PersonOuterClass.Person grpcPerson = convertToGrpcPerson(person);
            PersonOuterClass.GetPersonResponse response = PersonOuterClass.GetPersonResponse.newBuilder()
                    .setPerson(grpcPerson)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(
                    new StatusRuntimeException(
                            Status.NOT_FOUND.withDescription("Person with id " + personId + " not found")
                    )
            );
        }
    }

    @Override
    public void addPerson(
            PersonOuterClass.AddPersonRequest request,
            StreamObserver<PersonOuterClass.AddPersonResponse> responseObserver
    ) {
        log.debug("addPerson() -- method entered");
        String firstName = request.getPerson().getFirstName();
        String lastName = request.getPerson().getLastName();
        String phoneNumber = request.getPerson().getPhoneNumber();
        String dateOfBirth = request.getPerson().getDateOfBirth();
        String address = request.getPerson().getAddress();
        String pictureUrl = request.getPerson().getPictureUrl();
        Long userId = request.getPerson().getUserId();

        Person person = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .dateOfBirth(LocalDate.parse(dateOfBirth))
                .address(address)
                .pictureUrl(pictureUrl)
                .userId(userId)
                .propertyIds(new ArrayList<>())
                .appointmentIds(new ArrayList<>())
                .bidIds(new ArrayList<>())
                .build();

        Person savedPerson = personRepository.save(person);

        PersonOuterClass.Person grpcPerson = PersonOuterClass.Person.newBuilder()
                .setId(savedPerson.getId())
                .setFirstName(savedPerson.getFirstName())
                .setLastName(savedPerson.getLastName())
                .setPhoneNumber(savedPerson.getPhoneNumber())
                .setDateOfBirth(savedPerson.getDateOfBirth().toString())
                .setAddress(savedPerson.getAddress())
                .setPictureUrl(savedPerson.getPictureUrl())
                .setUserId(savedPerson.getUserId())
                .build();

        PersonOuterClass.AddPersonResponse response = PersonOuterClass.AddPersonResponse.newBuilder()
                .setPerson(grpcPerson)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updatePerson(
            PersonOuterClass.UpdatePersonRequest request,
            StreamObserver<PersonOuterClass.UpdatePersonResponse> responseObserver
    ) {
        log.debug("updatePerson() -- method entered");
        PersonOuterClass.Person updatedPersonProto = request.getPerson();
        Long id = updatedPersonProto.getId();

        Optional<Person> personOptional = personRepository.findById(id);

        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            person.setFirstName(updatedPersonProto.getFirstName());
            person.setLastName(updatedPersonProto.getLastName());
            person.setPhoneNumber(updatedPersonProto.getPhoneNumber());
            person.setDateOfBirth(LocalDate.parse(updatedPersonProto.getDateOfBirth()));
            person.setAddress(updatedPersonProto.getAddress());
            person.setPictureUrl(updatedPersonProto.getPictureUrl() != null ? updatedPersonProto.getPictureUrl() : person.getPictureUrl());
            person.setUserId(updatedPersonProto.getUserId());

            person.setPropertyIds(updatedPersonProto.getPropertyIdsList());
            person.setAppointmentIds(updatedPersonProto.getAppointmentIdsList());
            person.setBidIds(updatedPersonProto.getBidIdsList());

            Person updatedPerson = personRepository.save(person);

            PersonOuterClass.Person grpcPerson = PersonOuterClass.Person.newBuilder()
                    .setId(updatedPerson.getId())
                    .setFirstName(updatedPerson.getFirstName())
                    .setLastName(updatedPerson.getLastName())
                    .setPhoneNumber(updatedPerson.getPhoneNumber())
                    .setDateOfBirth(updatedPerson.getDateOfBirth().toString())
                    .setAddress(updatedPerson.getAddress())
                    .setPictureUrl(updatedPerson.getPictureUrl())
                    .setUserId(updatedPerson.getUserId())
                    .addAllPropertyIds(updatedPerson.getPropertyIds())
                    .addAllAppointmentIds(updatedPerson.getAppointmentIds())
                    .addAllBidIds(updatedPerson.getBidIds())
                    .build();

            PersonOuterClass.UpdatePersonResponse response = PersonOuterClass.UpdatePersonResponse.newBuilder()
                    .setPerson(grpcPerson)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(
                    new StatusRuntimeException(
                            Status.NOT_FOUND.withDescription("Person with id " + id + " not found")
                    )
            );
        }
    }

    @Override
    public void deletePersonById(
            PersonOuterClass.DeletePersonRequest request,
            StreamObserver<PersonOuterClass.DeletePersonResponse> responseObserver
    ) {
        Long personId = request.getId();
        Optional<Person> personOptional = personRepository.findById(personId);

        if (personOptional.isPresent()) {
            personRepository.delete(personOptional.get());
            PersonOuterClass.DeletePersonResponse response = PersonOuterClass.DeletePersonResponse.newBuilder()
                    .setMessage("Person deleted successfully")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(
                    new StatusRuntimeException(
                            Status.NOT_FOUND.withDescription("Person with id " + personId + " not found")
                    )
            );
        }
    }
}