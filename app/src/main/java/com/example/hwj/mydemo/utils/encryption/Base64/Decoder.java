package com.example.hwj.mydemo.utils.encryption.Base64;

public abstract interface Decoder {
    public abstract Object decode (Object paramObject)
            throws DecoderException;
}
