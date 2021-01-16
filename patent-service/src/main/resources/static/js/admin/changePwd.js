function submitChange() {
    if ($("#nowPassword").val() != $("#againPassword").val()) {
        $("#text").text("两次密码不一致，请重试!");
        $("#againPassword").val("");
        $("#nowPassword").val("");
    } else {
        $("#text").text("");
        var d = {"name":Cookies.get('adminName').toString(),"usedPassword":$("#usedPassword").val(),"nowPassword":$("#nowPassword").val()}
        var jsnStr = JSON.stringify(d);
        $.ajax({
            type: "POST",
            url: "/patent/admin/password/change.action",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data:jsnStr,
            success: function (data) {
                if(data.status==1){
                    $("#text").text(data.msg);
                    alert(data.msg);
                    Cookies.remove('adminName',);
                    window.location.href = "login";
                }else if(data.status ==0){
                    alert(data.msg);
                    $("#text").text(data.msg);
                    $("#againPassword").val("");
                    $("#nowPassword").val("");
                    $("#usedPassword").val("");
                }
            }
        });
    }
}