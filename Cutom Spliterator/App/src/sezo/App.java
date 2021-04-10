package sezo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import sezo.model.Person;
import sezo.splititerator.PersonSpliterator;

public class App {

	public static void main(String[] args) {

		Path path = Paths.get("data/people.txt");
		try (Stream<String> lines = Files.lines(path);) {

			Spliterator<String> lineSpliterator = lines.spliterator();
			Spliterator<Person> peopleSpliterator = new PersonSpliterator(lineSpliterator);

			Stream<Person> people = StreamSupport.stream(peopleSpliterator, false);
			people.forEach(System.out::println);

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
