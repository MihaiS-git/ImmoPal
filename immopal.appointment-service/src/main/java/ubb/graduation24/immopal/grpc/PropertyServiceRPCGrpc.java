package ubb.graduation24.immopal.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: property.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PropertyServiceRPCGrpc {

  private PropertyServiceRPCGrpc() {}

  public static final java.lang.String SERVICE_NAME = "ubb.graduation24.immopal.grpc.PropertyServiceRPC";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesRequest,
      ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesResponse> getGetAllPropertiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllProperties",
      requestType = ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesRequest.class,
      responseType = ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesRequest,
      ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesResponse> getGetAllPropertiesMethod() {
    io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesRequest, ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesResponse> getGetAllPropertiesMethod;
    if ((getGetAllPropertiesMethod = PropertyServiceRPCGrpc.getGetAllPropertiesMethod) == null) {
      synchronized (PropertyServiceRPCGrpc.class) {
        if ((getGetAllPropertiesMethod = PropertyServiceRPCGrpc.getGetAllPropertiesMethod) == null) {
          PropertyServiceRPCGrpc.getGetAllPropertiesMethod = getGetAllPropertiesMethod =
              io.grpc.MethodDescriptor.<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesRequest, ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllProperties"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PropertyServiceRPCMethodDescriptorSupplier("getAllProperties"))
              .build();
        }
      }
    }
    return getGetAllPropertiesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyRequest,
      ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyResponse> getGetPropertyByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getPropertyById",
      requestType = ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyRequest.class,
      responseType = ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyRequest,
      ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyResponse> getGetPropertyByIdMethod() {
    io.grpc.MethodDescriptor<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyRequest, ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyResponse> getGetPropertyByIdMethod;
    if ((getGetPropertyByIdMethod = PropertyServiceRPCGrpc.getGetPropertyByIdMethod) == null) {
      synchronized (PropertyServiceRPCGrpc.class) {
        if ((getGetPropertyByIdMethod = PropertyServiceRPCGrpc.getGetPropertyByIdMethod) == null) {
          PropertyServiceRPCGrpc.getGetPropertyByIdMethod = getGetPropertyByIdMethod =
              io.grpc.MethodDescriptor.<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyRequest, ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getPropertyById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PropertyServiceRPCMethodDescriptorSupplier("getPropertyById"))
              .build();
        }
      }
    }
    return getGetPropertyByIdMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PropertyServiceRPCStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PropertyServiceRPCStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PropertyServiceRPCStub>() {
        @java.lang.Override
        public PropertyServiceRPCStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PropertyServiceRPCStub(channel, callOptions);
        }
      };
    return PropertyServiceRPCStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PropertyServiceRPCBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PropertyServiceRPCBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PropertyServiceRPCBlockingStub>() {
        @java.lang.Override
        public PropertyServiceRPCBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PropertyServiceRPCBlockingStub(channel, callOptions);
        }
      };
    return PropertyServiceRPCBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PropertyServiceRPCFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PropertyServiceRPCFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PropertyServiceRPCFutureStub>() {
        @java.lang.Override
        public PropertyServiceRPCFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PropertyServiceRPCFutureStub(channel, callOptions);
        }
      };
    return PropertyServiceRPCFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getAllProperties(ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllPropertiesMethod(), responseObserver);
    }

    /**
     */
    default void getPropertyById(ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPropertyByIdMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PropertyServiceRPC.
   */
  public static abstract class PropertyServiceRPCImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PropertyServiceRPCGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PropertyServiceRPC.
   */
  public static final class PropertyServiceRPCStub
      extends io.grpc.stub.AbstractAsyncStub<PropertyServiceRPCStub> {
    private PropertyServiceRPCStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PropertyServiceRPCStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PropertyServiceRPCStub(channel, callOptions);
    }

    /**
     */
    public void getAllProperties(ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllPropertiesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getPropertyById(ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyRequest request,
        io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPropertyByIdMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PropertyServiceRPC.
   */
  public static final class PropertyServiceRPCBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PropertyServiceRPCBlockingStub> {
    private PropertyServiceRPCBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PropertyServiceRPCBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PropertyServiceRPCBlockingStub(channel, callOptions);
    }

    /**
     */
    public ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesResponse getAllProperties(ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllPropertiesMethod(), getCallOptions(), request);
    }

    /**
     */
    public ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyResponse getPropertyById(ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPropertyByIdMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PropertyServiceRPC.
   */
  public static final class PropertyServiceRPCFutureStub
      extends io.grpc.stub.AbstractFutureStub<PropertyServiceRPCFutureStub> {
    private PropertyServiceRPCFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PropertyServiceRPCFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PropertyServiceRPCFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesResponse> getAllProperties(
        ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllPropertiesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyResponse> getPropertyById(
        ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPropertyByIdMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_PROPERTIES = 0;
  private static final int METHODID_GET_PROPERTY_BY_ID = 1;

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
        case METHODID_GET_ALL_PROPERTIES:
          serviceImpl.getAllProperties((ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesRequest) request,
              (io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesResponse>) responseObserver);
          break;
        case METHODID_GET_PROPERTY_BY_ID:
          serviceImpl.getPropertyById((ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyRequest) request,
              (io.grpc.stub.StreamObserver<ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyResponse>) responseObserver);
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
          getGetAllPropertiesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesRequest,
              ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertiesResponse>(
                service, METHODID_GET_ALL_PROPERTIES)))
        .addMethod(
          getGetPropertyByIdMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyRequest,
              ubb.graduation24.immopal.grpc.PropertyOuterClass.GetPropertyResponse>(
                service, METHODID_GET_PROPERTY_BY_ID)))
        .build();
  }

  private static abstract class PropertyServiceRPCBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PropertyServiceRPCBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ubb.graduation24.immopal.grpc.PropertyOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PropertyServiceRPC");
    }
  }

  private static final class PropertyServiceRPCFileDescriptorSupplier
      extends PropertyServiceRPCBaseDescriptorSupplier {
    PropertyServiceRPCFileDescriptorSupplier() {}
  }

  private static final class PropertyServiceRPCMethodDescriptorSupplier
      extends PropertyServiceRPCBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PropertyServiceRPCMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (PropertyServiceRPCGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PropertyServiceRPCFileDescriptorSupplier())
              .addMethod(getGetAllPropertiesMethod())
              .addMethod(getGetPropertyByIdMethod())
              .build();
        }
      }
    }
    return result;
  }
}
