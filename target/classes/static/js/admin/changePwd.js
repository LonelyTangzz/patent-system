function submitChange() {
    if ($("#nowPassword").val() != $("#againPassword").val()) {
        $("#text").text("两次密码不一致，请重试!");
        $("#againPassword").val("");
        $("#nowPassword").val("");
    } else {
        $("#text").text("");
        $.ajax({
            type: "POST",
            url: "/patent/changeAdminPwd.action",
            dataType: "json",
            data: {
                name: localStorage.getItem('adminName'),
                usedPwd: $("#usedPassword").val(),
                nowPwd: $("#nowPassword").val(),
            },
            success: function (data) {
                if(data.code==1){
                    $("#text").text(data.msg);
                    alert(data.msg);
                    window.location.href = "/patent/views/admin/main.html";
                }else if(data.code ==0){
                    $("#text").text(data.msg);
                    $("#againPassword").val("");
                    $("#nowPassword").val("");
                    $("#usedPassword").val("");
                }else if(data.code==-1){
                    alert(data.msg);
                    window.location.href = "/patent/views/admin/changePassword.html";
                }
            }
        });
    }
}