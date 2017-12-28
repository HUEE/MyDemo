package com.example.hwj.mydemo.utils.encryption.Base64;

public abstract interface Encoder {
    public abstract Object encode (Object paramObject)
            throws EncoderException;
}
