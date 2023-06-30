package com.vaulka.kit.doc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vaulka
 */
@Tag(name = "接口列表")
@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class Controller {

    @Operation(summary = "测试接口")
    @PostMapping("/api")
    public void api() {
    }

}
