package pl.com.web.rest;

import pl.com.domain.Forwarder;
import pl.com.repository.ForwarderRepository;
import pl.com.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.com.domain.Forwarder}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ForwarderResource {

    private final Logger log = LoggerFactory.getLogger(ForwarderResource.class);

    private static final String ENTITY_NAME = "forwarder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ForwarderRepository forwarderRepository;

    public ForwarderResource(ForwarderRepository forwarderRepository) {
        this.forwarderRepository = forwarderRepository;
    }

    /**
     * {@code POST  /forwarders} : Create a new forwarder.
     *
     * @param forwarder the forwarder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new forwarder, or with status {@code 400 (Bad Request)} if the forwarder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/forwarders")
    public ResponseEntity<Forwarder> createForwarder(@RequestBody Forwarder forwarder) throws URISyntaxException {
        log.debug("REST request to save Forwarder : {}", forwarder);
        if (forwarder.getId() != null) {
            throw new BadRequestAlertException("A new forwarder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Forwarder result = forwarderRepository.save(forwarder);
        return ResponseEntity.created(new URI("/api/forwarders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /forwarders} : Updates an existing forwarder.
     *
     * @param forwarder the forwarder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated forwarder,
     * or with status {@code 400 (Bad Request)} if the forwarder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the forwarder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forwarders")
    public ResponseEntity<Forwarder> updateForwarder(@RequestBody Forwarder forwarder) throws URISyntaxException {
        log.debug("REST request to update Forwarder : {}", forwarder);
        if (forwarder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Forwarder result = forwarderRepository.save(forwarder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, forwarder.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /forwarders} : get all the forwarders.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of forwarders in body.
     */
    @GetMapping("/forwarders")
    public List<Forwarder> getAllForwarders() {
        log.debug("REST request to get all Forwarders");
        return forwarderRepository.findAll();
    }

    /**
     * {@code GET  /forwarders/:id} : get the "id" forwarder.
     *
     * @param id the id of the forwarder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the forwarder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/forwarders/{id}")
    public ResponseEntity<Forwarder> getForwarder(@PathVariable Long id) {
        log.debug("REST request to get Forwarder : {}", id);
        Optional<Forwarder> forwarder = forwarderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(forwarder);
    }

    /**
     * {@code DELETE  /forwarders/:id} : delete the "id" forwarder.
     *
     * @param id the id of the forwarder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/forwarders/{id}")
    public ResponseEntity<Void> deleteForwarder(@PathVariable Long id) {
        log.debug("REST request to delete Forwarder : {}", id);
        forwarderRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
