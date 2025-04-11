package com.example.demo.tipo_acto_liturgico.service;


import com.example.demo.tipo_acto_liturgico.model.TipoActoLiturgico;
import com.example.demo.tipo_acto_liturgico.repository.TipoActoLiturgicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoActoLiturgicoService {
    
    @Autowired
    private TipoActoLiturgicoRepository tipoActoLiturgicoRepository;

    public List<TipoActoLiturgico> getAllTipos(){
        return tipoActoLiturgicoRepository.findAll();
    }

    public TipoActoLiturgico getTipoById(Long id){
        return tipoActoLiturgicoRepository.findById(id).orElse(null);
    }

    public TipoActoLiturgico saveTipoActoLiturgico(TipoActoLiturgico tipoActoLiturgico){
        return tipoActoLiturgicoRepository.save(tipoActoLiturgico);
    }

    public void deleteTipoActoLiturgico(Long id){
        tipoActoLiturgicoRepository.deleteById(id);
    }

    public TipoActoLiturgico updateTipoActoLiturgico(Long id,TipoActoLiturgico tipoActoLiturgico){
        if(tipoActoLiturgicoRepository.existsById(id)){
            tipoActoLiturgico.setId(id);
            return tipoActoLiturgicoRepository.save(tipoActoLiturgico);
        }
        return null;
    }
}
