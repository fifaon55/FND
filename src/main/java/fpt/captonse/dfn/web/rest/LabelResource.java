package fpt.captonse.dfn.web.rest;

import fpt.captonse.dfn.service.LabelService;
import fpt.captonse.dfn.web.rest.errors.BadRequestAlertException;
import fpt.captonse.dfn.service.dto.LabelDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fpt.captonse.dfn.domain.Label}.
 */
@RestController
@RequestMapping("/api")
public class LabelResource {

    private final Logger log = LoggerFactory.getLogger(LabelResource.class);

    private static final String ENTITY_NAME = "label";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LabelService labelService;

    public LabelResource(LabelService labelService) {
        this.labelService = labelService;
    }

    /**
     * {@code POST  /labels} : Create a new label.
     *
     * @param labelDTO the labelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new labelDTO, or with status {@code 400 (Bad Request)} if the label has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/labels")
    public ResponseEntity<LabelDTO> createLabel(@RequestBody LabelDTO labelDTO) throws URISyntaxException {
        log.debug("REST request to save Label : {}", labelDTO);
        if (labelDTO.getId() != null) {
            throw new BadRequestAlertException("A new label cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LabelDTO result = labelService.save(labelDTO);
        return ResponseEntity.created(new URI("/api/labels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /labels} : Updates an existing label.
     *
     * @param labelDTO the labelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated labelDTO,
     * or with status {@code 400 (Bad Request)} if the labelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the labelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/labels")
    public ResponseEntity<LabelDTO> updateLabel(@RequestBody LabelDTO labelDTO) throws URISyntaxException {
        log.debug("REST request to update Label : {}", labelDTO);
        if (labelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LabelDTO result = labelService.save(labelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, labelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /labels} : get all the labels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of labels in body.
     */
    @GetMapping("/labels")
    public List<LabelDTO> getAllLabels() {
        log.debug("REST request to get all Labels");
        return labelService.findAll();
    }

    /**
     * {@code GET  /labels/:id} : get the "id" label.
     *
     * @param id the id of the labelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the labelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/labels/{id}")
    public ResponseEntity<LabelDTO> getLabel(@PathVariable Long id) {
        log.debug("REST request to get Label : {}", id);
        Optional<LabelDTO> labelDTO = labelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(labelDTO);
    }

    /**
     * {@code DELETE  /labels/:id} : delete the "id" label.
     *
     * @param id the id of the labelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/labels/{id}")
    public ResponseEntity<Void> deleteLabel(@PathVariable Long id) {
        log.debug("REST request to delete Label : {}", id);
        labelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
