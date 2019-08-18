package cn.liantao.lbook.mapper;

import cn.liantao.lbook.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMapper {
    Book getBook(String ISBN);
    List<Book> getBooks();
    List<Book> searchBooks(String filter);
    int deleteBook(String ISBN);
    void addBook(Book book);
    int modifyBook(Book book);
    Book getDetail(String ISBN);
}
