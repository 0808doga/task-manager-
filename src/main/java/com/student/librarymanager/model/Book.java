package com.student.librarymanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String isbn;
    private Integer publicationYear;
    private Long authorId;
    // Wrapper type so a PATCH request can leave it out (null = unchanged)
    private Boolean available;
}
