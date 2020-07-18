/*-
 * #%L
 * Spring Web simple REST controller sample
 * %%
 * Copyright (C) 2018 - 2020 Ingo Griebsch
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.github.ingogriebsch.sample.spring.web.restcontroller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PersonController {

    static final String PATH_FIND_ONE = "/persons/{personId}";
    static final String PATH_FIND_ALL = "/persons";
    static final String PATH_INSERT = "/persons";
    static final String PATH_DELETE = "/persons/{personId}";

    @NonNull
    private final PersonService personService;

    @GetMapping(path = PATH_FIND_ALL, produces = APPLICATION_JSON_VALUE)
    public Set<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping(path = PATH_FIND_ONE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> findOne(@PathVariable String personId) {
        return personService.findOne(personId).map(ResponseEntity::ok).orElse(notFound().build());
    }

    @PostMapping(path = PATH_INSERT, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> insert(@RequestBody Person person) {
        return personService.insert(person) ? status(CREATED).build() : badRequest().build();
    }

    @DeleteMapping(path = PATH_DELETE)
    public ResponseEntity<Void> delete(@PathVariable String personId) {
        return personService.delete(personId) ? ok().build() : notFound().build();
    }
}
