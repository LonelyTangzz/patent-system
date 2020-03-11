$.ajax({
    type: "POST",
    url: "/patent/countAll.action",
    dataType: "json",
    success: function (data) {
        $("#patentCount").text(data.patentCount+"项");
        $("#userCount").text(data.userCount+"位");
        $("#newsCount").text(data.newsCount+"条");
        $("#categoryCount").text(data.categoryCount+"种");
    }
});