//专利分类饼图
function loadCategoryType() {
    $.ajax({
        type: "GET",
        url: "/patent/loadCategoryType.action",
        dataType: "json",
        success: function (data) {
            //取出类型和相应类型下的专利数
            var type = new Array()
            var total = [];
            for (var i = 0; i < data.result.length; i++) {
                total.push({value: data.result[i].total, name: data.result[i].category});
                type[i] = data.result[i].category;
            }
            var categoryType = echarts.init(document.getElementById('categoryType'), 'walden');
            var categoryOption = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b}: {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 20,
                    data: type
                },
                series: [
                    {
                        name: '专利数',
                        type: 'pie',
                        radius: ['50%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data:
                        total
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            categoryType.setOption(categoryOption);
        }
    });
}

// 获取当月用户注册线形图
function loadLocalMonthData(year, month, date) {
    $.ajax({
        type: "GET",
        url: "/patent/getUserTimeByMonth.action",
        data: {
            year: year,
            month: month,
        },
        dataType: "json",
        success: function (data) {
            var userCurve = echarts.init(document.getElementById('userCurve'), 'walden');
            var day = new Array()
            var total = new Array();
            var j = 0;
            //做了一个遍历
            for (var i = 0; i < date; i++) {
                if (i == data.result[j].register_time - 1 && j < data.result.length - 1) {
                    day[i] = month + "月" + data.result[j].register_time + "日";
                    total[i] = data.result[j].total;
                    j++;
                } else if (i == data.result[j].register_time - 1 && j == data.result.length - 1) {
                    day[i] = month + "月" + data.result[j].register_time + "日";
                    total[i] = data.result[j].total;
                } else {
                    day[i] = month + "月" + (i + 1) + "日";
                    total[i] = 0;
                }
            }
            // 指定图表的配置项和数据
            var curveOption = {
                title: {
                    text: year + '年' + month + '月新增用户数量变化率'
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: day
                },
                yAxis: {
                    type: 'value'
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        magicType: {
                            show: true,
                            type: ['pie', 'funnel']
                        },
                        saveAsImage: {
                            show: true,
                            excludeComponents: ['toolbox'],
                            pixelRatio: 1,
                            backgroundColor: 'white'
                        }
                    }
                },
                series: [{
                    name: '用户注册数',
                    data: total,
                    type: 'line',
                    smooth: true,
                    areaStyle: {}
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            userCurve.setOption(curveOption);
        }
    });
}

//这是两个获取日期的函数
function getDay(day) {
    var today = new Date();
    var targetday_milliseconds = today.getTime() + 1000 * 60 * 60 * 24 * day;
    today.setTime(targetday_milliseconds); //注意，这行是关键代码
    var tYear = today.getFullYear();
    var tMonth = today.getMonth();
    var tDate = today.getDate();
    tMonth = doHandleMonth(tMonth + 1);
    tDate = doHandleMonth(tDate);
    return tYear + "-" + tMonth + "-" + tDate;
}

function doHandleMonth(month) {
    var m = month;
    if (month.toString().length == 1) {
        m = "0" + month;
    }
    return m;
}

// 获取近七日新闻增加数
function loadNewsData() {
    $.ajax({
        type: "GET",
        url: "/patent/countRecentNews.action",
        dataType: "json",
        success: function (data) {
            //封装后台数据
            var day = new Array();
            var total = new Array();
            var j = 0;
            var sevenDayNews = echarts.init(document.getElementById('sevenDayNews'), 'walden');
            for (var i = 0; i < 7; i++) {
                day[i] = getDay(i - 6).substring(5, 10);
                if (j < data.result.length) {
                    if (data.result[j].uptime == getDay(i - 6)) {
                        total[i] = data.result[j].total;
                        j++;
                    } else {
                        total[i] = 0;
                    }
                } else
                    total[i] = 0;
            }
            //封装后台数据
            var option = {
                color: ['#F0404C'],
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: day,
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: '新闻数量',
                        type: 'bar',
                        barWidth: '60%',
                        data: total
                    }
                ]
            };
            sevenDayNews.setOption(option);
        }
    });
}

function init() {
    $.ajax({
        type: "GET",
        url: "/patent/countAll.action",
        dataType: "json",
        success: function (data) {
            $("#patentCount").text(data.patentCount + "项");
            $("#userCount").text(data.userCount + "位");
            $("#newsCount").text(data.newsCount + "条");
            $("#categoryCount").text(data.categoryCount + "种");
        }
    });
    var date = new Date();
    //获取当前月用户注册人数;
    loadLocalMonthData(date.getFullYear(), date.getMonth() + 1, date.getDate());
    loadCategoryType();
    loadNewsData();
}

$(document).ready(function () {
    init();
});
