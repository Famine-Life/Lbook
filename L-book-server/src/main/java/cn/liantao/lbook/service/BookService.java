package cn.liantao.lbook.service;

import cn.liantao.lbook.entity.Book;

import java.util.List;

public interface BookService {
    // 获取所有书籍
    List<Book> getBooks();

    // 搜索书籍
    List<Book> searchBooks(String text);

    // 添加书籍
    void addBook(Book book);

    // 查找书籍
    Book getBook(String ISBN);

    // 删除书籍
    int deleteBook(String ISBN);

    // 修改书籍
    int modifyBook(Book book);

    // 获得书籍详细信息
    Book getDetail(String ISBN);
}
