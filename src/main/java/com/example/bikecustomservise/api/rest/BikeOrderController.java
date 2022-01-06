package com.example.bikecustomservise.api.rest;

import com.example.bikecustomservise.api.dto.BikeOrderDTO;
import com.example.bikecustomservise.api.entities.BikeOrder;
import com.example.bikecustomservise.api.service.BikeOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class BikeOrderController {

    private final BikeOrderServiceImpl bikeOrderService;

    @Autowired
    public BikeOrderController(BikeOrderServiceImpl bikeOrderService) {
        this.bikeOrderService = bikeOrderService;
    }

    /**
     * Find all orders in db
     *
     * @return status 200
     */
    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BikeOrderDTO> getAllOrder() {
        bikeOrderService.findAllOrders();
        return ResponseEntity.ok().build();
    }

    /**
     * Delete order with specify name
     *
     * @param name
     * @return 204 status after removed raw in db
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<BikeOrder> removeOrder(@PathVariable String name) {
        bikeOrderService.deleteByOrderName(name);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add")
    public ResponseEntity<BikeOrder> addOrder(@RequestBody BikeOrder order) {
        bikeOrderService.saveOrder(order);
        return ResponseEntity.accepted().build();
    }
}
