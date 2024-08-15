package com.bubbleshop.product.interfaces.rest.controller;

import com.bubbleshop.product.application.internal.commandservice.CategoryCommandService;
import com.bubbleshop.product.application.internal.queryservice.CategoryQueryService;
import com.bubbleshop.product.domain.command.CreateCategoryCommand;
import com.bubbleshop.product.domain.command.GetCategoriesCommand;
import com.bubbleshop.product.domain.command.ModifyCategoryCommand;
import com.bubbleshop.product.domain.model.view.CategoryListView;
import com.bubbleshop.product.interfaces.rest.dto.CreateCategoryReqDto;
import com.bubbleshop.product.interfaces.rest.dto.GetCategoriesRspDto;
import com.bubbleshop.product.interfaces.rest.dto.ModifyCategoryReqDto;
import com.bubbleshop.product.interfaces.rest.transform.CreateCategoryCommandDTOAssembler;
import com.bubbleshop.product.interfaces.rest.transform.GetCategoriesCommandDTOAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.bubbleshop.constants.StaticHeaders.BACKOFFICE_CHANNEL_HEADER;
import static com.bubbleshop.product.interfaces.rest.controller.CategoryUrl.*;

@Tag(name = "Category Management API", description = "카테고리 백오피스 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = CATEGORY_DEFAULT_URL, headers = BACKOFFICE_CHANNEL_HEADER)
public class CategoryManagementController extends BaseController {

    private final GetCategoriesCommandDTOAssembler getCategoriesCommandDTOAssembler;
    private final CategoryQueryService categoryQueryService;
    private final CreateCategoryCommandDTOAssembler createCategoryCommandDTOAssembler;
    private final CategoryCommandService categoryCommandService;

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

    @PostMapping(CATEGORIES)
    public ResponseEntity<Void> createCategory(@RequestBody @Validated CreateCategoryReqDto reqDto) {
        CreateCategoryCommand command = createCategoryCommandDTOAssembler.toCommand(reqDto);
        categoryCommandService.createCategory(command);
        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
    }

    @PutMapping(CATEGORY)
    public ResponseEntity<Void> modifyCategory(@PathVariable String categoryCode, @RequestBody @Validated ModifyCategoryReqDto reqDto) {
        ModifyCategoryCommand command = createCategoryCommandDTOAssembler.toCommand(categoryCode, reqDto);
        categoryCommandService.modifyCategory(command);
        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
    }

    @DeleteMapping(CATEGORY)
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryCode) {
        categoryCommandService.deleteCategory(categoryCode);
        return ResponseEntity.ok().headers(getSuccessHeaders()).build();
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
