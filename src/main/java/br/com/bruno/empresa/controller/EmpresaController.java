package br.com.bruno.empresa.controller;

import br.com.bruno.empresa.dto.EmpresaDTO;
import br.com.bruno.empresa.dto.ResponseDTO;
import br.com.bruno.empresa.entity.Empresa;
import br.com.bruno.empresa.service.EmpresaService;
import br.com.bruno.empresa.util.Message;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/empresa")
public class EmpresaController {

    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private EmpresaService empresaService;

    @GetMapping(path = "/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            Empresa empresa = empresaService.findById(id);
            if (empresa.getId() != null) {
                EmpresaDTO empresaDTO = mapper.map(empresa, EmpresaDTO.class);
                return new ResponseEntity<>(empresaDTO, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseDTO(String.format("%s", Message.EMPRESA_NAO_ENCONTRADA)), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>(empresaService.findAllSortedByNome(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity save(@Valid @RequestBody EmpresaDTO empresaDTO){
        try {
            Empresa empresa = mapper.map(empresaDTO, Empresa.class);
            Empresa empresaSalva = empresaService.save(empresa);
            return new ResponseEntity<>(new ResponseDTO(String.format("%s", Message.EMPRESA_CRIADA_COM_SUCESSO), empresaSalva), HttpStatus.CREATED);

        }catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity put(@PathVariable Long id, @RequestBody EmpresaDTO empresaDTO) {
        try {
            Empresa empresaEncontrada = empresaService.findById(id);
            if (empresaEncontrada.getId() != null) {
                mapper.typeMap(EmpresaDTO.class, Empresa.class).addMappings(mapper -> mapper.skip(Empresa::setId));
                mapper.map(empresaDTO, empresaEncontrada);
                Empresa empresaSalva = empresaService.save(empresaEncontrada);
                return new ResponseEntity<>(new ResponseDTO(String.format("%s", Message.EMPRESA_ATUALIZADA_COM_SUCESSO), empresaSalva), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseDTO(String.format("%s", Message.EMPRESA_NAO_ENCONTRADA)), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            Empresa empresa = empresaService.findById(id);
            if (empresa.getId() != null) {
                empresaService.deleteById(id);
                return new ResponseEntity<>(new ResponseDTO(String.format("%s", Message.EMPRESA_DELETADA_COM_SUCESSO)), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseDTO(String.format("%s", Message.EMPRESA_NAO_ENCONTRADA)), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(String.format("%s %s %s", Message.ERRO_AO_DELETAR_EMPRESA, " - ", e.getMessage())), HttpStatus.BAD_REQUEST);
        }
    }

}
