package co.kr.suhyeong.project.product.interfaces.rest.controller;

import co.kr.suhyeong.project.product.application.internal.queryservice.CategoryQueryService;
import co.kr.suhyeong.project.product.domain.command.GetCategoriesCommand;
import co.kr.suhyeong.project.product.domain.model.view.CategoryView;
import co.kr.suhyeong.project.product.interfaces.rest.transform.GetCategoriesCommandDTOAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
                                                @RequestParam(required = false, defaultValue = "0") Integer size,
                                                @RequestParam String pagingYn,
                                                @RequestParam(required = false, defaultValue = "") String categoryType,
                                                @RequestParam(required = false, defaultValue = "") String categoryCode) {
        GetCategoriesCommand command = getCategoriesCommandDTOAssembler.toCommand(page, size, pagingYn, categoryType, categoryCode);
        List<CategoryView> categoryViewList = categoryQueryService.getCategories(command);
        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .body(categoryViewList);
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
