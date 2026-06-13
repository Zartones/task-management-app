package online.taskmanagementapp.repository;

import online.taskmanagementapp.models.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long>,
        PagingAndSortingRepository<Attachment, Long> {
    Page<Attachment> findAllByTaskId(Long taskId, Pageable pageable);
}
