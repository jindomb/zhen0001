package com.zhen.crm.workbench.dao;

import com.zhen.crm.workbench.domain.Clue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClueDao {
    int insertClue(Clue clue);

    List<Clue> selectClues(Clue clue);

    int countClues(Clue clue);

    Clue selectClueById(String id);

    Clue getById(String id);

    int deleteById(String id);
}
