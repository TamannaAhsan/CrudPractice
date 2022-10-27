package com.example.crudpractice.repository;

import com.example.crudpractice.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachRepository extends JpaRepository<Attachment, Integer> {
}
