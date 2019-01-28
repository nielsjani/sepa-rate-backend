package be.separate.separation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SeparationController {

    @Autowired
    private SeparationRepository repository;


    @GetMapping(path = "/separations/{userId}")
    public @ResponseBody List<SeparationDto> getSeparationsForUser(@PathVariable(name = "userId") String userId) {
        return repository.findByCreatedUser(userId).stream()
                .map(this::mapSeparationToDto)
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/separation")
    public @ResponseBody SeparationDto createSeparation(@RequestBody SeparationDto separationDto) {
        return mapSeparationToDto(repository.save(mapDtoToSeparation(separationDto)));
    }

    private SeparationDto mapSeparationToDto(Separation separation) {
        return new SeparationDto()
                .withId(separation.getId())
                .withCreatedUser(separation.getCreatedUser())
                .withCountry(separation.getCountry())
                .withSeparatedRegionName(separation.getSeparatedRegionName());
    }

    private Separation mapDtoToSeparation(SeparationDto separation) {
        return Separation.SeparationBuilder.separation()
                .withCreatedUser(separation.getCreatedUser())
                .withCountry(separation.getCountry())
                .withSeparatedRegionName(separation.getSeparatedRegionName())
                .build();
    }
}
