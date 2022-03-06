package com.dahye.myhome.service;

import com.dahye.myhome.model.Board;
import com.dahye.myhome.model.User;
import com.dahye.myhome.repository.BoardRepository;
import com.dahye.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board){
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }

}
