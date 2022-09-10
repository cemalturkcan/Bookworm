package com.devhub.project2.service;

import com.devhub.project2.dto.BookDto;
import com.devhub.project2.entity.Book;
import com.devhub.project2.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookDto save(String bookName, String bookAuthor, String bookPages, MultipartFile pdf) throws IOException {
        BookDto bookDto = new BookDto(bookName, bookAuthor, bookPages, pdf);
        Book book = BookDto.dtoToEntity(bookDto);
        book = bookRepository.save(book);
        book.setPdfName(saveMultiPartFile(bookDto.file, book.getId()));
        book = bookRepository.save(book);
        return BookDto.entityToDto(book);
    }

    public List<BookDto> getBooks() {
        return BookDto.listEntityToDto(bookRepository.findAll());
    }

    public void update(String id, String bookName, String bookAuthor, String bookPages, MultipartFile pdf) {
        Book book = bookRepository.findById(Long.valueOf(id)).orElseThrow(EntityNotFoundException::new);
        BookDto bookDto = new BookDto(Long.valueOf(id), bookName, bookAuthor, bookPages, pdf);
        if (pdf == null) {
            bookDto.pdfName = book.getPdfName();
        } else {
            bookDto.pdfName = saveMultiPartFile(pdf, book.getId());
        }
        book = BookDto.dtoToEntity(bookDto);
        bookRepository.save(book);
    }

    public void delete(String id) {
        bookRepository.deleteById(Long.valueOf(id));
    }

    public ResponseEntity<Resource> download(@PathVariable String pdfName) {
        if (Objects.equals(pdfName, "empty")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        FileSystemResource resource = new FileSystemResource("C:/pdfStorage/" + pdfName);
        MediaType mediaType = MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        ContentDisposition disposition = ContentDisposition.inline().build();
        headers.setContentDisposition(disposition);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    public String saveMultiPartFile(MultipartFile file, Long id) {
        File f = new File("C:/pdfStorage");
        if (!f.exists()) {
            f.mkdirs();
        }
        if (file == null) {
            return "empty";
        }
        Path rootLocation = Paths.get("C:\\pdfStorage");
        String fileName = null;
        fileName = "" + id + file.getOriginalFilename();
        try {
            if (file.isEmpty()) {
                throw new IOException("File is empty");
            }
            Files.copy(file.getInputStream(), rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);


        } catch (IOException e) {
            throw new RuntimeException("Failed to save PDF file");
        }
        return fileName;
    }
}

