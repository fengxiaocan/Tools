package com.app.encrypt;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

public interface ICipher {

    String getTransformation();

    boolean isParameterMode();

    boolean isNoPadding();

    AlgorithmParameterSpec toParameterKey(byte[] vectorKey);

    Key toKey(byte[] key);
}
