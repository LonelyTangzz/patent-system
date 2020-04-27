var searchType = 0;
function typeChage(type) {
    switch (type) {
        case 0: {
            $("#type").empty();
            searchType = 0;
            $("#type").append("                                    标题\n" +
                "                                    <span class=\"caret\"></span>");
            break;
        }
        case 1: {
            $("#type").empty();
            searchType = 1;
            $("#type").append("                                    类别\n" +
                "                                    <span class=\"caret\"></span>");
            break;
        }
        case 2: {
            $("#type").empty();
            searchType = 2;
            $("#type").append("                                    持有人\n" +
                "                                    <span class=\"caret\"></span>");
            break;
        }
        case 3: {
            $("#type").empty();
            searchType = 3;
            $("#type").append("                                    内容\n" +
                "                                    <span class=\"caret\"></span>");
            break;
        }
    }
}
function show() {
    alert(searchType);
}