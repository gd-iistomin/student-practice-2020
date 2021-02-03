package kkramarenko.ecommerceapp.scheduled;

import kkramarenko.ecommerceapp.entity.Order;
import kkramarenko.ecommerceapp.enums.OrderStatus;
import kkramarenko.ecommerceapp.repository.OrderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class ScheduledDBTasks {

    private final OrderRepository orderRepository;

    public ScheduledDBTasks(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private static final long HALF_AN_HOUR_IN_MILLISECONDS = 1800000L;

    private static final long ONE_DAY_IN_HOURS = 24L;

    /**
     * Checks and processes order status in database
     * Is invoked once in half an hour
     * <p>
     * Every 24h order status should be updated to next status from OrderStatus enum,
     * until order is COMPLETED
     *
     * @see OrderStatus
     * <p>
     * gets all orders, loops through them, checks time delta between current time and
     * last_updated time, if 24 hours have passed, then changes order status to next,
     * if order is not COMPLETED yet.
     */

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    @SuppressWarnings("checkstyle:todocomment")
    public void checkOrderStatus() {

        List<Order> orders = orderRepository.findAll();

        for (Order tempOrder : orders) {
            //since some statuses were stored lowercase, convert to UpperCase
            OrderStatus orderStatus = OrderStatus.valueOf(tempOrder.getStatus().toUpperCase());

            Date currentDate = new Date();

            Date lastUpdatedDate = tempOrder.getLastUpdated();

            Duration delta = Duration.between(lastUpdatedDate.toInstant(), currentDate.toInstant());
            long hoursFromLastUpdate = delta.toHours();

            if (hoursFromLastUpdate >= ONE_DAY_IN_HOURS && !(orderStatus.equals(OrderStatus.COMPLETED))) {
                switch (orderStatus) {
                    case CREATED:
                        orderStatus = OrderStatus.PROCESSING;
                        break;
                    case PROCESSING:
                        orderStatus = OrderStatus.SHIPPING;
                        break;
                    case SHIPPING:
                        orderStatus = OrderStatus.COMPLETED;
                        break;
                    default:
                        // todo think of default behaviour

                }
            }
            tempOrder.setStatus(orderStatus.toString());

            orderRepository.save(tempOrder);
        }


    }
}
