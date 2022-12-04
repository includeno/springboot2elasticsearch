package com.example.springbootdemo.controller.es;

import com.example.springbootdemo.dao.es.ESBookRepository;
import com.example.springbootdemo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private ESBookRepository repo;

    @GetMapping("/listBook")
    public String viewlistBookPage(Model model) throws IOException {
        model.addAttribute("listBookDocuments", repo.findAll());
        return "listBook";
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute("Book") Book book) throws IOException {
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        repo.save(book);
        return "redirect:/book/listBook";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") String id, Model model) throws IOException {
        Book book = repo.findById(id).get();
        book.setUpdateTime(new Date());
        model.addAttribute("Book", book);
        return "updateBook";
    }

    @GetMapping("/showNewBookForm")
    public String showNewBookForm(Model model) {
        // creating model attribute to bind form data
        Book book = new Book();
        model.addAttribute("Book", book);
        return "addBook";
    }

    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable(value = "id") String id) throws IOException {
        this.repo.deleteById(id);
        return "redirect:/book/listBook";
    }
}
