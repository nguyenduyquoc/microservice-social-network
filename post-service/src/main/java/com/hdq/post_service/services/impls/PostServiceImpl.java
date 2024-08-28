package com.hdq.post_service.services.impls;

import com.hdq.post_service.dtos.PostDTO;
import com.hdq.post_service.dtos.requests.PostFormRequest;
import com.hdq.post_service.dtos.responses.PageResponse;
import com.hdq.post_service.entities.PostEntity;
import com.hdq.post_service.exception.NotFoundEntityException;
import com.hdq.post_service.repositories.PostRepository;
import com.hdq.post_service.services.DateTimeFormatter;
import com.hdq.post_service.services.IPostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements IPostService {

    PostRepository repository;
    ModelMapper modelMapper;
    DateTimeFormatter dateTimeFormatter;

    @Override
    public PostDTO create(PostFormRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PostEntity post = PostEntity.builder()
                .content(request.getContent())
                .createDate(Instant.now())
                .modifiedDate(Instant.now())
                .accountId(Long.valueOf(authentication.getName()))
                .build();
        post = repository.save(post);

        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PageResponse<PostDTO> getMyPosts(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Sort sort = Sort.by("createDate").descending();
        Pageable pageable = PageRequest.of(page-1, size, sort);
        Page<PostEntity> pageData = repository.findByAccountId(Long.valueOf(authentication.getName()), pageable);

        var  postList = pageData.getContent().stream().map(postEntity -> {
            var postDTO =  modelMapper.map(postEntity, PostDTO.class);
            postDTO.setCreated(dateTimeFormatter.format(postEntity.getCreateDate()));
            return postDTO;
        }).toList();

        return PageResponse.<PostDTO>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(postList)
                .build();
    }

    @Override
    public PostDTO getByPostId(String postId) throws NotFoundEntityException {
        PostEntity post = repository.findById(postId).orElseThrow(
                () -> new NotFoundEntityException("Bài đăng", postId)
        );
        return modelMapper.map(post, PostDTO.class);
    }
}
