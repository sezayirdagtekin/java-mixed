package com;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class App {

	private static HttpClient client;

	public static void main(String[] args) throws IOException {

		client = HttpClient.newHttpClient();

		Files.lines(Path.of("url.txt")).map(App::validateLink).forEach(System.out::println);

	}

	private static String validateLink(String link) {

		HttpRequest request = HttpRequest.newBuilder(URI.create(link)).GET().build();

		HttpResponse<Void> response = null;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.discarding());

		} catch (IOException | InterruptedException e) {
			return String.format("%s-> %s", response.uri(), link, false);
		}
		return responseTostring(response);

	}

	private static String responseTostring(HttpResponse<Void> response) {
		int status = response.statusCode();
		boolean success = status > 200 && status <= 299;
		return String.format("%s-> %s(status:%s)", response.uri(), success, status);

	}
}
