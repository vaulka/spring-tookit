package com.vaulka.kit.validation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vaulka
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page {

    private Integer pageNumber;

    private Integer pageSize;

}
