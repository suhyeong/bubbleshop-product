package co.kr.suhyeong.project.product.interfaces.event;

import co.kr.suhyeong.project.product.application.internal.commandservice.ProductImageEventCommandService;
import co.kr.suhyeong.project.product.domain.model.event.ModifiedProductImageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
@Slf4j
public class ModifiedProductImageEventListener {
    private final ProductImageEventCommandService productImageEventCommandService;

    /**
     * 상품 이미지 수정 내부 Event Listener
     * @param event 상품 이미지 수정 event
     */
    @Async
    @TransactionalEventListener
    public void modifiedProductImageEventHandle(ModifiedProductImageEvent event) {
        log.debug("ModifiedProductImageEvent Listener, event : {}", event);
        productImageEventCommandService.cleanUpProductImageInfo(event.getProductCode(), event.getImages());
    }
}
