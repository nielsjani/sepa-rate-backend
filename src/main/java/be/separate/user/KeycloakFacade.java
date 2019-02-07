package be.separate.user;

import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;

@Named
public class KeycloakFacade {

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    public AccessTokenResponse login(String username, String password) {
        HttpHeaders urlEncodedHeader = new HttpHeaders();
        urlEncodedHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "SpringBootClient");
        map.add("username", username);
        map.add("password", password);
        map.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(map, urlEncodedHeader);

        return new RestTemplate().postForObject("http://localhost:8880/auth/realms/master/protocol/openid-connect/token", body, AccessTokenResponse.class);
    }

    public UserRepresentation getUserByUsername(String username) {
        HttpEntity entity = getAuthorizationHeaders();
        String url = String.format("%s/admin/realms/%s/users?username=%s", keycloakUrl, keycloakRealm, username.toLowerCase());
        UserRepresentation[] users = new RestTemplate().exchange(url, HttpMethod.GET, entity, UserRepresentation[].class).getBody();
        return users.length > 0 ? users[0] : null;
    }

    private HttpEntity getAuthorizationHeaders() {
        AccessTokenResponse response = getMasterAuthenticationToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + response.getToken());
        return new HttpEntity(headers);
    }

    private AccessTokenResponse getMasterAuthenticationToken() {
        HttpHeaders urlEncodedHeader = new HttpHeaders();
        urlEncodedHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "SpringBootClient");
        map.add("username", "admin");
        map.add("password", "admin");
        map.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(map, urlEncodedHeader);

        return new RestTemplate().postForObject(String.format("%s/realms/master/protocol/openid-connect/token", keycloakUrl), body, AccessTokenResponse.class);
    }
}
