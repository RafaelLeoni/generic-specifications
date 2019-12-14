package br.com.rafaelleoni.specifications.resources.user;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaelleoni.specifications.filter.PageRequestBuilder;
import br.com.rafaelleoni.specifications.filter.SpecificationsBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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