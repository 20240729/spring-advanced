package org.example.expert.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.comment.service.CommentAdminService;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentAdminController {

    private final CommentAdminService commentAdminService;

    @DeleteMapping("/admin/comments/{commentId}")
    public void deleteComment(@PathVariable long commentId, @Auth AuthUser authUser) {
        commentAdminService.deleteComment(commentId);
    }
}