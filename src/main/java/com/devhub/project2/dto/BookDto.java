package com.devhub.project2.dto;

import com.devhub.project2.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookDto implements Serializable {

    public Long id;
    public String bookName;
    public String bookAuthor;
    public String bookPages;

    public MultipartFile file;

    public String pdfName;


    public static BookDto entityToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.id = book.getId();
        bookDto.bookName = book.getBookName();
        bookDto.bookPages = book.getBookPages();
        bookDto.bookAuthor = book.getBookAuthor();
        bookDto.pdfName = book.getPdfName();
        return bookDto;
    }

    public static Book dtoToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.id);
        book.setBookPages(bookDto.bookPages);
        book.setBookAuthor(bookDto.bookAuthor);
        book.setBookName(bookDto.bookName);
        if (bookDto.pdfName != null) {
            book.setPdfName(bookDto.pdfName);
        }
        return book;
    }

    public static List<BookDto> listEntityToDto(List<Book> book) {
        List<BookDto> bookList = new ArrayList<>();
        for (int i = 0; i < book.size(); i++) {
            bookList.add(entityToDto(book.get(i)));
        }
        return bookList;
    }

    public BookDto() {
    }

    public BookDto(String bookName, String bookAuthor, String bookPages, MultipartFile file) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPages = bookPages;
        this.file = file;
    }

    public BookDto(Long id, String bookName, String bookAuthor, String bookPages, MultipartFile file) {
        this.id = id;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPages = bookPages;
        this.file = file;
    }
}
