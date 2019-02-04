package be.separate.separation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

@Named
public class SeparationFilterRepository {

    @Inject
    private NamedParameterJdbcTemplate jdbcTemplate;
// JdbcTemplate 'execute' line 604
    public List<SeparationDto> getFilteredSeparationsForUser(String username, String filter) {
        Map<String, Object> arguments = newHashMap();
        arguments.put("username", username);
        String queryString = "SELECT s.ID as id, s.COUNTRY as country, s.REGION_NAME as separatedRegionName, s.CREATED_USER as createdUser, s.ARGUMENTATION as argumentation " +
                "FROM separation s " +
                "WHERE s.CREATED_USER = :username ";
        if(!StringUtils.isEmpty(filter)) {
            queryString += "AND s.REGION_NAME LIKE :filter ";
            arguments.put("filter", '%'+filter+'%');
        }

        List<Map<String, Object>> results = jdbcTemplate.queryForList(queryString, arguments);
        return results.stream()
                .map(result -> new SeparationDto()
                        .withId((Integer) result.get("id"))
                        .withCountry((String) result.get("country"))
                        .withSeparatedRegionName((String) result.get("separatedRegionName"))
                        .withCreatedUser((String) result.get("createdUser"))
                        .withArgumentation((String) result.get("argumentation"))
                )
                .collect(Collectors.toList());
    }
}
