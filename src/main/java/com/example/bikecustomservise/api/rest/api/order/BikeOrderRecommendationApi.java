package com.example.bikecustomservise.api.rest.api.order;

import com.example.bikecustomservise.api.dto.order.OrderRecommendationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotBlank;

@Tag(name = "BikeOrderRecommendationApi", description = "Rest Api for order recommendation information.")
@RequestMapping("/rest/api/v1/order-recommendation")
public interface BikeOrderRecommendationApi {

    @Operation(summary = "Get recommendation by rate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request not found recommendation by incoming rate"),
            @ApiResponse(responseCode = "500", description = "Server error while requesting")
    })
    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<OrderRecommendationDTO> getOrderRateInfo(@PathVariable @NotBlank String orderId);
}
