package com.example.hw15.repository;

import com.example.hw15.repository.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
