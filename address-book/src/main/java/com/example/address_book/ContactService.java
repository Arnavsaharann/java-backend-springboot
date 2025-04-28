package com.example.address_book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<ContactDTO> getAllContacts() {
        log.debug("Fetching all contacts from repository");
        List<ContactDTO> contacts = contactRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
        log.info("Retrieved {} contacts", contacts.size());
        return contacts;
    }

    public ContactDTO getContactById(Long id) {
        log.debug("Fetching contact with ID: {}", id);
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isPresent()) {
            log.info("Found contact with ID: {}", id);
            return toDTO(contact.get());
        } else {
            log.error("Contact with ID {} not found", id);
            throw new ContactNotFoundException("Contact with ID " + id + " not found");
        }
    }

    public ContactDTO createContact(ContactDTO contactDTO) {
        log.debug("Attempting to create contact: {}", contactDTO);
        Contact contact = toEntity(contactDTO);
        Contact savedContact = contactRepository.save(contact);
        log.info("Successfully created contact with ID: {}", savedContact.getId());
        return toDTO(savedContact);
    }

    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        log.debug("Attempting to update contact with ID: {}", id);
        if (!contactRepository.existsById(id)) {
            log.error("Contact with ID {} not found for update", id);
            throw new ContactNotFoundException("Contact with ID " + id + " not found");
        }
        Contact updatedContact = toEntity(contactDTO);
        updatedContact.setId(id);
        Contact savedContact = contactRepository.save(updatedContact);
        log.info("Successfully updated contact with ID: {}", id);
        return toDTO(savedContact);
    }

    public void deleteContact(Long id) {
        log.debug("Attempting to delete contact with ID: {}", id);
        if (!contactRepository.existsById(id)) {
            log.error("Contact with ID {} not found for deletion", id);
            throw new ContactNotFoundException("Contact with ID " + id + " not found");
        }
        contactRepository.deleteById(id);
        log.info("Successfully deleted contact with ID: {}", id);
    }

    private ContactDTO toDTO(Contact contact) {
        return new ContactDTO(contact.getName(), contact.getPhone(), contact.getCity());
    }

    private Contact toEntity(ContactDTO contactDTO) {
        return new Contact(null, contactDTO.getName(), contactDTO.getPhone(), contactDTO.getCity());
    }
}