package com.fr.yncrea.isen.cir3.chess.controller;

import com.fr.yncrea.isen.cir3.chess.domain.Figure;
import com.fr.yncrea.isen.cir3.chess.domain.Suggestion;
import com.fr.yncrea.isen.cir3.chess.repository.FigureRepository;
import com.fr.yncrea.isen.cir3.chess.repository.SuggestionRepository;
import com.fr.yncrea.isen.cir3.chess.services.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/suggestion")
public class SuggestionController {
    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private FigureRepository figureRepository;

    @Autowired
    private SuggestionService service;

    @GetMapping("/{figureId}")
    public Suggestion getSuggestion(
            @PathVariable final Long figureId
    ) {
        // return the suggestion of a specific Figure
        Optional<Figure> fig = figureRepository.findById(figureId);
        return fig.map(figure -> suggestionRepository.findByFigure(figure)).orElse(null);
    }
}
