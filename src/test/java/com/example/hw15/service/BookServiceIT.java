package com.example.hw15.service;

import com.example.hw15.dto.BookDto;
import com.example.hw15.repository.BookRepository;
import com.example.hw15.repository.entity.Book;
import com.example.hw15.service.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class BookServiceIT {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookService bookService;

    @AfterEach
    public void cleanDB() {
        bookRepository.deleteAll();
    }

    @Test
    public void findBookByIdBookExistsReturnsBookDto() {
        var book = new Book(1L, "Test Book", "Test Author", 200);

        bookRepository.save(book);

        BookDto bookDto = new BookDto();
        bookDto.setName("Test Book");
        bookDto.setAuthor("Test Author");

        var result = bookService.findBookById(book.getBookId());

        assertEquals(bookDto, result);
    }

    @Test
    public void findBookByIdBookNotExistsThrowsNotFoundException() {
        Long bookId = 2L;

        Assertions.assertThrows(NotFoundException.class, () -> bookService.findBookById(bookId));
    }

}
