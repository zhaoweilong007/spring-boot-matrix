// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: HelloService.proto

package com.zwl.grpc;

public final class HelloWorldProto {
  private HelloWorldProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HelloReq_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_HelloReq_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HelloResp_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_HelloResp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022HelloService.proto\"\030\n\010HelloReq\022\014\n\004name" +
      "\030\001 \001(\t\"\030\n\tHelloResp\022\013\n\003msg\030\001 \001(\t23\n\014Hell" +
      "oService\022#\n\010sayHello\022\t.HelloReq\032\n.HelloR" +
      "esp\"\000B!\n\014com.zwl.grpcB\017HelloWorldProtoP\001" +
      "b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_HelloReq_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_HelloReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_HelloReq_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_HelloResp_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_HelloResp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_HelloResp_descriptor,
        new java.lang.String[] { "Msg", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}