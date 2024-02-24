package com.example.ContactList.controllertest;

import com.example.ContactList.controller.ContactController;
import com.example.ContactList.entity.Contact;
import com.example.ContactList.entity.Person;
import com.example.ContactList.entity.database.Role;
import com.example.ContactList.entity.database.User;
import com.example.ContactList.repository.UserRepository;
import com.example.ContactList.security.RegisterRequest;
import com.example.ContactList.service.ContactService;
import com.example.ContactList.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    private final static String URL = "/api/v1/contacts";
    @Autowired
    private MockMvc mockMvc;

    private String token;
    private static String SECRET_KEY = "656kRJB8KFT4aNfoGNMPDXtYLH2cx25r+h4aYgJad2Q=";

    @InjectMocks
    private ContactController contactController;
    @MockBean
    private PersonService personService;
    @MockBean
    private ContactService contactService;
    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        when(userRepository.findByEmailWithContactLists(anyString()))
                .thenReturn(Optional.of(getUser()));
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        token = Jwts.builder()
                .claims(new HashMap<>())
                .subject("email@email.com")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .signWith(Keys.hmacShaKeyFor(keyByte), Jwts.SIG.HS256)
                .compact();
    }

    @Test
    public void getUserSuccessful() throws Exception {
        when(personService.getPerson(anyString()))
                .thenReturn(ResponseEntity.ok(getPerson()));

        MvcResult result = mockMvc.perform(get(URL + "/user")
                .param("email", "some@email.com")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void getUserWithoutContactList() throws Exception {
        Person p = getPerson();
        p.setContacts(null);
        when(personService.getPerson(anyString()))
                .thenReturn(ResponseEntity.ok(p));

        MvcResult result = mockMvc.perform(get(URL + "/user")
                .param("email", "some@email.com")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contacts").isEmpty())
                .andReturn();

        Assert.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void getUserWithoutAuthentication() throws Exception {
        MvcResult result = mockMvc.perform(get(URL + "/user")
                .param("email", "some@email.com"))
                .andExpect(status().isForbidden())
                .andReturn();

        Assert.assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    public void getAllContactListSuccess() throws Exception {
        when(contactService.getAllContactsByUser(anyString()))
                .thenReturn(ResponseEntity.ok(getAllContacts()));

        MvcResult result = mockMvc.perform(get(URL)
                .param("email", "some@email.com")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].contactName").value("contact 1"))
                .andReturn();

        verify(contactService, times(1)).getAllContactsByUser(anyString());
        Assert.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void getAllContactListEmpty() throws Exception {
        when(contactService.getAllContactsByUser(anyString()))
                .thenReturn(ResponseEntity.ok(new ArrayList<>()));

        MvcResult result = mockMvc.perform(get(URL)
                .param("email", "some@email.com")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn();

        verify(contactService, times(1)).getAllContactsByUser(anyString());
        Assert.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void saveContact() throws Exception {
        when(contactService.saveContact(anyString(), any()))
                .thenReturn(ResponseEntity.ok(getContact()));

        MvcResult result = mockMvc.perform(post(URL + "/contact")
                .param("email", "some@email.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getContact()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactName").value("contact 1"))
                .andExpect(jsonPath("$.contactEmail").value("contact1@email.com"))
                .andReturn();

        verify(contactService, times(1)).saveContact(anyString(), any());
    }

    @Test
    public void saveContactNameEmpty() throws Exception {
        Contact contact = getContact();
        contact.setContactName(null);
        when(contactService.saveContact(anyString(), any()))
                .thenReturn(ResponseEntity.badRequest().build());

        MvcResult result = mockMvc.perform(post(URL + "/contact")
                .param("email", "some@email.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(contact))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andReturn();

        verify(contactService, times(1)).saveContact(anyString(), any());
    }

    @Test
    public void deleteContact() throws Exception {
        when(contactService.deleteContact(anyString(), anyInt()))
                .thenReturn(ResponseEntity.ok().build());

        MvcResult result = mockMvc.perform(delete(URL + "/contact")
                .param("email", "some@email.com")
                .param("contactId", "1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        verify(contactService, times(1)).deleteContact(anyString(), anyInt());
    }

    @Test
    public void deleteContactUserNotFound() throws Exception {
        when(contactService.deleteContact(anyString(), anyInt()))
                .thenReturn(ResponseEntity.noContent().build());

        MvcResult result = mockMvc.perform(delete(URL + "/contact")
                .param("email", "some@email.com")
                .param("contactId", "1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(contactService, times(1)).deleteContact(anyString(), anyInt());
    }

    @Test
    public void getSingleContact() throws Exception {
        when(contactService.getContact(anyString(), anyInt()))
                .thenReturn(ResponseEntity.ok(getContact()));

        MvcResult result = mockMvc.perform(get(URL + "/contact")
                .param("email", "some@email.com")
                .param("contactId", "1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactName").value("contact 1"))
                .andExpect(jsonPath("$.contactEmail").value("contact1@email.com"))
                .andReturn();

        verify(contactService, times(1)).getContact(anyString(), anyInt());
    }

    @Test
    public void getContactNotFound() throws Exception {
        when(contactService.getContact(anyString(), anyInt()))
                .thenReturn(ResponseEntity.noContent().build());

        MvcResult result = mockMvc.perform(get(URL + "/contact")
                .param("email", "some@email.com")
                .param("contactId", "1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(contactService, times(1)).getContact(anyString(), anyInt());
    }

    @Test
    public void getContactUserNotFound() throws Exception {
        when(contactService.getContact(anyString(), anyInt()))
                .thenReturn(ResponseEntity.noContent().build());

        MvcResult result = mockMvc.perform(get(URL + "/contact")
                .param("email", "some@email.com")
                .param("contactId", "1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(contactService, times(1)).getContact(anyString(), anyInt());
    }

    @Test
    public void updateContact() throws Exception {
        when(contactService.updateContact(anyString(), any()))
                .thenReturn(ResponseEntity.ok(getContact()));

        MvcResult result = mockMvc.perform(put(URL + "/contact")
                .param("email", "some@email.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getContact()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactName").value("contact 1"))
                .andExpect(jsonPath("$.contactEmail").value("contact1@email.com"))
                .andReturn();

        verify(contactService, times(1)).updateContact(anyString(), any());
    }

    private RegisterRequest registerRequest() {
        return RegisterRequest.builder()
                .firstname("Some")
                .lastname("Lastname")
                .email("some@email.com")
                .password("1234")
                .build();
    }

    private Person getPerson() {
        return Person.builder()
                .contacts(new ArrayList<>())
                .email("some@email.com")
                .firstName("Some")
                .lastName("Person")
                .id(1)
                .build();
    }

    private User getUser() {
        return User.builder()
                .email("email@email.com")
                .password("1234")
                .firstName("Fulano")
                .lastName("da Silva")
                .contactLists(new ArrayList<>())
                .role(Role.USER)
                .id(1)
                .build();
    }

    private List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(Contact.builder()
                .contactId(1)
                .contactName("contact 1")
                .contactEmail("contact1@email.com")
                .build());
        contacts.add(Contact.builder()
                .contactId(2)
                .contactName("contact 2")
                .contactEmail("contact2@email.com")
                .build());
        return contacts;
    }

    private Contact getContact() {
        return Contact.builder()
                .contactId(1)
                .contactName("contact 1")
                .contactEmail("contact1@email.com")
                .build();
    }

    private String toJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

}
