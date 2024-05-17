package com.example.Project_CRUD.repositories;

import com.example.Project_CRUD.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    
}
