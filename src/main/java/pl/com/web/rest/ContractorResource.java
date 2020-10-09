package pl.com.web.rest;

import pl.com.domain.Contractor;
import pl.com.repository.ContractorRepository;
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
 * REST controller for managing {@link pl.com.domain.Contractor}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContractorResource {

    private final Logger log = LoggerFactory.getLogger(ContractorResource.class);

    private static final String ENTITY_NAME = "contractor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractorRepository contractorRepository;

    public ContractorResource(ContractorRepository contractorRepository) {
        this.contractorRepository = contractorRepository;
    }

    /**
     * {@code POST  /contractors} : Create a new contractor.
     *
     * @param contractor the contractor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contractor, or with status {@code 400 (Bad Request)} if the contractor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contractors")
    public ResponseEntity<Contractor> createContractor(@RequestBody Contractor contractor) throws URISyntaxException {
        log.debug("REST request to save Contractor : {}", contractor);
        if (contractor.getId() != null) {
            throw new BadRequestAlertException("A new contractor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contractor result = contractorRepository.save(contractor);
        return ResponseEntity.created(new URI("/api/contractors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contractors} : Updates an existing contractor.
     *
     * @param contractor the contractor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractor,
     * or with status {@code 400 (Bad Request)} if the contractor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contractor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contractors")
    public ResponseEntity<Contractor> updateContractor(@RequestBody Contractor contractor) throws URISyntaxException {
        log.debug("REST request to update Contractor : {}", contractor);
        if (contractor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contractor result = contractorRepository.save(contractor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contractor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contractors} : get all the contractors.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contractors in body.
     */
    @GetMapping("/contractors")
    public List<Contractor> getAllContractors() {
        log.debug("REST request to get all Contractors");
        return contractorRepository.findAll();
    }

    /**
     * {@code GET  /contractors/:id} : get the "id" contractor.
     *
     * @param id the id of the contractor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contractor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contractors/{id}")
    public ResponseEntity<Contractor> getContractor(@PathVariable Long id) {
        log.debug("REST request to get Contractor : {}", id);
        Optional<Contractor> contractor = contractorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contractor);
    }

    /**
     * {@code DELETE  /contractors/:id} : delete the "id" contractor.
     *
     * @param id the id of the contractor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contractors/{id}")
    public ResponseEntity<Void> deleteContractor(@PathVariable Long id) {
        log.debug("REST request to delete Contractor : {}", id);
        contractorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
