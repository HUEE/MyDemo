package com.example.hwj.mydemo.utils.encryption.Base64;

public abstract interface BinaryDecoder
        extends Decoder {
    public abstract byte[] decode (byte[] paramArrayOfByte)
            throws DecoderException;
}
