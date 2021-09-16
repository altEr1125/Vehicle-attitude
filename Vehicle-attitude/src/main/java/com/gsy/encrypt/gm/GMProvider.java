package com.gsy.encrypt.gm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;

/**
 * GM algorithm provider.
 */
public class GMProvider {
    private static Provider sProvider = null;

    /**
     * Get GM algorithm provider.
     *
     * @return GM algorithm provider.
     */
    public static Provider getProvider() {
        if (sProvider == null) {
            sProvider = new BouncyCastleProvider();
        }
        return sProvider;
    }


    private GMProvider() {
    }
}
