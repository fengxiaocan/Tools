package com.app.encrypt;

import android.util.Base64;

import com.app.tool.Tools;

import java.io.Serializable;
import java.nio.charset.Charset;

public final class ByteData implements Serializable {
    private byte[] data;

    public static ByteData build(byte[] data) {
        return new ByteData(data);
    }

    public static ByteData build(String data) {
        return new ByteData(data);
    }

    public static ByteData build(String data, Type type) {
        return new ByteData(data, type);
    }

    public ByteData(byte[] data) {
        this.data = data;
    }

    public ByteData(String data, Type type) {
        this.data = type.code(data);
    }

    public ByteData(String data) {
        this.data = data.getBytes();
    }

    public byte[] byteData() {
        return data;
    }

    public String codeData(Type type) {
        return type.code(data);
    }

    @Override
    public String toString() {
        return new String(data);
    }

    public String toByteString() {
        return new String(data);
    }

    public String toByteString(Charset charset) {
        return new String(data, charset);
    }

    public String toHexString() {
        return Type.HEX.code(data);
    }

    public String toBase64DecodeString() {
        return Type.BASE64_DECODE.code(data);
    }

    public String toBase64EncodeString() {
        return Type.BASE64_DECODE.code(data);
    }

    public enum Type {
        BASE64_ENCODE {
            @Override
            public byte[] code(String data) {
                return Base64.encode(data.getBytes(), Base64.DEFAULT);
            }

            @Override
            public String code(byte[] data) {
                return Base64.encodeToString(data, Base64.DEFAULT);
            }
        },
        BASE64_DECODE {
            @Override
            public byte[] code(String data) {
                return Base64.decode(data.getBytes(), Base64.DEFAULT);
            }

            @Override
            public String code(byte[] data) {
                return Base64.encodeToString(data, Base64.DEFAULT);
            }
        },
        HEX {
            @Override
            public byte[] code(String data) {
                return Tools.Hex.hex2Bytes(data);
            }

            @Override
            public String code(byte[] data) {
                return Tools.Hex.bytes2Hex(data);
            }
        },
        BYTE {
            @Override
            public byte[] code(String data) {
                return data.getBytes();
            }

            @Override
            public String code(byte[] data) {
                return new String(data);
            }
        };

        public byte[] code(String data) {
            throw new AbstractMethodError();
        }

        public String code(byte[] data) {
            throw new AbstractMethodError();
        }
    }
}
