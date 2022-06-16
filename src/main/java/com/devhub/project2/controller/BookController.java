package com.devhub.project2.controller;

import com.devhub.project2.dto.BookDto;
import com.devhub.project2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bookApp")
public class BookController {
    @Autowired
    BookService bookService;



    @RequestMapping(value="/addBook",  method = RequestMethod.POST)
    public  BookDto addBook( @RequestBody BookDto bookDto){
        return bookService.save(bookDto);
    }
    @RequestMapping(value="/getBooks",  method = RequestMethod.GET)
    public List<BookDto> getBooks(){
            return bookService.getBooks();
    }
}
