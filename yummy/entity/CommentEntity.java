package com.cookie.yummy.entity;

import com.cookie.yummy.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;
    @Column
    private String commentContents;

    /*
    Board:Comment = 1 : N
    댓글entity니까 manytoone임
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @CreationTimestamp
    private Timestamp createDate;

    public static CommentEntity toSaveEntity(CommentDTO commentDTO, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }
}
