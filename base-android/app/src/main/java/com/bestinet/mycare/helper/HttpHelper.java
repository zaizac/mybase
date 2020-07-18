package com.bestinet.mycare.helper;

import android.util.Base64;
import android.util.Log;

import com.bestinet.mycare.constant.AppConstant;
import com.bestinet.mycare.constant.BaseConstant;
import com.bestinet.mycare.constant.URLConstant;
import com.bestinet.mycare.util.BaseUtil;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpHelper {
    public String sendPostRequest(String endPointPath, String requestBody) {
        return sendPostRequestWithParameter(endPointPath, requestBody, null);
    }

    public String sendPostRequestWithParameter(String endPointPath, String requestBody, String httpParameter) {
        return sendRequest(URLConstant.HTTP_POST, endPointPath, requestBody, httpParameter);
    }

    public String sendGetRequest(String endPointPath) {
        return sendGetRequestWithParameter(endPointPath, null);
    }

    public String sendGetRequestWithParameter(String endPointPath, String httpParameter) {
        return sendRequest(URLConstant.HTTP_GET, endPointPath, null, httpParameter);
    }

    private String sendRequest(String requestMethod, String endPointPath, String requestBody, String httpParameter) {
        String results = "";

        try {
            // Trust Manager
            trustManager();

            HttpParams httpParameters = new BasicHttpParams();
            // Set the timeout in milliseconds until a connection is established.
            // The default value is zero, that means the timeout is not used.
            int timeoutConnection = 15000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 15000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            HttpClient httpclient = new DefaultHttpClient(httpParameters);

//            // 1. create HttpClient
//            HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
//            SchemeRegistry registry = new SchemeRegistry();
//            SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
//            socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
//            registry.register(new Scheme("https", socketFactory, 443));
//            SingleClientConnManager mgr = new SingleClientConnManager(httpParameters, registry);
//            HttpClient httpclient = new DefaultHttpClient(mgr, httpParameters);
//
//            // Set verifier
//            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

            Header[] headers = {
                    new BasicHeader(BaseConstant.HTTP_HEADER_CONTENT_TYPE, BaseConstant.CONTENT_TYPE)
                    , new BasicHeader(BaseConstant.HTTP_HEADER_AUTHORIZATION, getB64Auth(BaseConstant.HTTP_CLIENT_USERNAME, BaseConstant.HTTP_CLIENT_PSWD))
                    , new BasicHeader(BaseConstant.HTTP_HEADER_X_MESSAGE_ID, "123456")
                    , new BasicHeader(BaseConstant.HTTP_X_LANGUAGE, "en")
            };

            HttpGet httpGet = null;
            HttpPost httpPost = null;

            if (BaseUtil.isEquals(requestMethod, URLConstant.HTTP_GET)) {
                httpGet = new HttpGet(endPointPath);
                System.out.println("http: " + endPointPath);
                // Set Parameter
                if (!BaseUtil.isObjNull(httpParameter)) {
                    httpGet.setParams(httpParameters);
                }

                // Set Headers
                httpGet.setHeaders(headers);

            } else if (BaseUtil.isEquals(requestMethod, URLConstant.HTTP_POST)) {
                httpPost = new HttpPost(endPointPath);
                System.out.println("http: " + requestBody);
                StringEntity se = new StringEntity(requestBody);
                httpPost.setEntity(se);
                httpPost.setHeaders(headers);
            }

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse;

            if (!BaseUtil.isObjNull(httpGet)) {
                System.out.println("http 2: " + endPointPath);
                httpResponse = httpclient.execute(httpGet);
                System.out.println("http 3: " + endPointPath);
            } else {
                httpResponse = httpclient.execute(httpPost);
            }

            // 9. receive response as inputStream
            HttpEntity httpEntity = !BaseUtil.isObjNull(httpResponse) ? httpResponse.getEntity() : null;

            InputStream inputStream = httpEntity != null ? httpEntity.getContent() : null;

            // 10. convert inputStream to string
            if (inputStream != null) {
                results = convertInputStreamToString(inputStream);
                System.out.println("http: " + results);
            }
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return results;
    }

    private static void trustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                        try {
                            certs[0].checkValidity();
                        } catch (Exception e) {
                            throw new CertificateException(AppConstant.CERT_ERROR);
                        }
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                        try {
                            certs[0].checkValidity();
                        } catch (Exception e) {
                            throw new CertificateException(AppConstant.CERT_ERROR);
                        }
                    }
                }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private static String getB64Auth(String login, String pass) {
        String source = login + ":" + pass;
        return "Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        StringBuilder result = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null)
            result.append(line);

        inputStream.close();

        return result.toString();
    }
}
