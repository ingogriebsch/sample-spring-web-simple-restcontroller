/*
 * Copyright 2019 Ingo Griebsch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.ingogriebsch.sample.spring.web.restcontroller;

import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class Startup implements CommandLineRunner {

    @NonNull
    private final PersonService personService;

    @Override
    public void run(String... args) throws Exception {
        newHashSet(person("Ingo", 44), person("Marcel", 33), person("Sophia", 21)).stream().forEach(p -> insert(p));
    }

    private void insert(Person personInsert) {
        if (personService.insert(personInsert)) {
            log.info("Inserting person '{}'...", personInsert);
        }
    }

    private static Person person(String name, Integer age) {
        return new Person(randomAlphanumeric(8), name, age);
    }
}
