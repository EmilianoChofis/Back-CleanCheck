package utez.edu.mx.cleancheck.controller.image;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.cleancheck.service.image.ImageService;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api-clean/image")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor

public class ImageController {

    private final ImageService service;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<String>>> getImage (@RequestBody List<String> keys) {
        try {
            List<String> url = service.getPresidedUrl(keys);
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            url, false, 200, "Imagenes obtenidas correctamente"
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, 500, e.getMessage()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


}
