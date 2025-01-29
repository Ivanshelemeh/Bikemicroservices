package com.example.bikecustomservise.api.service;

import com.example.bikecustomservise.api.dto.CustomerAvroDto;
import com.example.bikecustomservise.api.model.customer.BikeCustomerModel;
import com.example.bikecustomservise.api.model.order.OrderModel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class FilledCustomerAvroService {

    private final BikeCustomerServiceImpl bikeCustomerService;
    private final BikeOrderServiceImpl orderService;

    @SneakyThrows
    public void serializeCustomerAvro( Integer customerId, Integer orderId) {
        final CustomerAvroDto avroDto = generateCustomerAvro(customerId, orderId);
        DatumWriter<CustomerAvroDto> dtoDatumWriter = new SpecificDatumWriter<>(CustomerAvroDto.class);
        DataFileWriter<CustomerAvroDto> dataFileWriter = new DataFileWriter<>(dtoDatumWriter);
        final byte [] avroBytes = SerializationUtils.serialize((Serializable) avroDto);
        dataFileWriter.appendEncoded(ByteBuffer.wrap(avroBytes));

    }

    private CustomerAvroDto generateCustomerAvro(Integer customerId, Integer orderId) {
        final BikeCustomerModel customer = getBikeCustomer(customerId);
        final OrderModel bikeOrder = getBikeOrder(orderId);
        return CustomerAvroDto.builder()
                .customerEmail(customer.customerEmail())
                .customerName(customer.nameCustomer())
                .dateTime(getNow())
                .orderName(bikeOrder.orderName())
                .orderPrice(bikeOrder.orderPrice())
                .build();
    }

    final File avroFile = new File("Customer.avsc");

    private BikeCustomerModel getBikeCustomer(@NonNull final Integer id) {
        return bikeCustomerService.findCustomer(id);

    }

    private OrderModel getBikeOrder(@NonNull final Integer id) {
        return orderService.findByOrderId(id);
    }

    protected OffsetDateTime getNow() {
        return OffsetDateTime.now();
    }

}
