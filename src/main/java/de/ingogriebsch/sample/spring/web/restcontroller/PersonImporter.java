/*-
 * Copyright 2018-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ingogriebsch.sample.spring.web.restcontroller;

import static java.lang.String.format;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class PersonImporter implements CommandLineRunner {

    @NonNull
    private final PersonService personService;
    @NonNull
    private final IdGenerator idGenerator;

    @Override
    public void run(String... args) throws Exception {
        Set<Person> persons = newHashSet(person("Ingo", 44), person("Marcel", 33), person("Sophia", 21));
        persons.forEach(p -> insert(p));
        log.info(format("%d persons successfully imported and ready to be accessed!", persons.size()));
    }

    private void insert(Person personInsert) {
        if (personService.insert(personInsert)) {
            log.info("Import person '{}'...", personInsert);
        }
    }

    private Person person(String name, Integer age) {
        return new Person(idGenerator.next(), name, age);
    }
}
