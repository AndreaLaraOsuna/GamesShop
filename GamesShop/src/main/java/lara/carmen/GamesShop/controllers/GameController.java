package lara.carmen.GamesShop.controllers;

import lara.carmen.GamesShop.models.Juego;
import lara.carmen.GamesShop.service.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GameController {

    @Autowired
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
    public String listarJuegos(@RequestParam(name ="buscar", required = false)String buscar, Model model) {
        if (buscar != null && !buscar.isEmpty()) {
            model.addAttribute("buscar", buscar);
        }
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
    public String guardarJuego(@RequestParam("titulo")String titulo,
                               @RequestParam("descripcion")String descripcion,
                               @RequestParam("precio")double precio,
                               @RequestParam("categoria")String categoria,
                               @RequestParam("portada")MultipartFile portada) {

        Juego nuevoJuego = new Juego();
        nuevoJuego.setTitulo(titulo);
        nuevoJuego.setDescripcion(descripcion);
        nuevoJuego.setPrecio(precio);
        nuevoJuego.setCategoria(categoria);

        juegoService.guardarJuego(nuevoJuego, portada);
        // Patrón PRG (Post/Redirect/Get)
        return "redirect:/juegos";
    }

    @GetMapping("/juegos/editar/{id}")
    public String editarJuego(@PathVariable long id, Model model) {

        Juego juego=juegoService.obtenerJuegoPorId(id);

        if(juego!=null){
            model.addAttribute("juego", juego);
            return "formulario";
        }

        return "juegos";
    }


    @PostMapping("/juegos/editar/{id}")
    public String actualizarJuego(@PathVariable Long id,
                                  @RequestParam("titulo")String titulo,
                                  @RequestParam("descripcion")String descripcion,
                                  @RequestParam("precio")Double precio,
                                  @RequestParam("categoria") String categoria,
                                  @RequestParam(value="portada",required = false)MultipartFile portada) {

        Juego juegoExistente =  juegoService.obtenerJuegoPorId(id);
        if(juegoExistente!=null){
            juegoExistente.setTitulo(titulo);
            juegoExistente.setDescripcion(descripcion);
            juegoExistente.setPrecio(precio);
            juegoExistente.setCategoria(categoria);
            juegoService.guardarJuego(juegoExistente, portada);
        }
        return  "redirect:/juegos";
    }

    @PostMapping("/juegos/eliminar/{id}")
    public String eliminarJuego(@PathVariable Long id){
        Juego juego=juegoService.obtenerJuegoPorId(id);
        if(juego!=null){
            juegoService.eliminarJuego(id);
            return "redirect:/juegos";
        }
    }
}
