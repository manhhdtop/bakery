package com.bakery.server.controller.admin.base;

import com.bakery.server.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BaseController<T> {
    @Autowired
    private BaseService baseService;

    @PutMapping
    public ResponseEntity<?> save(@Valid @RequestBody T t) {
        return new ResponseEntity<>(baseService.save(t), HttpStatus.OK);
    }

    @PutMapping("/all")
    public ResponseEntity<?> saveAll(@Valid @RequestBody List<T> t) {
        return new ResponseEntity<>(baseService.saveAll(t), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> update(@Valid @RequestBody T t) {
        return new ResponseEntity<>(baseService.update(t), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(Long id) {
        baseService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
