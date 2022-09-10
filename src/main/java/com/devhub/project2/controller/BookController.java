package com.devhub.project2.controller;

import com.devhub.project2.dto.BookDto;
import com.devhub.project2.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bookApp")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = {"multipart/form-data"})
    public BookDto addBook(@RequestParam String bookName, @RequestParam String bookAuthor, @RequestParam String bookPages, @RequestParam(required = false) MultipartFile pdf) throws IOException {
        return bookService.save(bookName, bookAuthor, bookPages, pdf);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public List<BookDto> getBooks() {
        return bookService.getBooks();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(consumes = {"multipart/form-data"})
    public void updateBook(@RequestParam String id, @RequestParam String bookName, @RequestParam String bookAuthor, @RequestParam String bookPages, @RequestParam(required = false) MultipartFile pdf) throws IOException {
        bookService.update(id, bookName, bookAuthor, bookPages, pdf);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable String id) {
        bookService.delete(id);
    }

    @GetMapping("/download/{pdfName}")
    public ResponseEntity<Resource> download(@PathVariable String pdfName) {
        return bookService.download(pdfName);
    }

}
