package online.taskmanagementapp.repository;

import online.taskmanagementapp.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LabelRepository extends JpaRepository<Label, Long>,
        PagingAndSortingRepository<Label, Long> {
}
