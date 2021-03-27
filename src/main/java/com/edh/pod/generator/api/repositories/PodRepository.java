package com.edh.pod.generator.api.repositories;

import com.edh.pod.generator.api.models.Pod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodRepository extends JpaRepository<Pod, Integer>{
}
