package com.sezo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sezo.model.City;
import com.sezo.model.Person;

public class MergeMapApp {

	public static void main(String[] args) {

		Person p1 = new Person("Alice", 23);
		Person p2 = new Person("Brian", 56);
		Person p3 = new Person("Chelsea", 46);
		Person p4 = new Person("David", 28);
		Person p5 = new Person("Sezayir", 37);
		Person p6 = new Person("Francisco", 18);

		City newYork = new City("New York");
		City shanghai = new City("Shanghai");
		City paris = new City("Paris");

		Map<City, List<Person>> map1 = new HashMap<>();
		map1.computeIfAbsent(newYork, city -> new ArrayList<>()).add(p1);
		map1.computeIfAbsent(shanghai, city -> new ArrayList<>()).add(p2);
		map1.computeIfAbsent(shanghai, city -> new ArrayList<>()).add(p3);

		System.out.println("Map 1");
		map1.forEach((city, people) -> System.out.println(city + " : " + people));

		Map<City, List<Person>> map2 = new HashMap<>();
		map2.computeIfAbsent(shanghai, city -> new ArrayList<>()).add(p4);
		map2.computeIfAbsent(paris, city -> new ArrayList<>()).add(p5);
		map2.computeIfAbsent(paris, city -> new ArrayList<>()).add(p6);

		System.out.println("Map 2");
		map2.forEach((city, people) -> System.out.println(city + " : " + people));

		map2.forEach((city, people) -> {

			map1.merge(city, people, (map1List, map2List) -> {
				map1List.addAll(map2List);
				return map1List;
			});

		});

		System.out.println("\nMap merge");
		map1.forEach((city, people) -> System.out.println(city + " : " + people));

	}
}
