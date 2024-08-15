package com.bubbleshop.product.interfaces.event;

import com.bubbleshop.product.application.internal.commandservice.ProductEventCommandService;
import com.bubbleshop.product.domain.model.event.CreatedProductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
@Slf4j
public class CreatedProductEventListener {
    private final ProductEventCommandService productEventCommandService;

    @Async
    @TransactionalEventListener
    public void createdProductEventHandle(CreatedProductEvent event) {
        log.debug("CreatedProductEvent Listener");
        productEventCommandService.deleteProductTempImages(event.getProduct());
    }
}
