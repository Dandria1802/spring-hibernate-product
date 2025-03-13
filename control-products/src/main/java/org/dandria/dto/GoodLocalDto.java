package org.dandria.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GoodLocalDto {
    Long id;
    Long goodId;
    Long placeId;
    LocalDateTime dateTime;
}
