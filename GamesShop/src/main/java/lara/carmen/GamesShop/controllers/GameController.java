package lara.carmen.GamesShop.controllers;

import lara.carmen.GamesShop.models.Juego;
import lara.carmen.GamesShop.service.JuegoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GameController {

    private final JuegoService juegoService;

    public GameController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    // Página independiente (sin layout) para enseñar las notaciones de fragmentos
    @GetMapping("/fragments-demo")
    public String fragmentsDemo() {
        return "fragments-demo";
    }

    // Catálogo usando el layout maestro
    @GetMapping({"/", "/juegos"})
    public String listarJuegos(Model model) {
        model.addAttribute("juegos", juegoService.listarTodos());
        return "juegos";
    }

    // Formulario de subida
    @GetMapping("/juegos/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("juego", new Juego());
        return "formulario";
    }

    // Guardado del juego y su portada en static/uploads
    @PostMapping("/juegos")
    public String guardarJuego(@ModelAttribute Juego juego,
                               @RequestParam("portada") MultipartFile portada) {
        juegoService.guardarJuego(juego, portada);
        // Patrón PRG (Post/Redirect/Get)
        return "redirect:/juegos";
    }
}
