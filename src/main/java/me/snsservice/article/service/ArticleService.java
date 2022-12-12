package me.snsservice.article.service;

import lombok.RequiredArgsConstructor;
import me.snsservice.article.domain.Article;
import me.snsservice.article.dto.ArticleListResponse;
import me.snsservice.article.dto.ArticleResponse;
import me.snsservice.article.dto.CreateArticleRequest;
import me.snsservice.article.dto.UpdateArticleRequest;
import me.snsservice.article.repository.ArticleRepository;
import me.snsservice.common.exception.BusinessException;
import me.snsservice.member.domain.Member;
import me.snsservice.member.repository.MemberRepository;
import me.snsservice.tag.domain.Tag;
import me.snsservice.tag.domain.Tags;
import me.snsservice.tag.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static me.snsservice.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public Long createArticle(CreateArticleRequest createArticleRequest, Long memberId) {
        Member member = getMember(memberId);
        Article article = articleRepository.save(createArticleRequest.toEntity(member));
        Tags tags = createTags(createArticleRequest.getTags()); // 저장 태그
        article.addTags(tags);
        return article.getId();
    }

    // Todo tag관련 클래스로 옮기기
    private Tags createTags(List<String> tags) {
        Tags findTags = findTags(tags);
        Tags tag = removeAllTags(Tags.from(tags), findTags);
        List<Tag> saveTags = saveAllTags(tag);
        return addTags(new Tags(saveTags), findTags);
    }

    // Todo tag관련 클래스로 옮기기
    private Tags removeAllTags(Tags requestTags, Tags findTags) {
        Tags newTags = requestTags.removeAllByName(findTags);
        return newTags;
    }

    // Todo tag관련 클래스로 옮기기
    private List<Tag> saveAllTags(Tags newTags) {
        return tagRepository.saveAll(newTags.getTags());
    }

    // Todo tag관련 클래스로 옮기기
    private Tags addTags(Tags newTags, Tags findTags) {
        return findTags.addAllTags(newTags);
    }

    // Todo tag관련 클래스로 옮기기
    private Tags findTags(List<String> tagNames) {
        return new Tags(tagRepository.findByNameIn(tagNames));
    }

    @Transactional
    public ArticleResponse findById(Long articleId) {
        Article article = articleRepository.findByIdWithAll(articleId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_ARTICLE));
        article.addViewCount();
        return ArticleResponse.of(article);
    }

    //Todo paging 처리
    @Transactional(readOnly = true)
    public List<ArticleListResponse> findAll() {
        return articleRepository.findAllArticles().stream()
                .map(ArticleListResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ArticleListResponse> findAllWithArticle(Pageable pageable) {
        return articleRepository.findAllWithArticle(pageable);
    }

    @Transactional
    public void updateArticle(UpdateArticleRequest request, Long articleId, Long loginId) {
        Article article = getArticle(articleId);
        validateArticleMEmberIdAndMemberId(article, loginId);
        article.updateArticle(request.getTitle(), request.getContent());
    }

    @Transactional
    public void deleteArticle(Long articleId, Long loginId) {
        Article article = getArticle(articleId);
        validateArticleMEmberIdAndMemberId(article, loginId);
        article.deleteArticle();
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));
    }

    private Article getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_ARTICLE));
    }

    private void validateArticleMEmberIdAndMemberId(Article article, Long loginId) {
        if (!article.getMember().getId().equals(loginId)) {
            throw new BusinessException(UNAUTHORIZED_ARTICLE_MEMBER);
        }
    }
}
