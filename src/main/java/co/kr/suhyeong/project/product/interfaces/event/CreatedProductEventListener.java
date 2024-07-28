package co.kr.suhyeong.project.product.interfaces.event;

import co.kr.suhyeong.project.product.application.internal.commandservice.ProductEventCommandService;
import co.kr.suhyeong.project.product.domain.model.valueobject.CreatedProductEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class CreatedProductEventListener {
    private final ProductEventCommandService productEventCommandService;

    @Async
    @TransactionalEventListener
    public void createdProductEventHandle(CreatedProductEvent event) {
        productEventCommandService.deleteProductTempImages(event.getProduct());
    }
}
