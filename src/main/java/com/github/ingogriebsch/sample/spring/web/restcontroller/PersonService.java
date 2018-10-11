/*
 * Copyright 2018 Ingo Griebsch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.ingogriebsch.sample.spring.web.restcontroller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final Set<Person> persons = new HashSet<>();

    public Set<Person> findAll() {
        return persons;
    }

    public Optional<Person> findOne(String id) {
        return persons.stream().filter(p -> p.getId().equals(id)).limit(1).findAny();
    }

    public boolean insert(Person person) {
        for (Person p : persons) {
            if (p.getId().equals(person.getId())) {
                return false;
            }
        }
        return persons.add(person);
    }

    public boolean delete(String id) {
        return persons.removeIf(p -> p.getId().equals(id));
    }
}