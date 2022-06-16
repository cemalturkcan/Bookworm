package com.devhub.project2.service;

import com.devhub.project2.dto.BookDto;
import com.devhub.project2.entity.Book;

import com.devhub.project2.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public BookDto save (BookDto bookDto) {
        Book book =  bookDto.dtoToEntity(bookDto);
        book = bookRepository.save(book);
        return bookDto.entityToDto(book);
    }
    public List<BookDto> getBooks (){
         List<BookDto> bookList = BookDto.listEntityToDto(bookRepository.findAll());
         return bookList;
    }
}
