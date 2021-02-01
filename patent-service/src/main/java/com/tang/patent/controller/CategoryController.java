package com.tang.patent.controller;

import com.tang.api.CategoryApi;
import com.tang.basic.BaseController;
import com.tang.basic.BaseResp;
import com.tang.basic.ResponseResult;
import com.tang.patent.entity.bean.Category;
import com.tang.patent.logger.LoggerUtils;
import com.tang.patent.service.CategoryService;
import com.tang.vos.category.CategoryGetByPageVo;
import com.tang.vos.category.CategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/27.
 * @name: CategoryController
 * @description: 类别API实现类, 控制层
 */
@Slf4j
@RestController
public class CategoryController extends BaseController implements CategoryApi {
    /**
     * 打印日志用
     */
    LoggerUtils logger = new LoggerUtils(this.getClass().getName());

    @Resource
    CategoryService categoryService;

    /**
     * 添加分类接口
     *
     * @param categoryName 分类名
     * @return 操作结果
     */
    @Override
    public ResponseResult addCategory(String categoryName) {
        logger.startLog();
        BaseResp baseResp = categoryService.insert(categoryName);
        logger.endLog();
        return setResult(baseResp);
    }

    /**
     * 分页获取类别信息
     *
     * @param pageNum 页码
     * @return 该页信息
     */
    @Override
    public ResponseResult<CategoryGetByPageVo> getCategoryByPage(Integer pageNum) {
        logger.startLog();
        BaseResp<CategoryGetByPageVo> baseResp = new BaseResp<>();
        List<CategoryGetByPageVo> list = new ArrayList<>();
        CategoryGetByPageVo categoryGetByPageVo = new CategoryGetByPageVo();
        //将总数除以每页最大可显示数向上取整
        categoryGetByPageVo.setTotalPage((int) Math.ceil((double) categoryService.countCategory() / 5));
        categoryGetByPageVo.setCategoryList(categoryService.getPageCategory(pageNum));
        list.add(categoryGetByPageVo);
        baseResp.setRespData(list);
        logger.endLog();
        return setResult(baseResp);
    }

    /**
     * 修改类别信息操作
     *
     * @param pkId         类别主键
     * @param categoryName 类别名
     * @return 操作结果
     */
    @Override
    public ResponseResult editCategory(Long pkId, String categoryName) {
        logger.startLog();
        Category category = new Category();
        category.setPkId(pkId);
        category.setCategoryName(categoryName);
        BaseResp baseResp = categoryService.updateCategory(category);
        logger.endLog();
        return setResult(baseResp);
    }

    /**
     * 删除类别操作
     *
     * @param pkId 类别主键
     * @return 操作结果
     */
    @Override
    public ResponseResult deleteCategory(Long pkId) {
        logger.startLog();
        BaseResp baseResp = categoryService.deleteCategory(pkId);
        logger.endLog();
        return setResult(baseResp);
    }

    /**
     * 获取所有类别信息
     *
     * @return 类别信息
     */
    @Override
    public ResponseResult<List<CategoryVo>> getAllCategory() {
        logger.startLog();
        BaseResp<CategoryVo> baseResp = new BaseResp();
        baseResp.setRespData(categoryService.getAllCategory());
        logger.endLog();
        return setResult(baseResp);
    }
}
