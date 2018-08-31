package com.example.test.utils.okhttps;

import com.example.test.utils.constant.Constant;

import okhttp3.OkHttpClient;


import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okio.Buffer;

/**
 * Created by qts on 2018/08/12.
 */
public final class HttpsUtils {

    private static OkHttpClient client ;

    /**
     * 获取OkHttpClient实例
     * @return
     */
    public static OkHttpClient getOKhttpsClient()
    {
        if (client == null) {
            client = initOKhttps();
        }
        return client;
    }

    /**
     * 初始化HTTPS,添加信任证书
     *
     */
    private static OkHttpClient initOKhttps() {

        try {
            OKhttpLogInterceptor logging = new OKhttpLogInterceptor();
            logging.setLevel(OKhttpLogInterceptor.Level.BODY);

            int timeout = Constant.CONN_TIME_OUT;
//            SSLSocketFactory sslSocketFactory = getSSLSocketFactory_Certificate(getApplicationContext(),"BKS", R.raw.server);
            SSLSocketFactory sslSocketFactory = createSSLSocketFactory();
            if(null == sslSocketFactory)
            {
                return null;
            }
            client = new okhttp3.OkHttpClient.Builder().connectTimeout(timeout, TimeUnit.MILLISECONDS).readTimeout(timeout, TimeUnit.MILLISECONDS)
                    .sslSocketFactory(sslSocketFactory)
                    .addInterceptor(logging)
//                    .hostnameVerifier(OkHostnameVerifier.INSTANCE)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }

    /**
     * 本地校验证书
     *
     */
    private  static SSLSocketFactory getSSLSocketFactory(){

        try{
            InputStream  certStream = new Buffer().writeUtf8(Constant.SERVER_CERT).inputStream();
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate serverCert = (X509Certificate)cf.generateCertificate(certStream);
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);

            keyStore.load(null,null);
            keyStore.setCertificateEntry("cert", serverCert);
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

//        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(serverCert);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null,tmf.getTrustManagers(),null);
            return sslContext.getSocketFactory();

        }catch (CertificateException e)
        {
            e.printStackTrace();
        }catch (KeyStoreException e)
        {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }catch (KeyManagementException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 信任所有证书
     *
     */
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null,  new TrustManager[] { new TrustAllCerts() }, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
    }


}