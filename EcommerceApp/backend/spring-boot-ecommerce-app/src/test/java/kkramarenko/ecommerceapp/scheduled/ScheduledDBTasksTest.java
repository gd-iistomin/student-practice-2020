package kkramarenko.ecommerceapp.scheduled;

import kkramarenko.ecommerceapp.entity.Order;
import kkramarenko.ecommerceapp.enums.OrderStatus;
import kkramarenko.ecommerceapp.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Scheduled task test")
@ExtendWith(MockitoExtension.class)
class ScheduledDBTasksTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    ScheduledDBTasks scheduledDBTasks;

    @Captor
    ArgumentCaptor<Order> orderArgumentCaptor;

    @Test
    @DisplayName("Test order status processing")
    void checkOrderStatus() {
        Order order1 = new Order();
        order1.setStatus(OrderStatus.CREATED.toString());
        order1.setLastUpdated(new Date(System.currentTimeMillis()));

        Order order2 = new Order();
        order2.setStatus(OrderStatus.CREATED.toString());
        order2.setLastUpdated(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)));

        Order order3 = new Order();
        order3.setStatus(OrderStatus.SHIPPING.toString());
        order3.setLastUpdated(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)));

        Order order4 = new Order();
        order4.setStatus(OrderStatus.PROCESSING.toString());
        order4.setLastUpdated(Date.from(Instant.now().minus(2, ChronoUnit.DAYS)));

        Order order5 = new Order();
        order5.setStatus(OrderStatus.SHIPPING.toString());
        order5.setLastUpdated(Date.from(Instant.now().minus(17, ChronoUnit.HOURS)));

        List<Order> orders = List.of(order1, order2, order3, order4, order5);

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderRepository.save(orderArgumentCaptor.capture())).thenReturn(null);

        scheduledDBTasks.checkOrderStatus();

        assertAll(
                () -> assertEquals(OrderStatus.CREATED.toString(),
                        orderArgumentCaptor.getAllValues().get(0).getStatus()),
                () -> assertEquals(OrderStatus.PROCESSING.toString(),
                        orderArgumentCaptor.getAllValues().get(1).getStatus()),
                () -> assertEquals(OrderStatus.COMPLETED.toString(),
                        orderArgumentCaptor.getAllValues().get(2).getStatus()),
                () -> assertEquals(OrderStatus.SHIPPING.toString(),
                        orderArgumentCaptor.getAllValues().get(3).getStatus()),
                () -> assertEquals(OrderStatus.SHIPPING.toString(),
                        orderArgumentCaptor.getAllValues().get(4).getStatus())
        );


    }
}