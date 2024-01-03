package com.bookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookListService;

import java.util.*;

//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;

@Controller
@RestController
public class BookController {
	
	@Autowired
	private BookService service;
	
	@Autowired
	private MyBookListService myBookService;
	
	@RequestMapping("/")
	public ModelAndView home() {
		ModelAndView modelAndViewName = new ModelAndView();
		modelAndViewName.setViewName("home");
		return modelAndViewName;
	}
	
	@GetMapping("/book_register")
	public ModelAndView bookRegister() {
		ModelAndView modelAndViewName = new ModelAndView();
		modelAndViewName.setViewName("bookRegister");
		return modelAndViewName;
	}
	
	@GetMapping("/available_books")
	public ModelAndView getAllBook() {
		List<Book>list=service.getAllBook();
		ModelAndView m=new ModelAndView();
		m.setViewName("bookList");
		m.addObject("book",list);
		return new ModelAndView("bookList","book",list);
	}
	
	@PostMapping("/save")
	public ModelAndView addBook(@ModelAttribute Book b) {
		service.save(b);
		ModelAndView modelAndViewName = new ModelAndView();
		modelAndViewName.setViewName("redirect:/available_books");
		return modelAndViewName;
		
//		return "redirect:/available_books";
	}
	@GetMapping("/my_books")
	public ModelAndView getMyBooks(Model model)
	{
		List<MyBookList>list=myBookService.getAllMyBooks();
		model.addAttribute("book",list);
		ModelAndView modelAndViewName = new ModelAndView();
		modelAndViewName.setViewName("myBooks");
	return modelAndViewName;
	}
	@RequestMapping("/mylist/{id}")
	public String getMyList(@PathVariable("id") int id) {
		Book b=service.getBookById(id);
		MyBookList mb=new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
		myBookService.saveMyBooks(mb);
		return "redirect:/my_books";
	}
	
	@RequestMapping("/editbook/{id}")
	public String editBook(@PathVariable("id") int id,Model model) {
		Book b=service.getBookById(id);
		model.addAttribute("book",b);
		return "bookEdit";
	}
	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id")int id) {
		service.deleteById(id);
		return "redirect:/available_books";
	}
	
//	 @RequestMapping("/error")
//	    public String handleError(Model model, HttpServletRequest request) {
//	        // Get the error status
//	        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//	        if (status != null) {
//	            int statusCode = Integer.parseInt(status.toString());
//
//	            // Handle different error status codes
//	            if (statusCode == HttpStatus.NOT_FOUND.value()) {
//	                model.addAttribute("error", "404 Not Found");
//	            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//	                model.addAttribute("error", "500 Internal Server Error");
//	            } else {
//	                model.addAttribute("error", "Something went wrong");
//	            }
//	        } else {
//	            model.addAttribute("error", "Unknown error");
//	        }
//
//	        return "error";
//	    }
	
}
