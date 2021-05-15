package com;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AppAsyncConf {

	private static HttpClient client;

	public static void main(String[] args) throws IOException {
		// Default HTTP_2
		client = HttpClient.newBuilder().version(Version.HTTP_2).connectTimeout(Duration.ofSeconds(3))
				.followRedirects(HttpClient.Redirect.NORMAL).build();

		var futures = Files.lines(Path.of("url.txt")).map(AppAsyncConf::validateLink).collect(Collectors.toList());

		futures.stream().map(CompletableFuture::join).forEach(System.out::println);

	}

	private static CompletableFuture<String> validateLink(String link) {

		HttpRequest request = HttpRequest.newBuilder(URI.create(link)).timeout(Duration.ofSeconds(2)).GET().build();

		return client.sendAsync(request, HttpResponse.BodyHandlers.discarding())
				.thenApply(AppAsyncConf::responseTostring)
				.exceptionally(e -> String.format("%s-> %s", link, link, false));

	}

	private static String responseTostring(HttpResponse<Void> response) {
		int status = response.statusCode();
		boolean success = status > 200 && status <= 299;
		return String.format("%s-> %s(status:%s)", response.uri(), success, status);

	}
}
