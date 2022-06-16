package com.devhub.project2.dto;

import com.devhub.project2.entity.Book;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookDto implements Serializable {

    public Long id;
    public String bookName;
    public String bookAuthor;
    public String bookPages;

    public static BookDto entityToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.id = book.getId();
        bookDto.bookName = book.getBookName();
        bookDto.bookPages = book.getBookPages();
        bookDto.bookAuthor = book.getBookAuthor();
        return  bookDto;
    }
    public  static Book dtoToEntity(BookDto bookDto) {
       Book book = new Book();
       book.setBookPages(bookDto.bookPages);
       book.setBookAuthor(bookDto.bookAuthor);
       book.setBookName(bookDto.bookName);
       return book;
    }
    public static List<BookDto> listEntityToDto (List<Book> book) {
        List<BookDto> bookList = new ArrayList<>();
        for (int i = 0; i < book.size(); i++) {
            bookList.add(entityToDto(book.get(i)));
        }
        return bookList;
    }
}
