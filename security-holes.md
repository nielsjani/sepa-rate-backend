## Code injection
### SQL injection
Possible in _SeparationFilterRepository_

##### Why
Parameter _filter_ is simply concatenated to the rest of the sql query.
This allows for some query manipulation if you turn filter into 

eg: _A%' OR 1=1 OR s.CREATED_USER LIKE '%A_

#### How to solve 
Parametrise the user-inputted fields. 
EG by using a NamedParameterJdbcTemplate and adding a map with parameters as second argument when doing the queryForList call.
