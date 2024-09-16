package ubb.graduation24.immopal.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: person.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PersonServiceRPCGrpc {

  private PersonServiceRPCGrpc() {}

  public static final java.lang.String SERVICE_NAME = "ubb.graduation24.immopal.grpc.PersonServiceRPC";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsRequest,
      ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsResponse> getGetPersonsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getPersons",
      requestType = ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsRequest.class,
      responseType = ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsRequest,
      ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsResponse> getGetPersonsMethod() {
    io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsRequest, ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsResponse> getGetPersonsMethod;
    if ((getGetPersonsMethod = PersonServiceRPCGrpc.getGetPersonsMethod) == null) {
      synchronized (PersonServiceRPCGrpc.class) {
        if ((getGetPersonsMethod = PersonServiceRPCGrpc.getGetPersonsMethod) == null) {
          PersonServiceRPCGrpc.getGetPersonsMethod = getGetPersonsMethod =
              io.grpc.MethodDescriptor.<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsRequest, ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getPersons"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PersonServiceRPCMethodDescriptorSupplier("getPersons"))
              .build();
        }
      }
    }
    return getGetPersonsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonRequest,
      ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonResponse> getGetPersonMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getPerson",
      requestType = ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonRequest.class,
      responseType = ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonRequest,
      ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonResponse> getGetPersonMethod() {
    io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonRequest, ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonResponse> getGetPersonMethod;
    if ((getGetPersonMethod = PersonServiceRPCGrpc.getGetPersonMethod) == null) {
      synchronized (PersonServiceRPCGrpc.class) {
        if ((getGetPersonMethod = PersonServiceRPCGrpc.getGetPersonMethod) == null) {
          PersonServiceRPCGrpc.getGetPersonMethod = getGetPersonMethod =
              io.grpc.MethodDescriptor.<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonRequest, ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getPerson"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PersonServiceRPCMethodDescriptorSupplier("getPerson"))
              .build();
        }
      }
    }
    return getGetPersonMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonRequest,
      ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonResponse> getAddPersonMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addPerson",
      requestType = ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonRequest.class,
      responseType = ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonRequest,
      ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonResponse> getAddPersonMethod() {
    io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonRequest, ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonResponse> getAddPersonMethod;
    if ((getAddPersonMethod = PersonServiceRPCGrpc.getAddPersonMethod) == null) {
      synchronized (PersonServiceRPCGrpc.class) {
        if ((getAddPersonMethod = PersonServiceRPCGrpc.getAddPersonMethod) == null) {
          PersonServiceRPCGrpc.getAddPersonMethod = getAddPersonMethod =
              io.grpc.MethodDescriptor.<ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonRequest, ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "addPerson"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PersonServiceRPCMethodDescriptorSupplier("addPerson"))
              .build();
        }
      }
    }
    return getAddPersonMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonRequest,
      ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonResponse> getUpdatePersonMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updatePerson",
      requestType = ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonRequest.class,
      responseType = ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonRequest,
      ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonResponse> getUpdatePersonMethod() {
    io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonRequest, ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonResponse> getUpdatePersonMethod;
    if ((getUpdatePersonMethod = PersonServiceRPCGrpc.getUpdatePersonMethod) == null) {
      synchronized (PersonServiceRPCGrpc.class) {
        if ((getUpdatePersonMethod = PersonServiceRPCGrpc.getUpdatePersonMethod) == null) {
          PersonServiceRPCGrpc.getUpdatePersonMethod = getUpdatePersonMethod =
              io.grpc.MethodDescriptor.<ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonRequest, ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updatePerson"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PersonServiceRPCMethodDescriptorSupplier("updatePerson"))
              .build();
        }
      }
    }
    return getUpdatePersonMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonRequest,
      ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonResponse> getDeletePersonByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deletePersonById",
      requestType = ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonRequest.class,
      responseType = ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonRequest,
      ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonResponse> getDeletePersonByIdMethod() {
    io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonRequest, ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonResponse> getDeletePersonByIdMethod;
    if ((getDeletePersonByIdMethod = PersonServiceRPCGrpc.getDeletePersonByIdMethod) == null) {
      synchronized (PersonServiceRPCGrpc.class) {
        if ((getDeletePersonByIdMethod = PersonServiceRPCGrpc.getDeletePersonByIdMethod) == null) {
          PersonServiceRPCGrpc.getDeletePersonByIdMethod = getDeletePersonByIdMethod =
              io.grpc.MethodDescriptor.<ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonRequest, ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "deletePersonById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PersonServiceRPCMethodDescriptorSupplier("deletePersonById"))
              .build();
        }
      }
    }
    return getDeletePersonByIdMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PersonServiceRPCStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PersonServiceRPCStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PersonServiceRPCStub>() {
        @java.lang.Override
        public PersonServiceRPCStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PersonServiceRPCStub(channel, callOptions);
        }
      };
    return PersonServiceRPCStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PersonServiceRPCBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PersonServiceRPCBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PersonServiceRPCBlockingStub>() {
        @java.lang.Override
        public PersonServiceRPCBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PersonServiceRPCBlockingStub(channel, callOptions);
        }
      };
    return PersonServiceRPCBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PersonServiceRPCFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PersonServiceRPCFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PersonServiceRPCFutureStub>() {
        @java.lang.Override
        public PersonServiceRPCFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PersonServiceRPCFutureStub(channel, callOptions);
        }
      };
    return PersonServiceRPCFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getPersons(ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPersonsMethod(), responseObserver);
    }

    /**
     */
    default void getPerson(ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPersonMethod(), responseObserver);
    }

    /**
     */
    default void addPerson(ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddPersonMethod(), responseObserver);
    }

    /**
     */
    default void updatePerson(ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdatePersonMethod(), responseObserver);
    }

    /**
     */
    default void deletePersonById(ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeletePersonByIdMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PersonServiceRPC.
   */
  public static abstract class PersonServiceRPCImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PersonServiceRPCGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PersonServiceRPC.
   */
  public static final class PersonServiceRPCStub
      extends io.grpc.stub.AbstractAsyncStub<PersonServiceRPCStub> {
    private PersonServiceRPCStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PersonServiceRPCStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PersonServiceRPCStub(channel, callOptions);
    }

    /**
     */
    public void getPersons(ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPersonsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getPerson(ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPersonMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addPerson(ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddPersonMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updatePerson(ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdatePersonMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deletePersonById(ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeletePersonByIdMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PersonServiceRPC.
   */
  public static final class PersonServiceRPCBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PersonServiceRPCBlockingStub> {
    private PersonServiceRPCBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PersonServiceRPCBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PersonServiceRPCBlockingStub(channel, callOptions);
    }

    /**
     */
    public ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsResponse getPersons(ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPersonsMethod(), getCallOptions(), request);
    }

    /**
     */
    public ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonResponse getPerson(ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPersonMethod(), getCallOptions(), request);
    }

    /**
     */
    public ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonResponse addPerson(ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddPersonMethod(), getCallOptions(), request);
    }

    /**
     */
    public ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonResponse updatePerson(ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdatePersonMethod(), getCallOptions(), request);
    }

    /**
     */
    public ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonResponse deletePersonById(ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeletePersonByIdMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PersonServiceRPC.
   */
  public static final class PersonServiceRPCFutureStub
      extends io.grpc.stub.AbstractFutureStub<PersonServiceRPCFutureStub> {
    private PersonServiceRPCFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PersonServiceRPCFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PersonServiceRPCFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsResponse> getPersons(
        ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPersonsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonResponse> getPerson(
        ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPersonMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonResponse> addPerson(
        ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddPersonMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonResponse> updatePerson(
        ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdatePersonMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonResponse> deletePersonById(
        ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeletePersonByIdMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PERSONS = 0;
  private static final int METHODID_GET_PERSON = 1;
  private static final int METHODID_ADD_PERSON = 2;
  private static final int METHODID_UPDATE_PERSON = 3;
  private static final int METHODID_DELETE_PERSON_BY_ID = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PERSONS:
          serviceImpl.getPersons((ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsRequest) request,
              (io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsResponse>) responseObserver);
          break;
        case METHODID_GET_PERSON:
          serviceImpl.getPerson((ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonRequest) request,
              (io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonResponse>) responseObserver);
          break;
        case METHODID_ADD_PERSON:
          serviceImpl.addPerson((ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonRequest) request,
              (io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonResponse>) responseObserver);
          break;
        case METHODID_UPDATE_PERSON:
          serviceImpl.updatePerson((ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonRequest) request,
              (io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonResponse>) responseObserver);
          break;
        case METHODID_DELETE_PERSON_BY_ID:
          serviceImpl.deletePersonById((ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonRequest) request,
              (io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetPersonsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsRequest,
              ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonsResponse>(
                service, METHODID_GET_PERSONS)))
        .addMethod(
          getGetPersonMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonRequest,
              ubb.graduation24.immopal.grpc.PersonOuterClass.GetPersonResponse>(
                service, METHODID_GET_PERSON)))
        .addMethod(
          getAddPersonMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonRequest,
              ubb.graduation24.immopal.grpc.PersonOuterClass.AddPersonResponse>(
                service, METHODID_ADD_PERSON)))
        .addMethod(
          getUpdatePersonMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonRequest,
              ubb.graduation24.immopal.grpc.PersonOuterClass.UpdatePersonResponse>(
                service, METHODID_UPDATE_PERSON)))
        .addMethod(
          getDeletePersonByIdMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonRequest,
              ubb.graduation24.immopal.grpc.PersonOuterClass.DeletePersonResponse>(
                service, METHODID_DELETE_PERSON_BY_ID)))
        .build();
  }

  private static abstract class PersonServiceRPCBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PersonServiceRPCBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ubb.graduation24.immopal.grpc.PersonOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PersonServiceRPC");
    }
  }

  private static final class PersonServiceRPCFileDescriptorSupplier
      extends PersonServiceRPCBaseDescriptorSupplier {
    PersonServiceRPCFileDescriptorSupplier() {}
  }

  private static final class PersonServiceRPCMethodDescriptorSupplier
      extends PersonServiceRPCBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PersonServiceRPCMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PersonServiceRPCGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PersonServiceRPCFileDescriptorSupplier())
              .addMethod(getGetPersonsMethod())
              .addMethod(getGetPersonMethod())
              .addMethod(getAddPersonMethod())
              .addMethod(getUpdatePersonMethod())
              .addMethod(getDeletePersonByIdMethod())
              .build();
        }
      }
    }
    return result;
  }
}
