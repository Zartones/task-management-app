package online.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.taskmanagementapp.dto.comment.CommentRequestDto;
import online.taskmanagementapp.dto.comment.CommentResponseDto;
import online.taskmanagementapp.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("isAuthenticated()")
@Tag(name = "Comment management", description = "Endpoints for comments")
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create a new comment")
    public CommentResponseDto createComment(@Valid @RequestBody CommentRequestDto requestDto) {
        return commentService.create(requestDto);
    }

    @GetMapping
    @Operation(description = "Get all comments for a task")
    public Page<CommentResponseDto> getAllCommentsForTask(@RequestParam Long taskId,
                                                          Pageable pageable) {
        return commentService.getComments(taskId, pageable);
    }
}
