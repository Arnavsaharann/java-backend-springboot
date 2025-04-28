package com.example.address_book;

import org.springframework.data.jpa.repository.JpaRepository;



public interface ContactRepository extends JpaRepository<Contact, Long> {
}