package com.webone.quiosq.controller;

import com.webone.quiosq.controller.request.ValidateToken;
import com.webone.quiosq.controller.response.IdentifcacaoResponse;
import com.webone.quiosq.dto.LoginClienteDto;
import com.webone.quiosq.dto.LoginUserDto;
import com.webone.quiosq.dto.RecoveryJwtTokenDto;
import com.webone.quiosq.service.AuthService;
import com.webone.quiosq.service.impl.JasperReportImpl;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JasperReportImpl jasperReport;

    @PostMapping
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(
        @RequestBody LoginUserDto loginUserDto) {
        return new ResponseEntity<>(authService.authenticateUser(loginUserDto), HttpStatus.OK);
    }

    @PostMapping("/cliente")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateCliente(
        @RequestBody LoginClienteDto request) {
        return new ResponseEntity<>(authService.authenticateClient(request), HttpStatus.OK);
    }

    @PostMapping("/sms/{id}")
    public ResponseEntity<Void> sendSmsToken(@PathVariable("id") String id) {
        authService.generateCodigoUsuario(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/sms/validate")
    public ResponseEntity<Map<String, Boolean>> sendSmsValidate(
        @RequestBody ValidateToken request) {
        return new ResponseEntity<>(Map.of("ativo", authService.validarToken(request)),
            HttpStatus.OK);
    }


    @GetMapping("/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response)
        throws FileNotFoundException {

        // String token = JWTUtil.generateToken(usuaioAutenticado.getEmail());
        // response.setHeader("Authorization", "Bearer " + usuaioAutenticado.getEmail());

        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<IdentifcacaoResponse> buildAppIdentificacao(
        @RequestParam(name = "token") String token) {
        return new ResponseEntity<>(authService.buildAppIdentificacao(token),
            HttpStatus.OK);
    }

}
