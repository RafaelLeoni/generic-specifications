package br.com.rafaelleoni.specifications.resources.client;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rafaelleoni.specifications.filter.PageRequestBuilder;
import br.com.rafaelleoni.specifications.filter.SpecificationsBuilder;
import br.com.rafaelleoni.specifications.resources.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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