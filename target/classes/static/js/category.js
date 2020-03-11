function showCategory() {
    $.ajax({
        type: "POST",
        url: "/patent/getAllCategory.action",
        dataType: "json",
        success: function (data) {
            jsonarray = data.allCategory;
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
                    "class=\"btn btn-primary\" class=\"btn btn-primary\" onclick='changeName("+jsonarray[i].id+",\""+jsonarray[i].category+"\")'>编辑</button>\n" +
                    "<button class=\"btn btn-secondary\" onclick='dele("+jsonarray[i].id+")'>删除</button>\n" +
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
                window.location.href = "/patent/views/admin/categoryControl.html";
            }
        }
    });
}

function changeName(id,name){
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
                window.location.href = "/patent/views/admin/categoryControl.html";
            }
        }
    });
}

function dele(id){
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
                window.location.href = "/patent/views/admin/categoryControl.html";
            }
        }
    });
}

$(function () {
    showCategory();
});