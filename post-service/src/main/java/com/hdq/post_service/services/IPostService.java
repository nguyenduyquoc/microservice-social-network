package com.hdq.post_service.services;

import com.hdq.post_service.dtos.PostDTO;
import com.hdq.post_service.dtos.requests.PostFormRequest;
import com.hdq.post_service.dtos.responses.PageResponse;
import com.hdq.post_service.exception.NotFoundEntityException;

public interface IPostService {


    PostDTO create(PostFormRequest request);

    PageResponse<PostDTO> getMyPosts(int page, int size);

    PostDTO getByPostId(String postId) throws NotFoundEntityException;
}
