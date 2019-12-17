/*-
 * #%L
 * Spring Web simple REST controller sample
 * %%
 * Copyright (C) 2018 - 2019 Ingo Griebsch
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

import static java.util.UUID.randomUUID;

import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;

import org.junit.Test;

public class PersonServiceTest {

    @Test
    public void findAll_should_return_available_persons() throws Exception {
        PersonService personService = new PersonService();
        Set<Person> persons = newHashSet(person(), person(), person());
        persons.stream().forEach(p -> personService.insert(p));
        assertThat(personService.findAll()).isNotNull().containsAll(persons);
    }

    @Test
    public void findOne_should_return_matching_person_if_available() throws Exception {
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
    public void findOne_should_return_empty_optional_if_not_available() throws Exception {
        PersonService personService = new PersonService();
        Set<Person> persons = newHashSet(person(), person(), person());
        persons.stream().forEach(p -> personService.insert(p));

        Optional<Person> optional = personService.findOne(randomUUID().toString());
        assertThat(optional).isNotNull();
        assertThat(optional.isPresent()).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void findOne_should_throw_exception_if_called_with_null() throws Exception {
        new PersonService().findOne(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_should_throw_exception_if_called_with_null() throws Exception {
        new PersonService().delete(null);
    }

    @Test
    public void delete_should_return_true_if_person_is_known() throws Exception {
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
    public void delete_should_return_false_if_person_is_not_known() throws Exception {
        PersonService personService = new PersonService();
        Set<Person> persons = newHashSet(person(), person(), person());
        persons.stream().forEach(p -> personService.insert(p));

        assertThat(personService.delete(randomUUID().toString())).isFalse();
    }

    @Test
    public void insert_should_return_true_if_person_is_not_known_before() throws Exception {
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
    public void insert_should_return_false_if_person_is_already_known() throws Exception {
        PersonService personService = new PersonService();
        Set<Person> persons = newHashSet(person(), person(), person());
        persons.stream().forEach(p -> personService.insert(p));

        assertThat(personService.insert(persons.iterator().next())).isFalse();
        assertThat(personService.findAll().size()).isEqualTo(persons.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void insert_should_throw_exception_if_called_with_null() throws Exception {
        new PersonService().insert(null);
    }

    private static Person person() {
        return new Person(randomUUID().toString(), randomAlphabetic(10), nextInt(1, 100));
    }
}
