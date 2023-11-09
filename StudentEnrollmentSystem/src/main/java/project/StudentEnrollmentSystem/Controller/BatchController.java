package project.StudentEnrollmentSystem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import project.StudentEnrollmentSystem.Entities.Batch;
import project.StudentEnrollmentSystem.Entities.BatchRepo;
import project.StudentEnrollmentSystem.Entities.Course;

@RestController
public class BatchController {
	@Autowired
	private BatchRepo batchRepo;
	// <------------Add a new batch to the system------------>

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/AddBatch")
	@Operation(summary = "Add a New Batch", description = "add a new batch to the system.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Batch created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid user input"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Batch addNewBatch(@Valid @RequestBody Batch batch) {
		batchRepo.save(batch);
		return batch;

	}
	// <------------Update an existing batch in the system------------>

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/UpdateBatch/{batchId}")
	@Operation(summary = "Update a Batch", description = "Update an existing batch in the system.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Batch updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid user input"),
			@ApiResponse(responseCode = "404", description = "Batch not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Batch updateBatch(@PathVariable("batchId") Integer batchId, @Valid @RequestBody Batch batch) {
		var optBatch = batchRepo.findById(batchId);
		try {
			if (!optBatch.isPresent()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BatchId Not Found..!");
			}
			List<Batch> existingBatches = batchRepo.findAll();

			if (existingBatches.contains(batch)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A batch with the same details already exists.");
			}
			batchRepo.save(batch);
			return batch;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}
	// <------------Delete a batch from the system.------------>

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/DeleteBatch/{batchId}")
	@Operation(summary = "Delete a Batch", description = "Delete a batch from the system.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Batch deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Batch not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Batch deleteBatch(@PathVariable("batchId") int batchId) {
		var optbatch = batchRepo.findById(batchId);
		if (optbatch.isPresent()) {
			batchRepo.deleteById(batchId);
			return optbatch.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Given Batch Id doesn't exist..!");
		}
	}

}
