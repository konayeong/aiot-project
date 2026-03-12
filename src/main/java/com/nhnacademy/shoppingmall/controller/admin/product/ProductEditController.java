package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/products/edit.do")
public class ProductEditController implements BaseController {
    private static final String UPLOAD_DIR = "/resources/upload";
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl(), new ProductCategoryRepositoryImpl(), new CategoryRepositoryImpl());


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            int productId = Integer.parseInt(req.getParameter("product_id"));
            String productName = req.getParameter("product_name");
            String description = req.getParameter("product_desc");
            int price = Integer.parseInt(req.getParameter("product_price"));
            int stock = Integer.parseInt(req.getParameter("product_stock"));

            // 기존 이미지
            String image = req.getParameter("existingImage");

            // 업로드 경로
            String uploadPath = req.getServletContext().getRealPath(UPLOAD_DIR);
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 파일 업로드
            Part part = req.getPart("imageFile");

            if (part != null && part.getSize() > 0) {
                String saveFileName = UUID.randomUUID().toString();
                part.write(uploadPath + File.separator + saveFileName);
                image = UPLOAD_DIR + "/" + saveFileName;
            }

            // category
            String[] categoryIds = req.getParameterValues("categoryIds");

            if (categoryIds == null || categoryIds.length == 0) {
                req.setAttribute("error", "카테고리를 최소 1개 선택해야 합니다.");
                return "shop/admin/product_edit";
            }

            // 상품 객체 생성
            Product product = new Product(productId, productName, description, price, stock, image);

            productService.updateProduct(product, categoryIds);

            return "redirect:/admin/products.do";

        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
