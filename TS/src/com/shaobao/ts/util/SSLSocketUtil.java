package com.shaobao.ts.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

import com.loopj.android.http.MySSLSocketFactory;

public class SSLSocketUtil  {
	public static SSLSocketFactory createSSLSocketFactory(){  
        MySSLSocketFactory sf = null;  
        try {  
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
            trustStore.load(null, null);  
            sf = new MySSLSocketFactory(trustStore);  
            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return sf;  
    }  
    
}

