package ru.keller.bidaskanalyzer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.keller.bidaskanalyzer.dto.ElvlDto;
import ru.keller.bidaskanalyzer.service.ElvlService;

import java.util.List;

@RestController
@RequestMapping("/elvls")
public class ElvlController {

    private final ElvlService elvlService;

    public ElvlController(ElvlService elvlService) {
        this.elvlService = elvlService;
    }

    /**
     * Получение elvl по ISIN.
     */
    @GetMapping("/{isin}")
    public ResponseEntity<ElvlDto> getElvlByIsin(@PathVariable String isin) {
        return ResponseEntity.ok(elvlService.getElvlByIsin(isin));
    }

    /**
     * Получение всех elvl.
     */
    @GetMapping
    public ResponseEntity<List<ElvlDto>> getAllElvls() {
        return ResponseEntity.ok(elvlService.getAllElvls());
    }

    /**
     * Удаление elvl по ISIN.
     */
    @DeleteMapping("/{isin}")
    public ResponseEntity<Void> deleteElvlByIsin(@PathVariable String isin) {
        elvlService.deleteElvlByIsin(isin);
        return ResponseEntity.noContent().build();
    }
}
