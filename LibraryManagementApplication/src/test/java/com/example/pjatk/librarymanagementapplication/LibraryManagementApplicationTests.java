package com.example.pjatk.librarymanagementapplication;

import com.example.pjatk.librarymanagementapplication.model.User;
import com.example.pjatk.librarymanagementapplication.model.enums.BookCondition;
import com.example.pjatk.librarymanagementapplication.model.enums.MembershipType;
import com.example.pjatk.librarymanagementapplication.repository.UserRepository;
import com.example.pjatk.librarymanagementapplication.service.BookService;
import com.example.pjatk.librarymanagementapplication.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.pjatk.librarymanagementapplication.model.Book;
import com.example.pjatk.librarymanagementapplication.model.enums.Category;
import com.example.pjatk.librarymanagementapplication.repository.BookRepository;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createBook_shouldCreateBook_WhenProvidedCorrectData() {
        //Given
        Book b = new Book("Title", new ArrayList<>(), 123L, Category.ACTION);
        when(bookRepository.save(any())).thenReturn(b);

        //When
        Book createdBook = bookService.createBook(b);

        //Then
        assertNotNull(createdBook);
        verify(bookRepository, times(1)).save(b);
    }

    @Test
    void createBook_shouldThrowException_WhenBookCreatedWithoutISBN() {
        //Given
        Book b = new Book();
        when(bookRepository.existsById(any())).thenReturn(false);

        //When //Then
        assertThrows(IllegalArgumentException.class, () -> bookService.createBook(b));


    }

    @Test
    void getBookById_shouldGetBookById_WhenProvideCorrectData() {
        //Given
        Optional<Book> b = Optional.of(new Book());
        when(bookRepository.existsById(any())).thenReturn(true);
        when(bookRepository.findById(any())).thenReturn(b);

        //When
        Book actualBook = bookService.getBookById(1L);

        //Then
        assertEquals(actualBook, b.get());
        verify(bookRepository, times(1)).existsById(any());
        verify(bookRepository, atLeast(1)).findById(any());
    }

    @Test
    void getBookById_shouldThrowException_WhenTryingFindBookByIdWhichNotExist() {
        //Given
        Optional<Book> b = Optional.of(new Book());
        when(bookRepository.existsById(any())).thenReturn(false);

        //When //Then
        assertThrows(IllegalArgumentException.class, () -> bookService.getBookById(1L));
    }

    @Test
    void updateBook_shouldUpdateBook_WhenPassedCorrectInput() {
        //Given
        Book actualBook = new Book("Test", new ArrayList<>(), 1L, Category.ACTION);
        Book updatedBook = new Book("Test", new ArrayList<>(), 1L, Category.HORROR);
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.findById(any())).thenReturn(Optional.of(actualBook));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //Then
        actualBook = bookService.updateBook(1L, updatedBook);

        //Then
        assertNotNull(actualBook);
        assertEquals(Category.HORROR, actualBook.getBookType());
    }

    @Test
    void updateBook_shouldNotUpdateBook_WhenUpdatedBookTypeIsNull() {
        //Given
        Book actualBook = new Book("Test", new ArrayList<>(), 1L, Category.ACTION);
        Book updatedBook = new Book("Test", new ArrayList<>(), 1L, null);
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.findById(any())).thenReturn(Optional.of(actualBook));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //Then
        actualBook = bookService.updateBook(1L, updatedBook);

        //Then
        assertNotNull(actualBook);
        assertEquals(Category.ACTION, actualBook.getBookType());
    }

    @Test
    void deleteBook_shouldDeleteBook_WhenDeletedBookExists() {
        //Given
        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(new Book()));

        //When
        bookService.deleteBook(1L);

        //Then
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteBook_shouldThrowException_WhenTryingToDeleteBookWhichNotExists() {
        //Given
        when(bookRepository.existsById(anyLong())).thenReturn(false);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(new Book()));

        //When //Then
        assertThrows(IllegalArgumentException.class, () -> bookService.deleteBook(1L));

    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks_WhenBooksExist() {
        //Given
        Book book1 = new Book("Test", new ArrayList<>(), 1L, Category.HORROR);
        Book book2 = new Book("Test", new ArrayList<>(), 2L, Category.ACTION);

        List<Book> mockBooks = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(mockBooks);

        //When
        List<Book> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(Category.HORROR, result.get(0).getBookType());
        assertEquals(Category.ACTION, result.get(1).getBookType());
        verify(bookRepository).findAll();
    }

    @Test
    void getAllBooks_ShouldReturnEmptyList_WhenNoBooksExist() {
        // Given
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Book> result = bookService.getAllBooks();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookRepository).findAll();
    }

    @Test
    void createBook_shouldSetBookCondition_WhenProvided() {
        // Given
        Book book = new Book("Test Book", new ArrayList<>(), 12345L, Category.ACTION);
        book.setBookCondition(BookCondition.NEW);
        when(bookRepository.save(any())).thenReturn(book);

        // When
        Book createdBook = bookService.createBook(book);

        // Then
        assertNotNull(createdBook);
        assertEquals(BookCondition.NEW, createdBook.getBookCondition());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook_shouldUpdateBookCondition_WhenProvidedCorrectInput() {
        // Given
        Book actualBook = new Book("Test", new ArrayList<>(), 1L, Category.ACTION);
        actualBook.setBookCondition(BookCondition.GOOD);
        Book updatedBook = new Book("Test", new ArrayList<>(), 1L, Category.ACTION);
        updatedBook.setBookCondition(BookCondition.FAIR);

        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.findById(any())).thenReturn(Optional.of(actualBook));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Book result = bookService.updateBook(1L, updatedBook);

        // Then
        assertNotNull(result);
        assertEquals(BookCondition.FAIR, result.getBookCondition());
        verify(bookRepository, times(1)).save(updatedBook);
    }

    @Test
    void createUser_shouldSetMembershipType_WhenProvided() {
        // Given
        User user = new User("test@example.com");
        user.setMembershipType(MembershipType.PREMIUM);

        // When
        userService.createUser(user);

        // Then
        assertNotNull(user);
        assertEquals(MembershipType.PREMIUM, user.getMembershipType());
    }

    @Test
    void updateUser_shouldUpdateMembershipType_WhenProvidedCorrectInput() {
        // Given: Stwórz użytkownika z ID = 1
        User existingUser = new User("email@example.com");
        existingUser.setId(1L);
        existingUser.setMembershipType(MembershipType.STANDARD);  // Dodaj MembershipType
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        // Given: Stwórz nowego użytkownika z nowym MembershipType
        User updatedUser = new User("email@example.com");
        updatedUser.setId(1L);
        updatedUser.setMembershipType(MembershipType.PREMIUM);  // Nowe MembershipType

        // When: Aktualizacja użytkownika
        User actualUser = userService.updateUser(1L, updatedUser);

        // Then: Sprawdź, czy MembershipType zostało zaktualizowane
        assertNotNull(actualUser);
        assertEquals(MembershipType.PREMIUM, actualUser.getMembershipType());
    }


}
