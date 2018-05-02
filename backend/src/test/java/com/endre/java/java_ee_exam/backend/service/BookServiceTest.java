package com.endre.java.java_ee_exam.backend.service;

import com.endre.java.java_ee_exam.backend.StubApplication;
import com.endre.java.java_ee_exam.backend.entity.Book;
import com.endre.java.java_ee_exam.backend.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookServiceTest extends ServiceTestBase{


    @Autowired
    BookService bookService;



    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueBookTitle(){return "defaultTitle_" + counter.getAndIncrement();}

    //TODO: Ikke bare create book men persist en default om denne metoden blir kalt.
    private Book createDefaultBook(){
        Book book = new Book();
        book.setTitle(getUniqueBookTitle());
        book.setUsed(false);
        book.setAuthor("TestAuthor");
        book.setCourse("TestCourse");
        return book;
    }

    @Test
    public void testCreateBook() {
        Book book = createDefaultBook();

        assertTrue(bookService.createBook(
                book.getTitle(),
                book.getAuthor(),
                book.getCourse(),
                book.isUsed()));
    }


    @Test
    public void testCreateDuplicateBook() {
        Book book = createDefaultBook();

        assertTrue(bookService.createBook(
                book.getTitle(),
                book.getAuthor(),
                book.getCourse(),
                book.isUsed()));

        assertFalse(bookService.createBook(
                book.getTitle(),
                book.getAuthor(),
                book.getCourse(),
                book.isUsed()));
    }

    @Test
    public void testGetAllBooksIfNonExist() {
        assertNull(bookService.getAllBooks());
    }

    @Test
    public void testGetAllBooks() {
        Book book = createDefaultBook();

        assertTrue(bookService.createBook(
                book.getTitle(),
                book.getAuthor(),
                book.getCourse(),
                book.isUsed()));

        assertTrue(bookService.getAllBooks().size() > 0);
    }

    @Test
    public void testCreateBookWithSeller() {
        Book book = createDefaultBook();

        assertTrue(bookService.createBook(
                book.getTitle(),
                book.getAuthor(),
                book.getCourse(),
                book.isUsed()));

        String email = "mee@mee.com";

        assertNull(bookService.getAllBooksForSale());

        assertTrue(bookService.addUserTooBook(email, book.getTitle()));

        assertEquals(1, bookService.getAllBooksForSale().size());

        //Able to load user email
        assertTrue(bookService.getAllBooksForSale().get(0).getUsers().contains(email));

    }

    @Test
    public void testRemoveUserFromBook() {
        Book book = createDefaultBook();

        assertTrue(bookService.createBook(
                book.getTitle(),
                book.getAuthor(),
                book.getCourse(),
                book.isUsed()));

        String email = "mee@mee.com";

        //Adding user to book
        assertTrue(bookService.addUserTooBook(email, book.getTitle()));

        assertTrue(bookService.getBook(book.getTitle()).getUsers().contains(email));

        assertTrue(bookService.removeUserFromBook(email, book.getTitle()));

        //Not containing that user anymore
        assertFalse(bookService.getBook(book.getTitle()).getUsers().contains(email));

    }

    @Test
    public void testGetBook() {
        Book book = createDefaultBook();

        assertTrue(bookService.createBook(
                book.getTitle(),
                book.getAuthor(),
                book.getCourse(),
                book.isUsed()));

        assertNotNull(bookService.getBook(book.getTitle()));
    }

    @Test
    public void testGetNonExistingBook() {
        assertNull(bookService.getBook(getUniqueBookTitle()));
    }

    @Test
    public void testDeleteBook() {
        Book book = createDefaultBook();

        assertTrue(bookService.createBook(
                book.getTitle(),
                book.getAuthor(),
                book.getCourse(),
                book.isUsed()));

        assertTrue(bookService.deleteBook(book.getTitle()));
    }

    @Test
    public void testDeleteNonExistingBook() {
        assertFalse(bookService.deleteBook(getUniqueBookTitle()));
    }


    @Test
    public void testAddUserToBookIfNoBookExist() {
        assertFalse(bookService.addUserTooBook("me@me.com", getUniqueBookTitle()));
    }


    @Test
    public void testRemoveUserIfBookNotExist() {
        assertFalse(bookService.removeUserFromBook("me@me.com", getUniqueBookTitle()));
    }


    @Test
    public void testAddUserIfUserAlreadyExist() {

        Book book = createDefaultBook();

        assertTrue(bookService.createBook(
                book.getTitle(),
                book.getAuthor(),
                book.getCourse(),
                book.isUsed()));

        String userEmail = "me@me.com";

        assertTrue(bookService.addUserTooBook(userEmail, book.getTitle()));

        assertFalse(bookService.addUserTooBook(userEmail, book.getTitle()));
    }

    @Test
    public void testRemoveUserIfUserNotExist() {

        Book book = createDefaultBook();

        assertTrue(bookService.createBook(
                book.getTitle(),
                book.getAuthor(),
                book.getCourse(),
                book.isUsed()));

        String userEmail = "me@me.com";

        assertFalse(bookService.removeUserFromBook(userEmail, book.getTitle()));
    }
}