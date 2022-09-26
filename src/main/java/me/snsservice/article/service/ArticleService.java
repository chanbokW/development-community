package me.snsservice.article.service;

import lombok.RequiredArgsConstructor;
import me.snsservice.article.domain.Article;
import me.snsservice.article.dto.ArticleRequest;
import me.snsservice.article.dto.ArticleResponse;
import me.snsservice.article.dto.ArticleUpdateRequest;
import me.snsservice.article.repository.ArticleRepository;
import me.snsservice.member.domain.Member;
import me.snsservice.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createArticle(ArticleRequest articleRequest, Long loginId) {
        Member member = getMember(loginId);
        Article article = articleRepository.save(articleRequest.toEntity(member));

        //Todo 태그 저장 기능추가

        return article.getId();
    }

    @Transactional
    public ArticleResponse findById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다"));

        article.addViewCount();
        return ArticleResponse.of(article);
    }

    //Todo paging 처리
    @Transactional(readOnly = true)
    public List<ArticleResponse> findAll() {
        List<Article> articles = articleRepository.findAll();

        return articles.stream()
                .map(ArticleResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateArticle(ArticleUpdateRequest request, Long articleId, Long loginId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다"));

        if (!article.getMember().getId().equals(loginId)) {
            throw new IllegalStateException("해당 개시물에 수정 권한이 없습니다.");
        }

        article.updateArticle(request.getTitle(),request.getContent());
    }

    @Transactional
    public void deleteArticle(Long articleId, Long loginId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다"));

        if (!article.getMember().getId().equals(loginId)) {
            throw new IllegalStateException("해당 개시물에 수정 권한이 없습니다.");
        }

        article.deleteArticle();
    }

    private Member getMember(Long loginId) {
        return memberRepository.findById(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));
    }

}
