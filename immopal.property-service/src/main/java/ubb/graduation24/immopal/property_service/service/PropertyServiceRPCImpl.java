package ubb.graduation24.immopal.property_service.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ubb.graduation24.immopal.grpc.PropertyOuterClass;
import ubb.graduation24.immopal.grpc.PropertyServiceRPCGrpc;
import ubb.graduation24.immopal.property_service.domain.Address;
import ubb.graduation24.immopal.property_service.domain.Property;
import ubb.graduation24.immopal.property_service.domain.PropertyDetails;
import ubb.graduation24.immopal.property_service.domain.PropertyImages;
import ubb.graduation24.immopal.property_service.exception.ResourceNotFoundException;
import ubb.graduation24.immopal.property_service.repository.PropertyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@GRpcService
public class PropertyServiceRPCImpl extends PropertyServiceRPCGrpc.PropertyServiceRPCImplBase {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public void getAllProperties(
            PropertyOuterClass.GetPropertiesRequest request,
            StreamObserver<PropertyOuterClass.GetPropertiesResponse> responseObserver
    ) {
        List<Property> properties = propertyRepository.findAll();
        if (properties.isEmpty()) {
            responseObserver.onError(new ResourceNotFoundException("Property not found"));
        }

        List<PropertyOuterClass.Property> grpcProperties = properties.stream()
                .map(this::convertToGrpcProperty)
                .collect(Collectors.toList());

        PropertyOuterClass.GetPropertiesResponse response = PropertyOuterClass.GetPropertiesResponse.newBuilder()
                .addAllProperty(grpcProperties)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllPropertiesByAgentId(
            PropertyOuterClass.GetPropertiesByAgentIdRequest request,
            StreamObserver<PropertyOuterClass.GetPropertiesByAgentIdResponse> responseObserver
    ) {
        List<Property> properties = propertyRepository.findAll();
        if (properties.isEmpty()) {
            responseObserver.onError(new ResourceNotFoundException("Property not found"));
        }

        List<PropertyOuterClass.Property> grpcProperties = properties.stream()
                .filter(property -> property.getAgentId() == request.getAgentId())
                .map(this::convertToGrpcProperty)
                .collect(Collectors.toList());

        PropertyOuterClass.GetPropertiesByAgentIdResponse response = PropertyOuterClass.GetPropertiesByAgentIdResponse.newBuilder()
                .addAllProperty(grpcProperties)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPropertyById(
            PropertyOuterClass.GetPropertyRequest request,
            StreamObserver<PropertyOuterClass.GetPropertyResponse> responseObserver
    ) {
        try {
            Long propertyId = request.getPropertyId();
            Optional<Property> propertyOptional = propertyRepository.findByIdWithImages(propertyId);

            if (propertyOptional.isPresent()) {
                Property property = propertyOptional.get();
                PropertyOuterClass.Property grpcProperty = convertToGrpcProperty(property);

                PropertyOuterClass.GetPropertyResponse response = PropertyOuterClass.GetPropertyResponse.newBuilder()
                        .setProperty(grpcProperty)
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(Status.NOT_FOUND.withDescription("Property not found").asRuntimeException());
            }
        } catch (Exception e) {
            log.error("Failed to process request for property with ID {}: {}", request.getPropertyId(), e.getMessage());
            responseObserver.onError(Status.INTERNAL.withDescription("Failed to process request").asRuntimeException());
        }
    }

    private PropertyOuterClass.Property convertToGrpcProperty(Property property) {
        List<PropertyOuterClass.PropertyImage> propertyImages = property.getPropertyImages().stream()
                .map(this::convertToGrpcPropertyImage)
                .collect(Collectors.toList());

        return PropertyOuterClass.Property.newBuilder()
                .setId(property.getId())
                .setTransactionType(PropertyOuterClass.TransactionType.valueOf(property.getTransactionType().name()))
                .setPropertyCategory(PropertyOuterClass.PropertyCategory.valueOf(property.getPropertyCategory().name()))
                .setAddress(convertToGrpcAddress(property.getAddress()))
                .setPropertyDetails(convertToGrpcPropertyDetails(property.getPropertyDetails()))
                .setPrice(property.getPrice())
                .setAgentId(property.getAgentId())
                .addAllPropertyImages(propertyImages)
                .build();
    }

    private PropertyOuterClass.PropertyImage convertToGrpcPropertyImage(PropertyImages propertyImage) {
        return PropertyOuterClass.PropertyImage.newBuilder()
                .setId(propertyImage.getId())
                .setImageUrl(propertyImage.getImageUrl())
                .build();
    }

    private PropertyOuterClass.Address convertToGrpcAddress(Address address) {
        return PropertyOuterClass.Address.newBuilder()
                .setId(address.getId())
                .setCountry(address.getCountry())
                .setState(address.getState())
                .setCity(address.getCity())
                .setNeighborhood(address.getNeighborhood())
                .setStreet(address.getStreet())
                .setStreetNo(address.getStreetNo())
                .setLatitude(address.getLatitude())
                .setLongitude(address.getLongitude())
                .build();
    }

    private PropertyOuterClass.PropertyDetails convertToGrpcPropertyDetails(PropertyDetails propertyDetails) {
        return PropertyOuterClass.PropertyDetails.newBuilder()
                .setId(propertyDetails.getId())
                .setDescription(propertyDetails.getDescription())
                .setCarpetArea(propertyDetails.getCarpetArea())
                .setBuiltUpArea(propertyDetails.getBuiltUpArea())
                .setComfortType(PropertyOuterClass.ComfortType.valueOf(propertyDetails.getComfortType().name()))
                .setFloor(propertyDetails.getFloor())
                .setStructureType(PropertyOuterClass.StructureType.valueOf(propertyDetails.getStructureType().name()))
                .setYearOfConstruction(propertyDetails.getYearOfConstruction())
                .setBathNo(propertyDetails.getBathNo())
                .setKitchenNo(propertyDetails.getKitchenNo())
                .setBedroomNo(propertyDetails.getBedroomNo())
                .setParkingNo(propertyDetails.getParkingNo())
                .setBalcony(propertyDetails.isBalcony())
                .setTerrace(propertyDetails.isTerrace())
                .setSwimmingPool(propertyDetails.isSwimmingPool())
                .setEnergeticCertificate(PropertyOuterClass.EnergeticCertificate.valueOf(propertyDetails.getEnergeticCertificate().name()))
                .build();
    }

}
