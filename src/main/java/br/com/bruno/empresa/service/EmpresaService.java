package br.com.bruno.empresa.service;

import br.com.bruno.empresa.entity.Empresa;
import br.com.bruno.empresa.exception.EmpresaException;
import br.com.bruno.empresa.repository.EmpresaRepository;
import br.com.bruno.empresa.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public Empresa findById(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new EmpresaException(String.format("%s%d", Message.SEM_EMPRESA_COM_ID_, id)));
    }

    public List<Empresa> findAll(){
        return new ArrayList<>(empresaRepository.findAll());
    }

    public List<Empresa> findAllSortedByNome() {
        return empresaRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public Empresa save(Empresa empresa){
        return empresaRepository.save(empresa);
    }

    public void deleteById(long id){
        empresaRepository.deleteById(id);
    }

}
