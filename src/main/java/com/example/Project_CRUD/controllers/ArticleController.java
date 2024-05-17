package com.example.Project_CRUD.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Project_CRUD.models.*;
import com.example.Project_CRUD.repositories.ArticleRepository;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

	@Autowired

	private ArticleRepository articleRepository;

	@GetMapping("/")
	public ResponseEntity<List<Article>> getAllArticles() {
		List<Article> articles = articleRepository.findAll();
		return ResponseEntity.ok(articles);
	}

	@PostMapping("/")
	public ResponseEntity<Article> createArticle(@jakarta.validation.Valid @RequestBody Article article) {
		Article savedArticle = articleRepository.save(article);
		return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
	}

	// Get article by ID
	@GetMapping("/{id}")
	public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
		try {
			Article article = articleRepository.findById(id)
					.orElseThrow(() -> new NoSuchElementException("Article not found with id " + id));
			return ResponseEntity.ok(article);
		} catch (NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Article> updateArticle(@PathVariable Long id,
			@jakarta.validation.Valid @RequestBody Article articleDetails) {
		try {
			Article existingArticle = articleRepository.findById(id)
					.orElseThrow(() -> new NoSuchElementException("Article not found with id " + id));

			existingArticle.setTitle(articleDetails.getTitle());
			existingArticle.setDescription(articleDetails.getDescription());
			existingArticle.setArticleDate(articleDetails.getArticleDate());

			Article updatedArticle = articleRepository.save(existingArticle);
			return ResponseEntity.ok(updatedArticle);
		} catch (NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
		try {
			articleRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
