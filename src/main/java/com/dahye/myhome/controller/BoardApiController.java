package com.dahye.myhome.controller;
import java.util.List;
import com.dahye.myhome.model.Board;
import com.dahye.myhome.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping("/api")
class BoardApiController { // ctrl+R 모두 바꾸기
            //final
        @Autowired
        private BoardRepository repository;
/*

        BoardApiController(BoardRepository repository) {
            this.repository = repository;
        }
*/


        // Aggregate root
        // tag::get-aggregate-root[]
        @GetMapping("/boards")
        List<Board> all(@RequestParam(required = false,defaultValue = "") String title,
                        @RequestParam(required = false,defaultValue = "") String content) {
            if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
                return repository.findAll();
            }else{
                //title을 이용해 검색
                return  repository.findByTitleOrContent(title,content);
            }

        }
        // end::get-aggregate-root[]

        @PostMapping("/boards")
        Board newBoard(@RequestBody Board newBoard) {
            return repository.save(newBoard);
        }

        // Single item

        @GetMapping("/boards/{id}")
        Board one(@PathVariable Long id) {

            return repository.findById(id).orElse(null);
                    //.orElseThrow(() -> new BoardNotFoundException(id));
        }

        @PutMapping("/boards/{id}")
        Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

            return repository.findById(id)
                    .map(board -> {
                        board.setTitle(newBoard.getTitle());
                        board.setContent(newBoard.getContent());
                        return repository.save(board);
                    })
                    .orElseGet(() -> {
                        newBoard.setId(id);
                        return repository.save(newBoard);
                    });
        }
        @Secured("ROLE_ADMIN")
        @DeleteMapping("/boards/{id}")
        void deleteBoard(@PathVariable Long id) {
            repository.deleteById(id);
        }
}
