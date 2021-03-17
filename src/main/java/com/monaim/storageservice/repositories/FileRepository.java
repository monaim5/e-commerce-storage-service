package com.monaim.storageservice.repositories;

import com.monaim.storageservice.models.BaseFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<BaseFile, Long> {
    Optional<BaseFile> findByFilename(String filename);
}
