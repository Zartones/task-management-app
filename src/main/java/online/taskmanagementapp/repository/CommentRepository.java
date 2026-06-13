package online.taskmanagementapp.repository;

import online.taskmanagementapp.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>,
        PagingAndSortingRepository<Comment, Long> {
    Page<Comment> findAllByTaskId(Long taskId, Pageable pageable);
}
