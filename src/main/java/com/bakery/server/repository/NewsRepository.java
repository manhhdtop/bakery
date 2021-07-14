package com.bakery.server.repository;

import com.bakery.server.entity.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    Page<NewsEntity> findByNameContaining(String name, Pageable pageable);

    @Query(value = "SELECT * FROM news c WHERE c.slug  REGEXP concat('^', :slug, '(?:-#[0-9]+)?$') ORDER BY c.slug DESC LIMIT 1", nativeQuery = true)
    NewsEntity findBySlugLike(String slug);

    @Query("SELECT n FROM NewsEntity n WHERE n.slug=:slug")
    NewsEntity findBySlug(String slug);

    @Modifying
    @Query("UPDATE NewsEntity n SET n.read=n.read+1 WHERE n.id=:id")
    void readNews(Long id);

    @Modifying
    @Query("UPDATE NewsEntity n SET n.like=n.like+1 WHERE n.id=:id")
    void likeNews(Long id);

    @Query("SELECT n FROM NewsEntity n WHERE n.status=1")
    Page<NewsEntity> getActiveNews(Pageable pageable);
}
