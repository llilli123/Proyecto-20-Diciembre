package com.mapa.evento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapa.evento.model.MapNode;
import com.mapa.evento.repository.MapNodeRepository;

@RestController
@RequestMapping("/map/nodes")
public class MapController {

    @Autowired
    private MapNodeRepository mapNodeRepository;

    // 1. Obtener todos los nodos
    @GetMapping
    public List<MapNode> getAllNodes() {
        // Si aquí da error, es porque MapNodeRepository no extiende de JpaRepository
        return mapNodeRepository.findAll();
    }

    // 2. Obtener ubicación por referencia
    @GetMapping("/location")
    public ResponseEntity<MapNode> getNodeLocation(
            @RequestParam String linkedId, 
            @RequestParam String type) {
        // Este método 'findByLinkedIdAndType' debe estar escrito en tu interfaz Repository
        return mapNodeRepository.findByLinkedIdAndType(linkedId, type)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Crear nodo
    @PostMapping
    public MapNode createNode(@RequestBody MapNode node) {
        return mapNodeRepository.save(node);
    }

    // 4. Actualizar nodo
    @PutMapping("/{id}")
    public ResponseEntity<MapNode> updateNode(@PathVariable Long id, @RequestBody MapNode details) {
        return mapNodeRepository.findById(id)
                .map(node -> {
                    // Si aquí da error, es porque MapNode.java no tiene los Getters/Setters guardados
                    node.setLabel(details.getLabel());
                    node.setPosX(details.getPosX());
                    node.setPosY(details.getPosY());
                    node.setType(details.getType());
                    node.setLinkedId(details.getLinkedId());
                    return ResponseEntity.ok(mapNodeRepository.save(node));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Eliminar nodo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNode(@PathVariable Long id) {
        mapNodeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    

}