package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

// 관리자 - 상품 등록
@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/products/create.do")
public class ProductCreateController implements BaseController {
    private static final String UPLOAD_DIR = "/resources/upload";

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl(), new ProductCategoryRepositoryImpl(), new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productName = req.getParameter("product_name");
        String description = req.getParameter("product_desc");
        int price = Integer.parseInt(req.getParameter("product_price"));
        int stock = Integer.parseInt(req.getParameter("product_stock"));

        // 파일 업로드
        String image = null;
        String uploadPath = req.getServletContext().getRealPath(UPLOAD_DIR);
        log.debug("image upload Path : {}", uploadPath);
        File uploadFile = new File(uploadPath);

        if(!uploadFile.exists()) {
            uploadFile.mkdirs();
        }

        // image
        Part part = null;
        try {
            part = req.getPart("imageFile");

            if(part != null && part.getSize() > 0) {
                String saveFileName = UUID.randomUUID().toString(); // 파일명에 한글이 들어가면 인코딩 문제 발생해서
                part.write(uploadPath + File.separator + saveFileName);

                image = UPLOAD_DIR + "/" + saveFileName;
            }
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }

        //category
        String[] categoryIds = req.getParameterValues("categoryIds");

        if(categoryIds == null || categoryIds.length == 0) {
            req.setAttribute("error", "카테고리를 최소 1개 선택해야 합니다.");

            return "shop/admin/product_create";
        }

        try{
            Product product = new Product(productName, description, price, stock, image);
            productService.saveProduct(product, categoryIds);
        }catch (ProductAlreadyExistsException e) {
            req.setAttribute("error", "이미 등록된 상품입니다.");
            return "shop/admin/product_create";
        }
        return "redirect:/admin/products.do";
    }
}
