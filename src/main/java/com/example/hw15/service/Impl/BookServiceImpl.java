package com.example.hw15.service.Impl;

import com.example.hw15.dto.BookDto;
import com.example.hw15.repository.BookRepository;
import com.example.hw15.repository.entity.Book;
import com.example.hw15.service.BookService;
import com.example.hw15.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book creatingBook = modelMapper.map(bookDto, Book.class);
        return modelMapper.map(bookRepository.save(creatingBook), BookDto.class);
    }

    @Override
    public BookDto findBookById(Long bookId) {
        return modelMapper.map(bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id : " + bookId + " not found")), BookDto.class);
    }

    @Override
    public List<BookDto> getAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBookById(Long bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
        } else {
            throw new RuntimeException("Book with id : " + bookId + " not found");
        }
    }
}
