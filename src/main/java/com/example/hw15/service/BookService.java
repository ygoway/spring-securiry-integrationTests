package com.example.hw15.service;

import com.example.hw15.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto bookDto);

    BookDto findBookById(Long bookId);

    List<BookDto> getAllBooks();

    void deleteBookById(Long bookId);
}
