package ubb.graduation24.immopal.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: agency.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AgencyServiceRPCGrpc {

  private AgencyServiceRPCGrpc() {}

  public static final java.lang.String SERVICE_NAME = "ubb.graduation24.immopal.grpc.AgencyServiceRPC";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesRequest,
      ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesResponse> getGetAllAgenciesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllAgencies",
      requestType = ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesRequest.class,
      responseType = ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesRequest,
      ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesResponse> getGetAllAgenciesMethod() {
    io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesRequest, ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesResponse> getGetAllAgenciesMethod;
    if ((getGetAllAgenciesMethod = AgencyServiceRPCGrpc.getGetAllAgenciesMethod) == null) {
      synchronized (AgencyServiceRPCGrpc.class) {
        if ((getGetAllAgenciesMethod = AgencyServiceRPCGrpc.getGetAllAgenciesMethod) == null) {
          AgencyServiceRPCGrpc.getGetAllAgenciesMethod = getGetAllAgenciesMethod =
              io.grpc.MethodDescriptor.<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesRequest, ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllAgencies"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AgencyServiceRPCMethodDescriptorSupplier("getAllAgencies"))
              .build();
        }
      }
    }
    return getGetAllAgenciesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyRequest,
      ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyResponse> getGetAgencyByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAgencyById",
      requestType = ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyRequest.class,
      responseType = ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyRequest,
      ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyResponse> getGetAgencyByIdMethod() {
    io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyRequest, ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyResponse> getGetAgencyByIdMethod;
    if ((getGetAgencyByIdMethod = AgencyServiceRPCGrpc.getGetAgencyByIdMethod) == null) {
      synchronized (AgencyServiceRPCGrpc.class) {
        if ((getGetAgencyByIdMethod = AgencyServiceRPCGrpc.getGetAgencyByIdMethod) == null) {
          AgencyServiceRPCGrpc.getGetAgencyByIdMethod = getGetAgencyByIdMethod =
              io.grpc.MethodDescriptor.<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyRequest, ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAgencyById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AgencyServiceRPCMethodDescriptorSupplier("getAgencyById"))
              .build();
        }
      }
    }
    return getGetAgencyByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdRequest,
      ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdResponse> getGetAgencyByAgentIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAgencyByAgentId",
      requestType = ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdRequest.class,
      responseType = ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdRequest,
      ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdResponse> getGetAgencyByAgentIdMethod() {
    io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdRequest, ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdResponse> getGetAgencyByAgentIdMethod;
    if ((getGetAgencyByAgentIdMethod = AgencyServiceRPCGrpc.getGetAgencyByAgentIdMethod) == null) {
      synchronized (AgencyServiceRPCGrpc.class) {
        if ((getGetAgencyByAgentIdMethod = AgencyServiceRPCGrpc.getGetAgencyByAgentIdMethod) == null) {
          AgencyServiceRPCGrpc.getGetAgencyByAgentIdMethod = getGetAgencyByAgentIdMethod =
              io.grpc.MethodDescriptor.<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdRequest, ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAgencyByAgentId"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AgencyServiceRPCMethodDescriptorSupplier("getAgencyByAgentId"))
              .build();
        }
      }
    }
    return getGetAgencyByAgentIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdRequest,
      ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdResponse> getGetAgencyByPropertyIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAgencyByPropertyId",
      requestType = ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdRequest.class,
      responseType = ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdRequest,
      ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdResponse> getGetAgencyByPropertyIdMethod() {
    io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdRequest, ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdResponse> getGetAgencyByPropertyIdMethod;
    if ((getGetAgencyByPropertyIdMethod = AgencyServiceRPCGrpc.getGetAgencyByPropertyIdMethod) == null) {
      synchronized (AgencyServiceRPCGrpc.class) {
        if ((getGetAgencyByPropertyIdMethod = AgencyServiceRPCGrpc.getGetAgencyByPropertyIdMethod) == null) {
          AgencyServiceRPCGrpc.getGetAgencyByPropertyIdMethod = getGetAgencyByPropertyIdMethod =
              io.grpc.MethodDescriptor.<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdRequest, ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAgencyByPropertyId"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AgencyServiceRPCMethodDescriptorSupplier("getAgencyByPropertyId"))
              .build();
        }
      }
    }
    return getGetAgencyByPropertyIdMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AgencyServiceRPCStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AgencyServiceRPCStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AgencyServiceRPCStub>() {
        @java.lang.Override
        public AgencyServiceRPCStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AgencyServiceRPCStub(channel, callOptions);
        }
      };
    return AgencyServiceRPCStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AgencyServiceRPCBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AgencyServiceRPCBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AgencyServiceRPCBlockingStub>() {
        @java.lang.Override
        public AgencyServiceRPCBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AgencyServiceRPCBlockingStub(channel, callOptions);
        }
      };
    return AgencyServiceRPCBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AgencyServiceRPCFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AgencyServiceRPCFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AgencyServiceRPCFutureStub>() {
        @java.lang.Override
        public AgencyServiceRPCFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AgencyServiceRPCFutureStub(channel, callOptions);
        }
      };
    return AgencyServiceRPCFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getAllAgencies(ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllAgenciesMethod(), responseObserver);
    }

    /**
     */
    default void getAgencyById(ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAgencyByIdMethod(), responseObserver);
    }

    /**
     */
    default void getAgencyByAgentId(ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAgencyByAgentIdMethod(), responseObserver);
    }

    /**
     */
    default void getAgencyByPropertyId(ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAgencyByPropertyIdMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AgencyServiceRPC.
   */
  public static abstract class AgencyServiceRPCImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AgencyServiceRPCGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AgencyServiceRPC.
   */
  public static final class AgencyServiceRPCStub
      extends io.grpc.stub.AbstractAsyncStub<AgencyServiceRPCStub> {
    private AgencyServiceRPCStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AgencyServiceRPCStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AgencyServiceRPCStub(channel, callOptions);
    }

    /**
     */
    public void getAllAgencies(ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllAgenciesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAgencyById(ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAgencyByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAgencyByAgentId(ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAgencyByAgentIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAgencyByPropertyId(ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAgencyByPropertyIdMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AgencyServiceRPC.
   */
  public static final class AgencyServiceRPCBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AgencyServiceRPCBlockingStub> {
    private AgencyServiceRPCBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AgencyServiceRPCBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AgencyServiceRPCBlockingStub(channel, callOptions);
    }

    /**
     */
    public ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesResponse getAllAgencies(ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllAgenciesMethod(), getCallOptions(), request);
    }

    /**
     */
    public ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyResponse getAgencyById(ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAgencyByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdResponse getAgencyByAgentId(ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAgencyByAgentIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdResponse getAgencyByPropertyId(ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAgencyByPropertyIdMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AgencyServiceRPC.
   */
  public static final class AgencyServiceRPCFutureStub
      extends io.grpc.stub.AbstractFutureStub<AgencyServiceRPCFutureStub> {
    private AgencyServiceRPCFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AgencyServiceRPCFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AgencyServiceRPCFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesResponse> getAllAgencies(
        ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllAgenciesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyResponse> getAgencyById(
        ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAgencyByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdResponse> getAgencyByAgentId(
        ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAgencyByAgentIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdResponse> getAgencyByPropertyId(
        ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAgencyByPropertyIdMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_AGENCIES = 0;
  private static final int METHODID_GET_AGENCY_BY_ID = 1;
  private static final int METHODID_GET_AGENCY_BY_AGENT_ID = 2;
  private static final int METHODID_GET_AGENCY_BY_PROPERTY_ID = 3;

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
        case METHODID_GET_ALL_AGENCIES:
          serviceImpl.getAllAgencies((ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesRequest) request,
              (io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesResponse>) responseObserver);
          break;
        case METHODID_GET_AGENCY_BY_ID:
          serviceImpl.getAgencyById((ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyRequest) request,
              (io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyResponse>) responseObserver);
          break;
        case METHODID_GET_AGENCY_BY_AGENT_ID:
          serviceImpl.getAgencyByAgentId((ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdRequest) request,
              (io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdResponse>) responseObserver);
          break;
        case METHODID_GET_AGENCY_BY_PROPERTY_ID:
          serviceImpl.getAgencyByPropertyId((ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdRequest) request,
              (io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdResponse>) responseObserver);
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
          getGetAllAgenciesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesRequest,
              ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgenciesResponse>(
                service, METHODID_GET_ALL_AGENCIES)))
        .addMethod(
          getGetAgencyByIdMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyRequest,
              ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyResponse>(
                service, METHODID_GET_AGENCY_BY_ID)))
        .addMethod(
          getGetAgencyByAgentIdMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdRequest,
              ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByAgentIdResponse>(
                service, METHODID_GET_AGENCY_BY_AGENT_ID)))
        .addMethod(
          getGetAgencyByPropertyIdMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdRequest,
              ubb.graduation24.immopal.grpc.AgencyOuterClass.GetAgencyByPropertyIdResponse>(
                service, METHODID_GET_AGENCY_BY_PROPERTY_ID)))
        .build();
  }

  private static abstract class AgencyServiceRPCBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AgencyServiceRPCBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ubb.graduation24.immopal.grpc.AgencyOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AgencyServiceRPC");
    }
  }

  private static final class AgencyServiceRPCFileDescriptorSupplier
      extends AgencyServiceRPCBaseDescriptorSupplier {
    AgencyServiceRPCFileDescriptorSupplier() {}
  }

  private static final class AgencyServiceRPCMethodDescriptorSupplier
      extends AgencyServiceRPCBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AgencyServiceRPCMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AgencyServiceRPCGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AgencyServiceRPCFileDescriptorSupplier())
              .addMethod(getGetAllAgenciesMethod())
              .addMethod(getGetAgencyByIdMethod())
              .addMethod(getGetAgencyByAgentIdMethod())
              .addMethod(getGetAgencyByPropertyIdMethod())
              .build();
        }
      }
    }
    return result;
  }
}
