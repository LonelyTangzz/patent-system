function getNewsByPage(num) {
    $("#allNews").empty();
    $.ajax({
        type: "GET",
        url: "/patent/getNewsByPage.action",
        dataType: "json",
        async: false,
        data: {
            page: num,
        },
        success: function (data) {
            total = data.total;
            jsonarray = data.newsList;
            var head = "<thead>\n" +
                "<tr>\n" +
                "<th>#</th>\n" +
                "<th>标题</th>\n" +
                "<th>作者</th>\n" +
                "<th>详情</th>\n" +
                "<th>操作</th>\n" +
                "</tr>\n" +
                "</thead>" + "<tbody>";
            for (var i = 0; i < jsonarray.length; i++) {
                window.sessionStorage.setItem('newsList[' + jsonarray[i].id + ']', JSON.stringify(jsonarray[i]));
                head = head + "<tr>" + "<th scope=\"row\" style='vertical-align: middle'>" + jsonarray[i].id + "</th>" +
                    "<td style='vertical-align: middle'>" + jsonarray[i].title + "</td>" +
                    "<td style='vertical-align: middle'>" + jsonarray[i].author + "</td>";
                head = head + "<td style='vertical-align: middle'><a onclick='newsDetails(" + jsonarray[i].id + ")' data-toggle='modal'  data-target='#newsDetails'>....</a></td>" +
                    "<td style='vertical-align: middle'>\n" + "<div class='btn-group'>" +
                    "<button type='button' class=\"btn btn-secondary\" onclick='deleNews(\"" + jsonarray[i].id + "\")'>删除本条</button></div>" + "</td>";
            }
            head = head + "</tbody>";
            $("#allNews").append(head);
        }
    });
}

function deleNews(id) {
    $.ajax({
        type: "POST",
        url: "/patent/deleNews.action",
        dataType: "json",
        data: {
            id: id
        },
        success: function (data) {
            if (data.code.toString() == "1") {
                alert(data.msg.toString());
                window.location.href = "newsControl";
            } else if (data.code.toString() == "0") {
                alert(data.msg.toString());
            }
        }
    });
}

function submitEditNews(id) {
    $("#text").text(" ");
    if ($("#title").val() == "") {
        $("#text").text("请输入标题！");
    } else if (tinymce.get('details_news').getContent() == "") {
        $("#text").text("文章内容为空！");
    } else if ($("#author").val() == "") {
        $("#text").text("请输入作者！");
    } else {
        $.ajax({
            type: "POST",
            url: "/patent/updateNews.action",
            dataType: "json",
            data: {
                id: parseInt(id),
                title: $("#title").val(),
                author: $("#author").val(),
                details: tinymce.get('details_news').getContent(),
            },
            success: function (data) {
                if (data.code.toString() == "1") {
                    alert(data.msg.toString());
                    window.location.href = "newsControl";
                } else if (data.code.toString() == "0") {
                    alert(data.msg.toString());
                }
            }
        });
    }
}

function newsDetails(id) {
    var object = JSON.parse(sessionStorage.getItem('newsList[' + id + ']'));
    $("#news_detail").empty();
    $("#newsTitle_detail").empty();
    $("#newsTitle_detail").append(object.title);
    detail = "<h2 class='text-center'>" + object.title + "</h2>" + "<br><p class='text-center'>作者：" + object.author + "<span>&nbsp;&nbsp;&nbsp;&nbsp;发布日期：" + object.uptime.substring(0, 10) + "</span></p><br>" + object.details;
    detail = detail + "<div class=\"modal-footer\">\n" +
        "<button type=\"button\" class=\"btn btn-primary\" onclick=\"changeNews(" + object.id + ")\">修改\n" +
        "</button>\n" +
        "<button type=\"button\" data-dismiss=\"modal\" class=\"btn btn-secondary\">关闭\n" +
        "</button>\n" +
        "</div>"
    $("#news_detail").append(detail);
}

function changeNews(id) {
    $("#news_detail").empty();
    var object = JSON.parse(sessionStorage.getItem('newsList[' + id + ']'));
    detail = "<div class=\"modal-body\">\n" +
        "<h5 style=\"text-align: center\" class=\"card-title\">修改新闻信息</h5>\n" +
        "<div class=\"form-group row\">\n" +
        "<label class=\"form-control-label\"><strong>标题<span class=\"text-red\">*</span></strong>请勿超过60个字!</label>\n" +
        " <div class=\"col-md-12\">\n" +
        "<input id=\"title\" type=\"text\" placeholder=\"文章标题\"\n" +
        "class=\"form-control form-check-inline\" name=\"title\" value='" + object.title + "'>\n" +
        " </div>\n" +
        " </div>\n" +
        "<div class=\"form-group row\">\n" +
        "<label class=\"form-control-label\"><strong>文章内容<span\n" +
        "class=\"text-red\">*</span>：</strong></label>\n" +
        "<div class=\"col-md-12\">\n" +
        "<textarea id=\"details_news\" name=\"details_news\">" + object.details + "</textarea>\n" +
        "</div>\n" +
        "</div>\n" + "</div>" + "<div class=\"modal-footer\">\n" +
        "<span id=\"text\" style=\"color: red\"></span>\n" +
        "<label class=\" form-control-label\"><strong>作者<span\n" +
        "class=\"text-red\">*</span>：</strong></label> <div>" +
        " <input id=\"author\" type=\"text\" name=\"author\"\n" +
        "class=\"form-control form-check-inline\" value='" + object.author + "'></div>" +
        "<button type=\"button\" class=\"btn btn-primary\" onclick=\"submitEditNews(" + object.id + ")\">提交\n" +
        "</button>\n" +
        "<button type=\"button\" data-dismiss=\"modal\" class=\"btn btn-secondary\">取消\n" +
        "</button>\n" +
        "</div>"
    //配置文本编辑器
    $("#news_detail").append(detail);
    tinymce.init({
        selector: '#details_news',
        language: 'zh_CN',
        height: 400,
        plugins: 'print preview importcss  searchreplace autolink autosave save directionality  visualblocks visualchars fullscreen image link media  template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists  wordcount   imagetools textpattern noneditable help    charmap   quickbars  emoticons',
        mobile: {
            plugins: 'print preview importcss  searchreplace autolink autosave save directionality  visualblocks visualchars fullscreen image link media  template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists  wordcount   textpattern noneditable help   charmap  quickbars  emoticons'
        },
        menu: {
            tc: {
                title: '',
                items: 'addcomment showcomments deleteallconversations'
            }
        },
        menubar: 'file edit view insert format tools table tc help',
        toolbar: 'undo redo | bold italic underline strikethrough | fontselect fontsizeselect formatselect | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist  | forecolor backcolor casechange   removeformat | pagebreak | charmap emoticons | fullscreen  preview save print | insertfile image media  template link anchor codesample | a11ycheck ltr rtl | showcomments addcomment',
        image_advtab: true,
    });
}


function currentPage(currentPage) {
    /*
        触发页码数位置： Page/js/jquery.z-pager.js 64行
    */
    getNewsByPage(currentPage);
}

$(document).ready(function () {
    getNewsByPage(1);
    $("#pager").zPager({
        totalData: total * 10, //数据总条数
        pageData: 10, //每页数据条数
        current: 1, //当前页码数
        pageStep: 5, //当前可见最多页码个数
        minPage: 4, //最小页码数，页码小于此数值则不显示上下分页按钮
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