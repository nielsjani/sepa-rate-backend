package be.separate.user;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private KeycloakFacade keycloakFacade;

    @PostMapping(path = "/login")
    public AccessTokenResponse login(@RequestBody UserDto userDto) {
        return keycloakFacade.login(userDto.getUsername(), userDto.getPassword());
    }

    @GetMapping(path = "/user/country/{username}")
    public CountryDto getCountryForUser(@PathVariable("username") String usernmae) {
        CountryDto countryDto = new CountryDto();
        countryDto.country = keycloakFacade.getUserByUsername(usernmae).getAttributes().get("country").toString();
        return countryDto;
    }
}
