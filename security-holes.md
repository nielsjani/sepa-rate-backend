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

If you want to see Spring escaping the special characters, do the following:
- Start your backend in debug mode
- Put a debug point at JdbcTemplate.query -> rs = ps.executeQuery(); (line 666) 
- In the UI, try to filter using SQL injection
- If you evaluate the line with the debug point, you will get a 'HikariProxyResultSet' object. 
This has a 'Statement' field. If you check the delegate on it, you will see that the special characters have been escaped with backslashes.

### Code injection (non-sql)
#### Why
Backend stores any user input without checking if there is any executable code in it.
The UI does the same.

Try to input the following in the argumentation column of the separation table:

<h1>This could go wrong!</h1>
<p style='background-color:red'>For example...<p>
  <style>h1 {background-color:yellow;}</style>
  
  or
  
  <style>table {
  display: none !important;
  }</style>

#### How to solve
Angular escapes all code by default (both input and variables retrieved from backend), but other frameworks might not.
It is safest to implement a mechanism in the backend to escape all input received from rest calls that can be called by users you don't trust. 

EG: use OWASP sanitizer: https://github.com/OWASP/java-html-sanitizer/blob/master/src/main/java/org/owasp/html/examples/EbayPolicyExample.java

