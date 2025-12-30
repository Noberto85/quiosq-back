package com.webone.quiosq.itg.impl;


import com.webone.quiosq.dto.HeaderApi;
import com.webone.quiosq.itg.MercadoApiService;
import com.webone.quiosq.itg.request.PixRequest;
import com.webone.quiosq.itg.response.OAuthTokenResponse;
import com.webone.quiosq.itg.response.PagamentoApiResponse;
import com.webone.quiosq.itg.response.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class MercadoPagoApi implements MercadoApiService {

    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String GRANT_TYPE = "grant_type";
    private static final String CODE = "code";
    private static final String REDIRECT_URI = "redirect_uri";
    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String X_IDEMPOTENCY_KEY = "X-Idempotency-Key";
    private static final String FORMAT_URL = "%s/%s";
    private final String PATH_USERS = "users/me";
    private final String PATH_AUTH_TOKEN = "oauth/token";
    private final String PATH_AUTH_PAYMENT = "v1/payments";
    private final RestTemplate restTemplate;

    private final String urlBase;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public MercadoPagoApi(RestTemplate restTemplate,
        @Value("${mercadopago.pago.base-url}") String urlBase,
        @Value("${mercadopago.client_id}") String clientId,
        @Value("${mercadopago.client_secret}") String clientSecret,
        @Value("${mercadopago.redirect_uri}") String redirectUri) {
        this.restTemplate = restTemplate;
        this.urlBase = urlBase;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    @Override
    public UserDTO getAuthToken(String accessToken) {
        HeaderApi headerApi = new HeaderApi();
        headerApi.setToken(accessToken);
        return get(PATH_USERS, headerApi, UserDTO.class);
    }

    @Override
    public OAuthTokenResponse getAutorizationDetails(String code) {
        return post(buidForm(code), PATH_AUTH_TOKEN, OAuthTokenResponse.class);
    }

    @Override
    public PagamentoApiResponse createPix(String acessToken, String idempotencyKey, PixRequest request) {
        return post(idempotencyKey, acessToken, request, PATH_AUTH_PAYMENT, PagamentoApiResponse.class);
    }

    private <OUT> OUT get(String path, HeaderApi headerApi, Class<OUT> responseType) {
        HttpHeaders headers = getHttpHeaders(headerApi);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        String url = String.format(FORMAT_URL, urlBase, path);
        ResponseEntity<OUT> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            request,
            responseType
        );
        return response.getBody();
    }

    public <OUT> OUT post(MultiValueMap<String, String> body, String path,
        Class<OUT> responseType) {
        try {
            String url = String.format(FORMAT_URL, urlBase, path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<OUT> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                responseType
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <OUT, IN extends BaserRequest> OUT post(String idempotencyKey, String acessToken, IN body,
        String path,
        Class<OUT> responseType) {
        String url = String.format(FORMAT_URL, urlBase, path);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(acessToken);
            headers.add(X_IDEMPOTENCY_KEY, idempotencyKey);
            HttpEntity<BaserRequest> request = new HttpEntity<>(body, headers);

            ResponseEntity<OUT> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                responseType
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private MultiValueMap<String, String> buidForm(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(CLIENT_ID, clientId);
        body.add(CLIENT_SECRET, clientSecret);
        body.add(GRANT_TYPE, AUTHORIZATION_CODE);
        body.add(CODE, code);
        body.add(REDIRECT_URI, redirectUri);
        return body;
    }

    private static HttpHeaders getHttpHeaders(HeaderApi headerApi) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(headerApi.getToken());
        return headers;
    }


}
