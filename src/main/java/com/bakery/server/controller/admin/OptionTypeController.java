package com.bakery.server.controller.admin;

import com.bakery.server.constant.Status;
import com.bakery.server.model.request.OptionCreateDto;
import com.bakery.server.model.request.OptionUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.service.OptionTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@PreAuthorize("hasPermission('OPTION_TYPE', 'VIEW')")
@RequestMapping("${admin-base-path}/option-type")
@RestController
public class OptionTypeController {
    @Autowired
    private OptionTypeService optionTypeService;

    @GetMapping
    public ResponseEntity<?> findAll(String keyword,
                                     @Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (StringUtils.isNotBlank(keyword)) {
            return ResponseEntity.ok(optionTypeService.findByKeyword(keyword, pageable));
        }
        return ResponseEntity.ok(optionTypeService.findAll(pageable));
    }

    @GetMapping("/actives")
    public ResponseEntity<?> findByStatusActive() {
        return ResponseEntity.ok(optionTypeService.findByStatus(Status.ACTIVE.getStatus()));
    }

    @PutMapping
    public ResponseEntity<?> save(@Valid @RequestBody OptionCreateDto optionCreateDto) {
        return ResponseEntity.ok(optionTypeService.save(optionCreateDto));
    }

    @PostMapping
    public ResponseEntity<?> update(@Valid @RequestBody OptionUpdateDto optionUpdateDto) {
        return ResponseEntity.ok(optionTypeService.update(optionUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        optionTypeService.delete(id);
        return ResponseEntity.ok(ApiBaseResponse.success());
    }
}
