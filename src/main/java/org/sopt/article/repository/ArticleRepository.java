package org.sopt.article.repository;

import org.sopt.article.entity.Article;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByTitle(String title);

    Optional<Article> findByTitle(String title);

    List<Article> findByTitleContainingAndMember_NameContaining(String title, String name);

}
