package cn.liantao.lbook.service;

import cn.liantao.lbook.entity.Book;
import cn.liantao.lbook.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BookMapper bookMapper;

    // 获取所有书籍
    @Override
    public List<Book> getBooks() {
        List<Book> books = bookMapper.getBooks();
        return books;
    }

    @Override
    // 搜索书籍
    public List<Book> searchBooks(String text){
        String filter = "%"+text+"%";
        List<Book> books = bookMapper.searchBooks(filter);
        return books;
    }

    // 添加书籍
    @Override
    public void addBook(Book book) {
        bookMapper.addBook(book);
    }

    // 查找书籍
    @Override
    public Book getBook(String ISBN){
        Book book = bookMapper.getBook(ISBN);
        return book;
    }

    // 删除书籍
    @Override
    public int deleteBook(String ISBN) {
        int result = bookMapper.deleteBook(ISBN);
        return result;
    }

    // 修改书籍
    @Override
    public int modifyBook(Book book) {
        int result = bookMapper.modifyBook(book);
        return result;
    }

    // 获得书籍详细信息
    @Override
    public Book getDetail(String ISBN){
        Book book = bookMapper.getDetail(ISBN);
        return book;
    }
}
