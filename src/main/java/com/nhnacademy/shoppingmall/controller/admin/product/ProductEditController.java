package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.CategoryNotFoundException;
import com.nhnacademy.shoppingmall.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class ProductEditController{
    private final ProductService productService;

    public ProductEditController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping( "/admin/products/edit.do")
    public String execute(@RequestParam(name = "product_id") int productId,
                          @RequestParam(name = "product_name") String productName,
                          @RequestParam(name = "product_desc") String description,
                          @RequestParam(name = "product_price") int price,
                          @RequestParam(name = "product_stock") int stock,
                          @RequestParam(name = "existingImage") String image,
                          @RequestParam("imageFile") MultipartFile imageFile,
                          @RequestParam(value = "categoryIds", required = false) String[] categoryIds,
                          Model model
                          ) {

            // 업로드 경로
            String uploadPath = System.getProperty("user.home") + "/upload";
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            try {
                if (imageFile != null && !imageFile.isEmpty()) {
                    String saveFileName = UUID.randomUUID().toString();
                    File dest = new File(uploadPath, saveFileName);
                    imageFile.transferTo(dest);

                    image = "/upload/" + saveFileName;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // category
            if (categoryIds == null || categoryIds.length == 0) {
                model.addAttribute("error", "카테고리를 최소 1개 선택해야 합니다.");
                return "shop/admin/product_edit";
            }

            // 상품 객체 생성
        try {
            Product product = new Product(productId, productName, description, price, stock, image);

            productService.updateProduct(product, categoryIds);
        }catch (ProductNotFoundException e) {
            model.addAttribute("error", "존재하지 않는 상품입니다.");
            return "shop/admin/product_edit";
        }catch (CategoryNotFoundException e) {
            model.addAttribute("error", "존재하지 않는 카테고리가 포함되어 있습니다.");
            return "shop/admin/product_edit";
        }

            return "redirect:/admin/products.do";

    }
}
