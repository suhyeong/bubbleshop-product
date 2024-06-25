package co.kr.suhyeong.project.product.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductListCommand {
    private Pageable pageable;

    public GetProductListCommand(Integer page, Integer size) {
        this.pageable = PageRequest.of(page-1, size);
    }
}
