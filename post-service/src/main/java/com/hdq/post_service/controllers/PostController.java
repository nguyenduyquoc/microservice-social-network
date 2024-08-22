package com.hdq.post_service.controllers;

import com.hdq.post_service.core.BaseResponse;
import com.hdq.post_service.dtos.requests.PostFormRequest;
import com.hdq.post_service.exception.NotFoundEntityException;
import com.hdq.post_service.services.IPostService;
import com.hdq.post_service.utils.Constants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.REQUEST_MAPPING_PREFIX + Constants.VERSION_API_V1 + "/posts")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {

    IPostService postService;

    @GetMapping(value = "/me")
    BaseResponse myPosts(@RequestParam(value = "page", required = false,defaultValue = "1") int page,
                         @RequestParam(value = "size", required = false,defaultValue = "10") int size
                         ) {

        return BaseResponse.success(postService.getMyPosts(page, size));
    }

    @GetMapping(value = "/{postId}")
    BaseResponse show(@PathVariable(name = "postId") String postId) {

        try {
            return BaseResponse.success(postService.getByPostId(postId));
        } catch (NotFoundEntityException e) {
            return BaseResponse.throwException(e);
        }
    }


    @PostMapping(value = "")
    BaseResponse create(@RequestBody PostFormRequest request) {

        return BaseResponse.created(postService.create(request));
    }
}