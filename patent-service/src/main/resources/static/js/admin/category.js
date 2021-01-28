/**
 * 分页获取类别信息
 * @param num 页码
 */
function showCategory(num) {
    $.ajax({
        type: "GET",
        url: "/category/page/get",
        dataType: "json",
        async: false,
        data: {
            pageNum: num,
        },
        success: function (res) {
            var data = res.data;
            total = data[0].totalPage;
            $("#allCategory").empty();
            jsonarray = data[0].categoryList;
            var head = "<thead>\n" +
                "<tr>\n" +
                "<th>#</th>\n" +
                "<th>行业类别</th>\n" +
                "<th>操作</th>\n" +
                "</tr>\n" +
                "</thead>" + "<tbody>";
            for (var i = 0; i < jsonarray.length; i++) {
                head = head + "<tr>" + "<th scope=\"row\">" + (i + 1) + "</th>" +
                    "<td>" + jsonarray[i].categoryName + "</td>" + "<td>\n" +
                    "<button type='button' data-toggle=\"modal\" data-target=\"#editCategory\"\n" +
                    "class=\"btn btn-primary\" class=\"btn btn-primary\" onclick='changeName(" + jsonarray[i].pkId + ",\"" + jsonarray[i].categoryName + "\")'>编辑</button>\n" +
                    "<button class=\"btn btn-secondary\" onclick='deleteCategory(" + jsonarray[i].pkId + ")'>删除</button>\n" +
                    "</td>";
            }
            head = head + "</tbody>";
            $("#allCategory").append(head);

        }
    });
}

/**
 * 新增类别
 */
function submitAdd() {
    var categoryName = $("#categoryName").val();
    $.ajax({
        type: "POST",
        url: "/category/add",
        data: {
            categoryName: categoryName
        },
        dataType: "json",
        success: function (data) {
            alert(data.msg);
            if (data.status == 0) {
                window.location.href = "categoryControl";
            }
        }
    });
}

/**
 * 当前页码调用显示页面
 * @param currentPage 当前页码
 */
function currentPage(currentPage) {
    // 触发页码数位置： Page/js/jquery.z-pager.js 64行
    showCategory(currentPage);
}

/**
 * 点击编辑后，将pkId以及原信息放入弹窗中
 * @param id
 * @param name
 */
function changeName(id, name) {
    $("#beforeName").val(name);
    $("#hidden_id").val(id);
}

/**
 * 提交类别修改
 */
function submitEditCategory() {
    var id = $("#hidden_id").val();
    var name = $("#nowName").val();
    $.ajax({
        type: "POST",
        url: "/category/edit",
        data: {
            pkId: id,
            categoryName: name
        },
        dataType: "json",
        success: function (data) {
            alert(data.msg);
            if (data.status == 0) {
                window.location.href = "categoryControl";
            }
        }
    });
}

function deleteCategory(id) {
    $.ajax({
        type: "DELETE",
        url: "/category/delete",
        data: {
            pkId: id
        },
        dataType: "json",
        success: function (data) {
            alert(data.msg);
            if (data.status == 0) {
                window.location.href = "categoryControl";
            }
        }
    });
}

$(function () {
    showCategory(1);
    $("#pager").zPager({
        totalData: total * 5, //数据总条数
        pageData: 5, //每页数据条数
        current: 1, //当前页码数
        pageStep: 6, //当前可见最多页码个数
        minPage: 5, //最小页码数，页码小于此数值则不显示上下分页按钮
        active: 'current', //当前页码样式
        prevBtn: 'pg-prev', //上一页按钮
        nextBtn: 'pg-next', //下一页按钮
        btnBool: true, //是否显示上一页下一页
        firstBtn: 'pg-first', //第一页按钮
        lastBtn: 'pg-last', //最后一页按钮
        btnShow: true, //是否显示第一页和最后一页按钮
        ajaxSetData: false, //是否使用ajax获取数据 此属性为真时需要url和htmlBox不为空
        htmlBox: $('#wraper') //ajax数据写入容器
    });
});