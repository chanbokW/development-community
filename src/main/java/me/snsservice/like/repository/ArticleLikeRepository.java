package me.snsservice.like.repository;

import me.snsservice.article.domain.Article;
import me.snsservice.like.domain.ArticleLike;
import me.snsservice.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

    Optional<ArticleLike> findByArticleAndMember(Article article, Member member);
}
