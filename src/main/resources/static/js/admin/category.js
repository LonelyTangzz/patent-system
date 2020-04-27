function showCategory(num) {
    $.ajax({
        type: "GET",
        url: "/patent/getPageCategory.action",
        dataType: "json",
        async: false,
        data: {
            page: num,
        },
        success: function (data) {
            total = data.total;
            $("#allCategory").empty();
            jsonarray = data.Category;
            var head = "<thead>\n" +
                "<tr>\n" +
                "<th>#</th>\n" +
                "<th>行业类别</th>\n" +
                "<th>操作</th>\n" +
                "</tr>\n" +
                "</thead>" + "<tbody>";
            for (var i = 0; i < jsonarray.length; i++) {
                head = head + "<tr>" + "<th scope=\"row\">" + jsonarray[i].id + "</th>" +
                    "<td>" + jsonarray[i].category + "</td>" + "<td>\n" +
                    "<button type='button' data-toggle=\"modal\" data-target=\"#editCategory\"\n" +
                    "class=\"btn btn-primary\" class=\"btn btn-primary\" onclick='changeName(" + jsonarray[i].id + ",\"" + jsonarray[i].category + "\")'>编辑</button>\n" +
                    "<button class=\"btn btn-secondary\" onclick='dele(" + jsonarray[i].id + ")'>删除</button>\n" +
                    "</td>";
            }
            head = head + "</tbody>";
            $("#allCategory").append(head);

        }
    });
}

function submitAdd() {
    var typeName = $("#typeName").val();
    $.ajax({
        type: "POST",
        url: "/patent/addCategory.action",
        data: {
            typeName: typeName
        },
        dataType: "json",
        success: function (data) {
            if (data.code.toString() === "0") {
                alert(data.msg.toString());
            } else if (data.code.toString() === "1") {
                alert(data.msg.toString())
                window.location.href = "categoryControl";
            }
        }
    });
}
function currentPage(currentPage) {
    // 触发页码数位置： Page/js/jquery.z-pager.js 64行
    showCategory(currentPage);
}

function changeName(id, name) {
    $("#beforeName").val(name);
    $("#hidden_id").val(id);
}

function submitEditCategory() {
    var id = $("#hidden_id").val();
    var name = $("#nowName").val();
    $.ajax({
        type: "POST",
        url: "/patent/editCategory.action",
        data: {
            id: id,
            name: name
        },
        dataType: "json",
        success: function (data) {
            if (data.code.toString() === "0") {
                alert(data.msg.toString());
            } else if (data.code.toString() === "1") {
                alert(data.msg.toString())
                window.location.href = "categoryControl";
            }
        }
    });
}

function dele(id) {
    $.ajax({
        type: "POST",
        url: "/patent/deleteCategory.action",
        data: {
            id: id
        },
        dataType: "json",
        success: function (data) {
            if (data.code.toString() === "0") {
                alert(data.msg.toString());
            } else if (data.code.toString() === "1") {
                alert(data.msg.toString());
                window.location.href = "categoryControl";
            }
        }
    });
}

$(function () {
    showCategory(1);
    $("#pager").zPager({
        totalData: total * 10, //数据总条数
        pageData: 10, //每页数据条数
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