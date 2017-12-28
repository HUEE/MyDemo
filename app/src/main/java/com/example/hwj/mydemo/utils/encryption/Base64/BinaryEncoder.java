package com.example.hwj.mydemo.utils.encryption.Base64;

public abstract interface BinaryEncoder
        extends Encoder {
    public abstract byte[] encode (byte[] paramArrayOfByte)
            throws EncoderException;
}
