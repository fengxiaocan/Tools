package com.app.tool;

import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Author: Mr.xiao on 2017/3/15
 *
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: https工具类
 */

class HttpsUtils extends Util {


    /**
     * 授权的密钥管理器，用来授权验证
     *
     * @param keyIs
     * @param password
     * @return
     */
    public static KeyManager[] getKeyManagers(InputStream keyIs, String password) {
        try {
            if (keyIs == null || password == null)
                return null;
            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(keyIs, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(keyIs);
        }
    }

    /**
     * 授权的密钥管理器，用来授权验证
     *
     * @param keyFile
     * @param password
     * @return
     */
    public static KeyManager[] getKeyManagers(File keyFile, String password) {
        try {
            if (!FileUtils.isFileExists(keyFile) || password == null)
                return null;
            FileInputStream fis = new FileInputStream(keyFile);
            return getKeyManagers(fis, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 授权的密钥管理器，用来授权验证
     *
     * @param keyFile
     * @param password
     * @return
     */
    public static KeyManager[] getKeyManagers(String keyFile, String password) {
        return getKeyManagers(new File(keyFile), password);
    }

    /**
     * 授权的密钥管理器，用来授权验证
     *
     * @param keyFile
     * @param password
     * @return
     */
    public static KeyManager[] getKeyManagers(Uri keyFile, String password) {
        try {
            if (keyFile == null || password == null)
                return null;
            InputStream fis = getContext().getContentResolver().openInputStream(keyFile);
            return getKeyManagers(fis, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 被授权的证书管理器，用来验证服务器端的证书
     *
     * @param certificates
     * @return
     */
    public static TrustManager[] getTrustManagers(InputStream... certificates) {
        if (EmptyUtils.isEmpty(certificates))
            return null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                if (certificate != null) {
                    try {
                        keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.
                    getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            return trustManagerFactory.getTrustManagers();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(certificates);
        }
        return null;
    }

    /**
     * 被授权的证书管理器，用来验证服务器端的证书
     *
     * @return
     */
    public static TrustManager[] getTrustManagers(File... cerFiles) {
        if (EmptyUtils.isEmpty(cerFiles))
            return null;
        InputStream[] iss = new InputStream[cerFiles.length];
        for (int i = 0; i < cerFiles.length; i++) {
            try {
                iss[i] = new FileInputStream(cerFiles[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return getTrustManagers(iss);
    }

    /**
     * 被授权的证书管理器，用来验证服务器端的证书
     *
     * @return
     */
    public static TrustManager[] getTrustManagers(String... cerFiles) {
        if (EmptyUtils.isEmpty(cerFiles))
            return null;
        InputStream[] iss = new InputStream[cerFiles.length];
        for (int i = 0; i < cerFiles.length; i++) {
            try {
                iss[i] = new FileInputStream(cerFiles[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return getTrustManagers(iss);
    }

    /**
     * 被授权的证书管理器，用来验证服务器端的证书
     *
     * @return
     */
    public static TrustManager[] getTrustManagers(Uri... cerFiles) {
        if (EmptyUtils.isEmpty(cerFiles))
            return null;
        InputStream[] iss = new InputStream[cerFiles.length];
        for (int i = 0; i < cerFiles.length; i++) {
            try {
                iss[i] = getContext().getContentResolver().openInputStream(cerFiles[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return getTrustManagers(iss);
    }

    /**
     * 获取证书创建工具
     *
     * @param trustManagers
     * @return
     */
    public static SSLSocketFactory getSslSocketFactory(TrustManager... trustManagers) {
        return getSslSocketFactory(null, trustManagers);
    }

    /**
     * 获取证书创建工具
     *
     * @param keyManagers
     * @param certificates
     * @return
     */
    public static SSLSocketFactory getSslSocketFactory(KeyManager[] keyManagers, InputStream... certificates) {
        return getSslSocketFactory(keyManagers, getTrustManagers(certificates));
    }

    /**
     * 获取证书创建工具
     *
     * @param keyManagers
     * @param files
     * @return
     */
    public static SSLSocketFactory getSslSocketFactory(KeyManager[] keyManagers, File... files) {
        return getSslSocketFactory(keyManagers, getTrustManagers(files));
    }

    /**
     * 获取证书创建工具
     *
     * @param keyManagers
     * @param files
     * @return
     */
    public static SSLSocketFactory getSslSocketFactory(KeyManager[] keyManagers, String... files) {
        return getSslSocketFactory(keyManagers, getTrustManagers(files));
    }

    /**
     * 获取证书创建工具
     *
     * @param keyManagers
     * @param files
     * @return
     */
    public static SSLSocketFactory getSslSocketFactory(KeyManager[] keyManagers, Uri... files) {
        return getSslSocketFactory(keyManagers, getTrustManagers(files));
    }

    /**
     * 获取证书创建工具
     *
     * @param files
     * @return
     */
    public static SSLSocketFactory getSslSocketFactory(File... files) {
        return getSslSocketFactory(null, getTrustManagers(files));
    }

    /**
     * 获取证书创建工具
     *
     * @param files
     * @return
     */
    public static SSLSocketFactory getSslSocketFactory(String... files) {
        return getSslSocketFactory(null, getTrustManagers(files));
    }

    /**
     * 获取证书创建工具
     *
     * @param files
     * @return
     */
    public static SSLSocketFactory getSslSocketFactory(Uri... files) {
        return getSslSocketFactory(null, getTrustManagers(files));
    }


    /**
     * 获取证书创建工具
     *
     * @param trustManagers
     * @return
     */
    public static SSLSocketFactory getSslSocketFactory(KeyManager[] keyManagers, TrustManager[] trustManagers) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            if (trustManagers == null) {
                sslContext.init(keyManagers, new TrustManager[]{new UnSafeTrustManager()}, new SecureRandom());
            } else {
                sslContext.init(keyManagers, trustManagers, null);
            }
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class UnSafeTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
