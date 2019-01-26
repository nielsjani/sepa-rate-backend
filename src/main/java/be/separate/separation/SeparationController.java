package be.separate.separation;

import be.separate.sql_injection_target.SqlInjectionTargetDto;
import be.separate.sql_injection_target.SqlInjectionTargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SeparationController {

//    @Autowired
//    private SqlInjectionTargetRepository sqlInjectionTargetRepository;
//
//
//    @GetMapping(path = "/separations")
//    public @ResponseBody List<SqlInjectionTargetDto> createSeparation() {
//        return sqlInjectionTargetRepository.findAll().stream().map(testDom -> new SqlInjectionTargetDto().withCol(testDom.getColumn1()).withId(testDom.getId())).collect(Collectors.toList());
//    }
}
