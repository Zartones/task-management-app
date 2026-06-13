package online.taskmanagementapp.repository;

import java.util.Optional;
import online.taskmanagementapp.models.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectRepository extends JpaRepository<Project, Long>,
        PagingAndSortingRepository<Project, Long> {
    Page<Project> findAllByUserId(Long id, Pageable pageable);

    Optional<Project> findByUserIdAndId(Long userId, Long id);

    @Modifying
    @Transactional
    void deleteByUserIdAndId(Long userId, Long id);
}
