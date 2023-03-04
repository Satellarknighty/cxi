package de.cxi.solitaire.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlayController {
    @GetMapping("/start")
    public String startGame(){
        return "startGame";
    }
}
