package lara.carmen.GamesShop.service;

import lara.carmen.GamesShop.models.Juego;
import lara.carmen.GamesShop.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class JuegoService {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Autowired
    private JuegoRepository juegoRepository;

    public List<Juego> listarTodos() {
        return juegoRepository.findAll();
    }

    public void guardarJuego(Juego juego, MultipartFile portada) {
        String urlImagen = "default.png";

        if (!portada.isEmpty()) {
            //codigo generado en la practica 9
            try {
                // Tip para clase: Crear la carpeta si no existe
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Generamos un nombre único para evitar sobreescribir archivos con el mismo nombre
                urlImagen = UUID.randomUUID().toString() + "_" + portada.getOriginalFilename();
                Path filePath = uploadPath.resolve(urlImagen);

                // Guardamos el archivo físicamente en disco
                Files.copy(portada.getInputStream(), filePath);

                System.out.println("Archivo guardado en: " + filePath.toAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
                // En un proyecto real, manejaríamos esta excepción apropiadamente (ej. redirigir con error)
            }
        }
        //verificar que el titulo no sea vacio, que tenga precio, que el precio no sea negativo
        juego.setPortadaUrl(urlImagen);

        juegoRepository.save(juego);
    }

    public List<Juego> buscarPorTitulo(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) {
            return listarTodos();
        }
        return juegoRepository.findByTituloContainingIgnoreCase(palabra);
    }


    public Juego obtenerJuegoPorId(long id) {
        return juegoRepository.findById(id).orElse(null);
    }

    public void eliminarJuego(Long id){
        juegoRepository.deleteById(id);
    }
}
