package com.example.demo;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UploadClient {

	public static void main(String[] args) throws IOException {

		RestTemplate restTemplate = new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new BasicAuthenticationInterceptor("TEST", "******"));

		interceptors.add(new CustomLoggingRequestInterceptor());
		restTemplate.setInterceptors(interceptors);
		final HttpHeaders getHeaders = new HttpHeaders();

		// getHeaders.setContentType(MediaType.);
		getHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		getHeaders.add("X-CSRF-Token", "Fetch");

		getHeaders.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));
		ResponseEntity<?> csrfTokenExchange = restTemplate.exchange(
				"OST", HttpMethod.GET, new HttpEntity<>(getHeaders),
				String.class);

		System.out.println(csrfTokenExchange.getHeaders());
		final String csrfToken = csrfTokenExchange.getHeaders().getFirst("X-CSRF-Token");
		final List<String> cookies = csrfTokenExchange.getHeaders().get("set-cookie");
		
		
		String cookie = cookies.stream().collect(Collectors.joining(";"));

		Resource bodyMap = getUserFileResource();
		// System.out.println(bodyMap);

		System.out.println(csrfToken);
		System.out.println(cookie);

		HttpHeaders headers = new HttpHeaders();
		
		// headers.add("Content-Type",
		// "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		headers.add("x-csrf-token", csrfToken);
		headers.set("cookie", cookie);
		
	    headers.add("Accept", "application/json, application/*+json");

		HttpEntity<Resource> requestEntity = new HttpEntity<>(bodyMap, headers);

		RestTemplate restTemplate1 = new RestTemplate();
		restTemplate1.setInterceptors(interceptors);
		ResponseEntity<String> response = restTemplate1.exchange(
				"SETt", HttpMethod.POST, requestEntity,
				String.class);

		System.out.println("response status: " + response.getStatusCode());
		System.out.println("response body: " + response.getBody());
	}

	public static Resource getUserFileResource() throws IOException {
		// todo replace tempFile with a real file
//        Path tempFile = Files.createTempFile("upload-test-file", ".txt");
//        Files.write(tempFile, "some test content...\nline1\nline2".getBytes());
//        System.out.println("uploading: " + tempFile);
		File file = new File("Book1.xlsx");
		// to upload in-memory bytes use ByteArrayResource instead
		return new FileSystemResource(file);
	}
}
