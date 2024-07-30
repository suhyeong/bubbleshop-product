package co.kr.suhyeong.project.product.interfaces.event;

import co.kr.suhyeong.project.product.application.internal.commandservice.ProductEventCommandService;
import co.kr.suhyeong.project.product.domain.model.event.CreatedProductEvent;
import co.kr.suhyeong.project.product.domain.model.event.DeletedProductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
@Slf4j
public class DeletedProductEventListener {
    private final ProductEventCommandService productEventCommandService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void deletedProductEventHandle(DeletedProductEvent event) {
        log.debug("DeletedProductEvent Listener");
        productEventCommandService.deleteProductImages(event.getProduct());
    }
}
