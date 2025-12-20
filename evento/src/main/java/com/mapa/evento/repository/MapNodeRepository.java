package com.mapa.evento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; // IMPORTANTE
import org.springframework.stereotype.Repository;

import com.mapa.evento.model.MapNode;

@Repository
public interface MapNodeRepository extends JpaRepository<MapNode, Long> {
    // IMPORTANTE: fíjate que dice "extends JpaRepository<MapNode, Long>"
    
    Optional<MapNode> findByLinkedIdAndType(String linkedId, String type);
    
}