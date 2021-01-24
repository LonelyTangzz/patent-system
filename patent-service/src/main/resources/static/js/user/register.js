function getVerifyCode() {
    $.ajax({
        type: "POST",
        url: "getVerifyCode",
        dataType: "json",
        data: {
            phoneNum: $("#phoneNum").val(),
            type: "0"
        },
        success: function (data) {
            var button = document.getElementById("verifyButton");
            if (data.status == 1) {
                button.innerText="发送成功";
            } else {
                button.innerText="发送失败";
            }
            button.href="";
        }
    });
}