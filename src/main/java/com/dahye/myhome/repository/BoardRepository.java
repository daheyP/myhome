package com.dahye.myhome.repository;

import com.dahye.myhome.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);
    //http://localhost:8080/api/boards?title=TEST22&content=%EB%82%B4%EC%9A%A9

    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
