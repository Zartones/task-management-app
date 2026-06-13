package online.taskmanagementapp.repository;

import online.taskmanagementapp.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TaskRepository extends JpaRepository<Task, Long>,
        PagingAndSortingRepository<Task, Long> {

    Page<Task> findAllByProjectId(Long id, Pageable pageable);

    @Modifying
    @Transactional
    void deleteById(Long id);
}
