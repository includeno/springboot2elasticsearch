package com.example.springbootdemo.controller.es;

import com.example.springbootdemo.dao.es.ESBookRepository;
import com.example.springbootdemo.dao.es.ESInvoiceRepository;
import com.example.springbootdemo.entity.Book;
import com.example.springbootdemo.entity.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/book")
public class BookRestController {
    @Autowired
    private ESBookRepository repo;

    @PostMapping("/createOrUpdateBook")
    public ResponseEntity<Object> createOrUpdateBook(@RequestBody Book book) throws IOException {
        Book response = repo.save(book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getBook")
    public ResponseEntity<Object> getBookById(@RequestParam String bookId) throws IOException {
        Book book = repo.findById(bookId).get();
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<Object> getAllBooks() throws IOException {
        List<Book> books = (List<Book>) repo.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<Object> deleteBookById(@RequestParam String bookId) throws IOException {
        repo.deleteById(bookId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
