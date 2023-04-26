package co.kr.suhyeong.project.product.domain.command;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
public class GetProductListCommand {
    private Pageable pageable;
}
