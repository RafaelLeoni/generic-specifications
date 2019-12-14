package br.com.rafaelleoni.specifications.resources.client;

import lombok.Data;

@Data
public class ClientRequest {
	
	private String firstName;
	private String lastName;
	private String email;
	private String age;

}
