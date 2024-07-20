package co.kr.suhyeong.project.product.interfaces.rest.controller;

import co.kr.suhyeong.project.product.application.internal.queryservice.CategoryQueryService;
import co.kr.suhyeong.project.product.domain.command.GetCategoriesCommand;
import co.kr.suhyeong.project.product.domain.model.view.CategoryListView;
import co.kr.suhyeong.project.product.domain.model.view.CategoryView;
import co.kr.suhyeong.project.product.interfaces.rest.dto.GetCategoriesRspDto;
import co.kr.suhyeong.project.product.interfaces.rest.transform.GetCategoriesCommandDTOAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static co.kr.suhyeong.project.product.interfaces.rest.controller.CategoryUrl.*;

@Tag(name = "Category API", description = "카테고리 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = CATEGORY_DEFAULT_URL)
public class CategoryController extends BaseController {

    private final GetCategoriesCommandDTOAssembler getCategoriesCommandDTOAssembler;
    private final CategoryQueryService categoryQueryService;

    @GetMapping(CATEGORIES)
    public ResponseEntity<Object> getCategories(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                @RequestParam(required = false, defaultValue = "1") Integer size,
                                                @RequestParam String pagingYn,
                                                @RequestParam(required = false, defaultValue = "") String categoryType,
                                                @RequestParam(required = false, defaultValue = "") String categoryCode,
                                                @RequestParam(required = false, defaultValue = "") String categoryName,
                                                @RequestParam(required = false, defaultValue = "false") boolean isCategoryNameContains) {
        GetCategoriesCommand command = getCategoriesCommandDTOAssembler.toCommand(page, size, pagingYn, categoryType, categoryCode, categoryName, isCategoryNameContains);
        CategoryListView categoryListview = categoryQueryService.getCategories(command);

        ResponseEntity.BodyBuilder response = ResponseEntity.ok()
                .headers(getSuccessHeaders());

        if(command.isNeedToPaging()) {
            GetCategoriesRspDto rspDto = getCategoriesCommandDTOAssembler.toRspDto(categoryListview);
            return response.body(rspDto);
        } else {
            return response.body(categoryListview.getCategoryList());
        }
    }

    @GetMapping(MAIN_CATEGORIES)
    public ResponseEntity<Object> getMainCategories() {

        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .body(null);
    }

    @GetMapping(SUB_CATEGORIES)
    public ResponseEntity<Object> getSubCategories() {

        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .body(null);
    }
}
