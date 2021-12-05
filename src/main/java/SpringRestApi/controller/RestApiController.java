package SpringRestApi.controller;


import SpringRestApi.model.User;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestApiController {


    @PostConstruct
    public void getCode() {
        String url = "http://91.241.64.178:7081/api/users";
        String code = "Итоговый код = ";


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String sessionID = Objects.requireNonNull(responseEntity.getHeaders().get("Set-Cookie")).get(0).split(";")[0];

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", sessionID);


        HttpEntity<User> request = new HttpEntity<>(new User(3L, "James", "Brown", (byte) 73), httpHeaders);
        ResponseEntity<String> query = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        code += query.getBody();


        request = new HttpEntity<>(new User(3L, "Thomas", "Shelby", (byte) 45), httpHeaders);
        query = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        code += query.getBody();


        request = new HttpEntity<>(new User(3L, "Thomas", "Shelby", (byte) 45), httpHeaders);
        query = restTemplate.exchange(url + "/" + 3, HttpMethod.DELETE, request, String.class);
        code += query.getBody();


        System.out.println(code);
    }
}

