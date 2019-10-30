// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: loginResp.proto

package com.qs.game.socket.client.msg;

public final class LoginRespBuf {
  private LoginRespBuf() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface LoginRespOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.qs.game.socket.client.msg.LoginResp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required bool su = 1;</code>
     */
    boolean hasSu();
    /**
     * <code>required bool su = 1;</code>
     */
    boolean getSu();

    /**
     * <code>required int32 exp = 2;</code>
     */
    boolean hasExp();
    /**
     * <code>required int32 exp = 2;</code>
     */
    int getExp();
  }
  /**
   * Protobuf type {@code com.qs.game.socket.client.msg.LoginResp}
   */
  public  static final class LoginResp extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.qs.game.socket.client.msg.LoginResp)
      LoginRespOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use LoginResp.newBuilder() to construct.
    private LoginResp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private LoginResp() {
      su_ = false;
      exp_ = 0;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private LoginResp(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              bitField0_ |= 0x00000001;
              su_ = input.readBool();
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              exp_ = input.readInt32();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.qs.game.socket.client.msg.LoginRespBuf.internal_static_com_qs_game_socket_client_msg_LoginResp_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.qs.game.socket.client.msg.LoginRespBuf.internal_static_com_qs_game_socket_client_msg_LoginResp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.qs.game.socket.client.msg.LoginRespBuf.LoginResp.class, com.qs.game.socket.client.msg.LoginRespBuf.LoginResp.Builder.class);
    }

    private int bitField0_;
    public static final int SU_FIELD_NUMBER = 1;
    private boolean su_;
    /**
     * <code>required bool su = 1;</code>
     */
    public boolean hasSu() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required bool su = 1;</code>
     */
    public boolean getSu() {
      return su_;
    }

    public static final int EXP_FIELD_NUMBER = 2;
    private int exp_;
    /**
     * <code>required int32 exp = 2;</code>
     */
    public boolean hasExp() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required int32 exp = 2;</code>
     */
    public int getExp() {
      return exp_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasSu()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasExp()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBool(1, su_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeInt32(2, exp_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(1, su_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, exp_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.qs.game.socket.client.msg.LoginRespBuf.LoginResp)) {
        return super.equals(obj);
      }
      com.qs.game.socket.client.msg.LoginRespBuf.LoginResp other = (com.qs.game.socket.client.msg.LoginRespBuf.LoginResp) obj;

      boolean result = true;
      result = result && (hasSu() == other.hasSu());
      if (hasSu()) {
        result = result && (getSu()
            == other.getSu());
      }
      result = result && (hasExp() == other.hasExp());
      if (hasExp()) {
        result = result && (getExp()
            == other.getExp());
      }
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasSu()) {
        hash = (37 * hash) + SU_FIELD_NUMBER;
        hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
            getSu());
      }
      if (hasExp()) {
        hash = (37 * hash) + EXP_FIELD_NUMBER;
        hash = (53 * hash) + getExp();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.qs.game.socket.client.msg.LoginRespBuf.LoginResp parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qs.game.socket.client.msg.LoginRespBuf.LoginResp parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qs.game.socket.client.msg.LoginRespBuf.LoginResp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qs.game.socket.client.msg.LoginRespBuf.LoginResp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qs.game.socket.client.msg.LoginRespBuf.LoginResp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.qs.game.socket.client.msg.LoginRespBuf.LoginResp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.qs.game.socket.client.msg.LoginRespBuf.LoginResp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qs.game.socket.client.msg.LoginRespBuf.LoginResp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qs.game.socket.client.msg.LoginRespBuf.LoginResp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.qs.game.socket.client.msg.LoginRespBuf.LoginResp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.qs.game.socket.client.msg.LoginRespBuf.LoginResp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.qs.game.socket.client.msg.LoginRespBuf.LoginResp parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.qs.game.socket.client.msg.LoginRespBuf.LoginResp prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.qs.game.socket.client.msg.LoginResp}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.qs.game.socket.client.msg.LoginResp)
        com.qs.game.socket.client.msg.LoginRespBuf.LoginRespOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.qs.game.socket.client.msg.LoginRespBuf.internal_static_com_qs_game_socket_client_msg_LoginResp_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.qs.game.socket.client.msg.LoginRespBuf.internal_static_com_qs_game_socket_client_msg_LoginResp_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.qs.game.socket.client.msg.LoginRespBuf.LoginResp.class, com.qs.game.socket.client.msg.LoginRespBuf.LoginResp.Builder.class);
      }

      // Construct using com.qs.game.socket.client.msg.LoginRespBuf.LoginResp.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        su_ = false;
        bitField0_ = (bitField0_ & ~0x00000001);
        exp_ = 0;
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.qs.game.socket.client.msg.LoginRespBuf.internal_static_com_qs_game_socket_client_msg_LoginResp_descriptor;
      }

      @java.lang.Override
      public com.qs.game.socket.client.msg.LoginRespBuf.LoginResp getDefaultInstanceForType() {
        return com.qs.game.socket.client.msg.LoginRespBuf.LoginResp.getDefaultInstance();
      }

      @java.lang.Override
      public com.qs.game.socket.client.msg.LoginRespBuf.LoginResp build() {
        com.qs.game.socket.client.msg.LoginRespBuf.LoginResp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.qs.game.socket.client.msg.LoginRespBuf.LoginResp buildPartial() {
        com.qs.game.socket.client.msg.LoginRespBuf.LoginResp result = new com.qs.game.socket.client.msg.LoginRespBuf.LoginResp(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.su_ = su_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.exp_ = exp_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return (Builder) super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.qs.game.socket.client.msg.LoginRespBuf.LoginResp) {
          return mergeFrom((com.qs.game.socket.client.msg.LoginRespBuf.LoginResp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.qs.game.socket.client.msg.LoginRespBuf.LoginResp other) {
        if (other == com.qs.game.socket.client.msg.LoginRespBuf.LoginResp.getDefaultInstance()) return this;
        if (other.hasSu()) {
          setSu(other.getSu());
        }
        if (other.hasExp()) {
          setExp(other.getExp());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        if (!hasSu()) {
          return false;
        }
        if (!hasExp()) {
          return false;
        }
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.qs.game.socket.client.msg.LoginRespBuf.LoginResp parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.qs.game.socket.client.msg.LoginRespBuf.LoginResp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private boolean su_ ;
      /**
       * <code>required bool su = 1;</code>
       */
      public boolean hasSu() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required bool su = 1;</code>
       */
      public boolean getSu() {
        return su_;
      }
      /**
       * <code>required bool su = 1;</code>
       */
      public Builder setSu(boolean value) {
        bitField0_ |= 0x00000001;
        su_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bool su = 1;</code>
       */
      public Builder clearSu() {
        bitField0_ = (bitField0_ & ~0x00000001);
        su_ = false;
        onChanged();
        return this;
      }

      private int exp_ ;
      /**
       * <code>required int32 exp = 2;</code>
       */
      public boolean hasExp() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required int32 exp = 2;</code>
       */
      public int getExp() {
        return exp_;
      }
      /**
       * <code>required int32 exp = 2;</code>
       */
      public Builder setExp(int value) {
        bitField0_ |= 0x00000002;
        exp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 exp = 2;</code>
       */
      public Builder clearExp() {
        bitField0_ = (bitField0_ & ~0x00000002);
        exp_ = 0;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:com.qs.game.socket.client.msg.LoginResp)
    }

    // @@protoc_insertion_point(class_scope:com.qs.game.socket.client.msg.LoginResp)
    private static final com.qs.game.socket.client.msg.LoginRespBuf.LoginResp DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.qs.game.socket.client.msg.LoginRespBuf.LoginResp();
    }

    public static com.qs.game.socket.client.msg.LoginRespBuf.LoginResp getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<LoginResp>
        PARSER = new com.google.protobuf.AbstractParser<LoginResp>() {
      @java.lang.Override
      public LoginResp parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new LoginResp(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<LoginResp> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoginResp> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.qs.game.socket.client.msg.LoginRespBuf.LoginResp getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_qs_game_socket_client_msg_LoginResp_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_qs_game_socket_client_msg_LoginResp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\017loginResp.proto\022\035com.qs.game.socket.cl" +
      "ient.msg\"$\n\tLoginResp\022\n\n\002su\030\001 \002(\010\022\013\n\003exp" +
      "\030\002 \002(\005B\016B\014LoginRespBuf"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_qs_game_socket_client_msg_LoginResp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_qs_game_socket_client_msg_LoginResp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_qs_game_socket_client_msg_LoginResp_descriptor,
        new java.lang.String[] { "Su", "Exp", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}