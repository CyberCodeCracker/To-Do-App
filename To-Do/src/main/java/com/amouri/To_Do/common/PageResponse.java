package com.amouri.To_Do.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> content;
    private int number;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean firstPosition;
    private boolean lastPosition;

}
