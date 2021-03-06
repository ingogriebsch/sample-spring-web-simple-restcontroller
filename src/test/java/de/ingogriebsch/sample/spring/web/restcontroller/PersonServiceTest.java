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

import static java.util.UUID.randomUUID;

import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

class PersonServiceTest {

    @Test
    void findAll_should_return_available_persons() throws Exception {
        PersonService personService = new PersonService();
        Set<Person> persons = newHashSet(person(), person(), person());
        persons.stream().forEach(p -> personService.insert(p));
        assertThat(personService.findAll()).isNotNull().containsAll(persons);
    }

    @Test
    void findOne_should_return_matching_person_if_available() throws Exception {
        PersonService personService = new PersonService();
        Set<Person> persons = newHashSet(person(), person(), person());
        persons.stream().forEach(p -> personService.insert(p));

        Person person = persons.iterator().next();
        Optional<Person> optional = personService.findOne(person.getId());
        assertThat(optional).isNotNull();
        assertThat(optional.isPresent()).isTrue();
        assertThat(optional.get()).isEqualTo(person);
    }

    @Test
    void findOne_should_return_empty_optional_if_not_available() throws Exception {
        PersonService personService = new PersonService();
        Set<Person> persons = newHashSet(person(), person(), person());
        persons.stream().forEach(p -> personService.insert(p));

        Optional<Person> optional = personService.findOne(randomUUID().toString());
        assertThat(optional).isNotNull();
        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    void findOne_should_throw_exception_if_called_with_null() throws Exception {
        assertThatThrownBy(() -> new PersonService().findOne(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void delete_should_throw_exception_if_called_with_null() throws Exception {
        assertThatThrownBy(() -> new PersonService().delete(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void delete_should_return_true_if_person_is_known() throws Exception {
        PersonService personService = new PersonService();
        Set<Person> persons = newHashSet(person(), person(), person());
        persons.stream().forEach(p -> personService.insert(p));

        Person person = persons.iterator().next();
        assertThat(personService.delete(person.getId())).isTrue();

        Optional<Person> optional = personService.findOne(person.getId());
        assertThat(optional).isNotNull();
        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    void delete_should_return_false_if_person_is_not_known() throws Exception {
        PersonService personService = new PersonService();
        Set<Person> persons = newHashSet(person(), person(), person());
        persons.stream().forEach(p -> personService.insert(p));

        assertThat(personService.delete(randomUUID().toString())).isFalse();
    }

    @Test
    void insert_should_return_true_if_person_is_not_known_before() throws Exception {
        PersonService personService = new PersonService();
        Set<Person> persons = newHashSet(person(), person(), person());
        persons.stream().forEach(p -> personService.insert(p));

        Person person = person();
        assertThat(personService.insert(person)).isTrue();

        Set<Person> all = personService.findAll();
        assertThat(all.size()).isEqualTo(persons.size() + 1);
        assertThat(all).contains(person);
    }

    @Test
    void insert_should_return_false_if_person_is_already_known() throws Exception {
        PersonService personService = new PersonService();
        Set<Person> persons = newHashSet(person(), person(), person());
        persons.stream().forEach(p -> personService.insert(p));

        assertThat(personService.insert(persons.iterator().next())).isFalse();
        assertThat(personService.findAll().size()).isEqualTo(persons.size());
    }

    @Test
    void insert_should_throw_exception_if_called_with_null() throws Exception {
        assertThatThrownBy(() -> new PersonService().insert(null)).isInstanceOf(IllegalArgumentException.class);
    }

    private static Person person() {
        return new Person(randomUUID().toString(), randomAlphabetic(10), nextInt(1, 100));
    }
}
