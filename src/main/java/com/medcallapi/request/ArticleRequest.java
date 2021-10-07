package com.medcallapi.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ArticleRequest {
    private String title;
    private String content;
    private String speciality;
    private String username;
}
