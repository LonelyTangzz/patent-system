function getVerify() {
    $.ajax({
        type: "POST",
        url: "verifyPhone",
        dataType: "json",
        data: {
            phoneNum: $("#phoneNum").val()
        },
        success: function (data) {
            if (data) {
                $("#verifyButton").text("发送成功");
                $("#verifyButton").href("");
            } else {
                $("#verifyButton").text("发送失败");
            }
        }
    });
}