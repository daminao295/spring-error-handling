package com.springsecurity.demo.controller;


import com.springsecurity.demo.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private List<Book> books;

    public BookController() {
        books = new ArrayList<>();
        books.add(new Book("ksiazka1", "Author1"));
        books.add(new Book("ksiazka2", "Author2"));
    }

    @GetMapping("/books")
    public List<Book> getBooks(){
        return books;
    }

    @GetMapping("/admin")
    public List<Book> getBooksAdmin(){
        return books;
    }

    @GetMapping("/moderator")
    public List<Book> getBooksModerator(){
        return books;
    }

    @GetMapping("/user")
    public List<Book> getBooksUser(){
        return books;
    }

}
