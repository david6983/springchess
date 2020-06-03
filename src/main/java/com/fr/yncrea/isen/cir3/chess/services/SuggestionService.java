package com.fr.yncrea.isen.cir3.chess.services;

import com.fr.yncrea.isen.cir3.chess.repository.SuggestionCoordRepository;
import com.fr.yncrea.isen.cir3.chess.repository.SuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuggestionService {
    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private SuggestionCoordRepository suggestionCoordRepository;
}
