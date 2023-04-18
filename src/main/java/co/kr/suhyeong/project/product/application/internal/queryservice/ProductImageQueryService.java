package co.kr.suhyeong.project.product.application.internal.queryservice;

import co.kr.suhyeong.project.constants.ApiException;
import co.kr.suhyeong.project.product.domain.command.GetProductImageCommand;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import co.kr.suhyeong.project.product.domain.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static co.kr.suhyeong.project.constants.ResponseCode.NON_EXIST_DATA;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductImageQueryService {
    private final ProductImageRepository productImageRepository;

    @Transactional(readOnly = true)
    public List<ProductImage> getProductImages(GetProductImageCommand command) {
        List<ProductImage> productImageList = productImageRepository.findByProductImageId_ProductCodeAndAndDivCodeIn(
                command.getProductCode(), command.getProductImageCodeList()
        );

        if(productImageList.isEmpty())
            throw new ApiException(NON_EXIST_DATA);

        return productImageList;
    }
}
