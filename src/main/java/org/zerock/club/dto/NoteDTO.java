package org.zerock.club.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {
    private Long num;
    private String title;
    private String content;
    private String writerEmail; //연관 관계 없이
    private LocalDateTime regDate, modDate;
}
