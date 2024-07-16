package co.kr.suhyeong.project.product.application.internal.commandservice;

import co.kr.suhyeong.project.exception.ApiException;
import co.kr.suhyeong.project.product.domain.command.CreateProductCommand;
import co.kr.suhyeong.project.product.domain.command.ModifyProductCommand;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.repository.CategoryRepository;
import co.kr.suhyeong.project.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static co.kr.suhyeong.project.constants.ResponseCode.NON_EXIST_DATA;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCommandService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private void checkCategory(String mainCategoryCode, String subCategoryCode) {
        if(!categoryRepository.existsById(mainCategoryCode) || !categoryRepository.existsById(subCategoryCode))
            throw new ApiException(NON_EXIST_DATA);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createProduct(CreateProductCommand command) {
        this.checkCategory(command.getMainCategoryCode(), command.getSubCategoryCode());
        int count = productRepository.countByMainCategoryCodeAndSubCategoryCode(command.getMainCategoryCode(), command.getSubCategoryCode());
        Product product = new Product(command, count+1);
        productRepository.save(product);
    }

    /**
     * 상품 정보 수정
     *
     * 1. 수정 요청 상품 코드로 상품 정보를 조회하고 데이터가 없으면 에러를 리턴한다.
     * 2. 메인 카테고리 혹은 서브 카테고리 정보가 수정되었다면 새 상품 엔티티를 생성하여 저장한다.
     * 3. 메인 카테고리와 서브 카테고리 정보가 수정되지 않았다면 기존 상품 엔티티에서 데이터를 변경하여 저장한다.
     * @param command
     */
    @Transactional(rollbackFor = Exception.class)
    public void modifyProduct(ModifyProductCommand command) {
        Product originProduct = productRepository.findById(command.getProductCode())
                .orElseThrow(() -> new ApiException(NON_EXIST_DATA));
        if(originProduct.isSameCategory(command.getMainCategoryCode(), command.getSubCategoryCode())) {
            originProduct.modifyProduct(command);
            productRepository.save(originProduct);
        } else {
            this.createProduct(command);
            productRepository.delete(originProduct);
        }
    }
}
