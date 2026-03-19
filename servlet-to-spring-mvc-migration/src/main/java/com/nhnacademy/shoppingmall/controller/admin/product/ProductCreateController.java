package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.CategoryNotFoundException;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

// 관리자 - 상품 등록
@Slf4j
@Controller
public class ProductCreateController{
    private final ProductService productService;

    public ProductCreateController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/admin/product/create.do")
    // TODO Q 폼데이터가 많으면 DTO로?
    public String execute(@RequestParam("product_name") String productName,
                          @RequestParam("product_desc") String description,
                          @RequestParam("product_price") int price,
                          @RequestParam("product_stock") int stock,
                          @RequestParam("imageFile") MultipartFile imageFile,
                          @RequestParam(value = "categoryIds", required = false) String[] categoryIds,
                          Model model
                          ) {
        // 파일 업로드
        String image = null;
        String uploadPath = System.getProperty("user.home") + "/upload";
        log.debug("image upload Path : {}", uploadPath);
        File uploadFile = new File(uploadPath);

        if(!uploadFile.exists()) {
            uploadFile.mkdirs();
        }

        // image
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String saveFileName = UUID.randomUUID().toString();
                File dest = new File(uploadPath, saveFileName);
                imageFile.transferTo(dest); // 톰캣 임시 폴더에서 실제 저장소로 이동

                image = "/upload/" + saveFileName;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //category
        if(categoryIds == null || categoryIds.length == 0) {
            model.addAttribute("error", "카테고리를 최소 1개 선택해야 합니다.");
            // TODO Q 문제 (카테고리를 불러오지 않음)
            return "shop/admin/product_create";
        }

        try{
            Product product = new Product(productName, description, price, stock, image);
            productService.saveProduct(product, categoryIds);
        }catch (ProductAlreadyExistsException e) {
            model.addAttribute("error", "이미 등록된 상품입니다.");
            return "shop/admin/product_create";
        }catch (CategoryNotFoundException e) {
            model.addAttribute("error", "존재하지 않는 카테고리가 포함되어 있습니다.");
            return "shop/admin/product_create";
        }
        return "redirect:/admin/products.do";
    }
}
