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

import static java.util.Optional.empty;
import static java.util.Optional.of;

import static com.github.ingogriebsch.sample.spring.web.restcontroller.PersonController.PATH_DELETE;
import static com.github.ingogriebsch.sample.spring.web.restcontroller.PersonController.PATH_FIND_ALL;
import static com.github.ingogriebsch.sample.spring.web.restcontroller.PersonController.PATH_FIND_ONE;
import static com.github.ingogriebsch.sample.spring.web.restcontroller.PersonController.PATH_INSERT;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @Test
    public void findOne_shoud_return_status_ok_and_person_resource_if_available() throws Exception {
        Person person = new Person(randomAlphanumeric(8), "Kamil", 32);
        given(personService.findOne(person.getId())).willReturn(of(person));

        ResultActions actions = mockMvc.perform(get(PATH_FIND_ONE, person.getId()).accept(APPLICATION_JSON));
        actions.andExpect(status().isOk());

        actions.andExpect(content().contentType(APPLICATION_JSON));
        actions.andExpect(jsonPath("$.id", is(person.getId())));
        actions.andExpect(jsonPath("$.name", is(person.getName())));
        actions.andExpect(jsonPath("$.age", is(person.getAge())));

        verify(personService, times(1)).findOne(person.getId());
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void findOne_should_return_status_not_found_if_not_available() throws Exception {
        String id = randomAlphanumeric(8);
        given(personService.findOne(id)).willReturn(empty());

        ResultActions actions = mockMvc.perform(get(PATH_FIND_ONE, id).accept(APPLICATION_JSON));
        actions.andExpect(status().isNotFound());
        actions.andExpect(content().string(EMPTY));

        verify(personService, times(1)).findOne(id);
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void findAll_should_return_status_ok_and_person_resources_if_available() throws Exception {
        Set<Person> persons = newHashSet(new Person(randomAlphanumeric(8), "Ingo", 44),
            new Person(randomAlphanumeric(8), "Edina", 21), new Person(randomAlphanumeric(8), "Marcus", 37));
        given(personService.findAll()).willReturn(persons);

        ResultActions actions = mockMvc.perform(get(PATH_FIND_ALL).accept(APPLICATION_JSON));
        actions.andExpect(status().isOk());
        actions.andExpect(content().contentType(APPLICATION_JSON));
        actions.andExpect(jsonPath("$", not(Matchers.empty())));

        verify(personService, times(1)).findAll();
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void findAll_should_return_status_ok_but_no_resources_if_not_available() throws Exception {
        given(personService.findAll()).willReturn(newHashSet());

        ResultActions actions = mockMvc.perform(get(PATH_FIND_ALL).accept(APPLICATION_JSON));
        actions.andExpect(status().isOk());
        actions.andExpect(content().contentType(APPLICATION_JSON));
        actions.andExpect(jsonPath("$", Matchers.empty()));

        verify(personService, times(1)).findAll();
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void insert_should_return_status_created_if_not_known() throws Exception {
        Person person = new Person(randomAlphanumeric(8), "Kamil", 32);
        given(personService.insert(person)).willReturn(true);

        ResultActions actions =
            mockMvc.perform(post(PATH_INSERT).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(person)));
        actions.andExpect(status().isCreated());
        actions.andExpect(content().string(EMPTY));

        verify(personService, times(1)).insert(person);
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void insert_should_return_status_bad_request_if_already_known() throws Exception {
        Person person = new Person(randomAlphanumeric(8), "Kamil", 32);
        given(personService.insert(person)).willReturn(false);

        ResultActions actions =
            mockMvc.perform(post(PATH_INSERT).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(person)));
        actions.andExpect(status().isBadRequest());
        actions.andExpect(content().string(EMPTY));

        verify(personService, times(1)).insert(person);
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void delete_should_return_status_ok_if_known() throws Exception {
        String id = randomAlphanumeric(8);
        given(personService.delete(id)).willReturn(true);

        ResultActions actions = mockMvc.perform(delete(PATH_DELETE, id));
        actions.andExpect(status().isOk());
        actions.andExpect(content().string(EMPTY));

        verify(personService, times(1)).delete(id);
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void delete_should_return_not_found_if_not_known() throws Exception {
        String id = randomAlphanumeric(8);
        given(personService.delete(id)).willReturn(false);

        ResultActions actions = mockMvc.perform(delete(PATH_DELETE, id));
        actions.andExpect(status().isNotFound());
        actions.andExpect(content().string(EMPTY));

        verify(personService, times(1)).delete(id);
        verifyNoMoreInteractions(personService);
    }

}
