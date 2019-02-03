package be.separate.separation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SeparationController {

    @Autowired
    private SeparationRepository repository;
    @Autowired
    private SeparationFilterRepository separationFilterRepository;


    @PostMapping(path = "/separations/{userId}")
    public @ResponseBody List<SeparationDto> getSeparationsForUser(@PathVariable(name = "userId") String userId, @RequestBody FilterDto filter) {
        return separationFilterRepository.getFilteredSeparationsForUser(userId, filter.getFilter());
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
