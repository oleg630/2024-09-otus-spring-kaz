package ru.otus.hw08.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.hw08.model.Comment;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void removeCommentArrayElementsById(String id) {
        val query = Query.query(Criteria.where("book.id").is(id));
        mongoTemplate.remove(query, Comment.class);
    }

}
