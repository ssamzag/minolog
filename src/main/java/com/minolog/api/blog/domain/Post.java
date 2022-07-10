package com.minolog.api.blog.domain;

import com.minolog.api.blog.dto.request.PostEdit;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private Long userId;

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(this.title)
                .content(this.content);
    }

    public void update(PostEdit postEditor) {
        this.title = postEditor.getTitle();
        this.content = postEditor.getContent();
    }
}
