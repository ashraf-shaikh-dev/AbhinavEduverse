package com.abhinav.eduverse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhinav.eduverse.model.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long>{

}
