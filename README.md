
# Generic Specifications
This project is to use generic specifications of Spring Data JPA to query any parameter

## Install
To install clone the project and then run the gradle build command:

> 	$ git clone https://github.com/RafaelLeoni/generic-specifications.git
> 	$ cd generic-specifications
> 	$ gradle build

## Operations
This table shows a list of the current supported operations and the ones that still need implementation.

| Operation				   | Code | Implemented |
| ------------------------ | ---- | ----------- |
| equal 				   | eq   |	Yes      	|
| not equal 			   | neq  |	No			|
| greater than 			   | gt	  |	Yes 		|
| greater than or equal to | gte  |	No 			|
| less than 			   | lt	  |	Yes 		|
| less than or equal to	   | lte  |	No 			|
| in 					   | in   |	No 			|
| not in 				   | nin  |	No 			|
| between 				   | btn  |	No 			|
| like 					   | like |	No	 		|


## Implementation
### Using query parameter 
 To be able to query fields using a single query parameter you should inject `SpecificationFactory` class like example bellow:

```java
@RestController
@RequestMapping(path = "/v1/users")
@Api(value = "/v1/users", tags = "Operations about users")
public class UserResource {
 
	@Autowired
	private SpecificationsBuilder<User> builder;
	
    @Autowired
    private UserRepository userRepository;
 
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all users by filter", response = User.class, responseContainer = "List")
    public List<User> search(@RequestParam(required = false) String query,
    	@RequestParam(required = false) Integer _offset,
    	@RequestParam(required = false) Integer _limit,
    	@RequestParam(required = false) String _sort) {
    	
    	var page = PageRequestBuilder.getPageRequest(_limit, _offset, _sort);
        return userRepository.findAll(builder.with(query).build(), page).getContent();
    }
    
}
```

So on a browser you can call the endpoint `http://localhost:8080/specifications/v1/users?query=firstName:eq:John:AND,age:gt:25:AND` and will see the result:

```json
[{  
	"id":  1,
	"firstName":  "John",
	"lastName":  "Doe",
	"email":  "johndoe@mail.com",
	"age":  30  
}]
```

### Using @ModelAttribute
You also have the option to create a model that contains all specifics fields that you want to search, follow up an example hoe to implement this option.

```java
@Data
public class ClientRequest {
	
	private String firstName;
	private String lastName;
	private String email;
	private String age;

}

@RestController
@RequestMapping(path = "/v1/clients")
@Api(value = "/v1/clients", tags = "Operations about clients")
public class ClientResource {
 
	@Autowired
	private SpecificationsBuilder<Client> builder;
	
    @Autowired
    private ClientRepository clientRepository;
 
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all clients by filter", response = User.class, responseContainer = "List")
    public List<Client> search(@ModelAttribute ClientRequest clientRequest,
		@RequestParam(required = false) Integer _offset,
    	@RequestParam(required = false) Integer _limit,
    	@RequestParam(required = false) String _sort) {
    	
    	var params = new ObjectMapper()
    		.convertValue(clientRequest, new TypeReference<Map<String, Object>>() {});
		
		var spec = builder.with(params).build();
		var page = PageRequestBuilder.getPageRequest(_limit, _offset, _sort);
		return clientRepository.findAll(spec, page).getContent();
    }
    
}
```

Then when you call `http://localhost:8080/specifications/v1/clients?firstName=:eq:John:AND&email=:eq:janedoe@mail.com:OR` you will get the follow result:

```json
[
	{  
		"id":  1,
		"firstName":  "John",
		"lastName":  "Doe",
		"email":  "johndoe@mail.com",
		"age":  30  
	},
	{
		"id":2,
		"firstName":"Jane",
		"lastName":null,
		"email":"janedoe@mail.com",
		"age":null
	}
]
```