package com.company.bookStore.repository;

import com.company.bookStore.model.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByTitleIgnoreCase(String title);

    @Cacheable(cacheNames = "book-cache")
    @Override
    Optional<Book> findById(Long aLong);

    List<Book> findAllByOrderByPublishedYearDesc();

    Page<Book> findAll(Pageable pageable);

    Optional<Book> findBookByTitleAndPublishedYear(String title, Long publishedYear);

    @Query("from Book where title=:title")
    List<Book> findBooksForTitle(@Param("title") String title);

    @Query("from Book where publishedYear >= :start and publishedYear <= :end")
    List<Book> findBooksForPublishedYearYearStartEnd(@Param("start") Long start,
                                                     @Param("end") Long end);

    @Query("select title, publishedYear from Book")
    List<Object[]> findAllBooksTitlePublishedYear();

}