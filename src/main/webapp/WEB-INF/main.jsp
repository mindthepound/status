<%--
  Created by IntelliJ IDEA.
  User: Dennis Na
  Date: 2018. 3. 28.
  Time: AM 9:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="tComponent" %>

<t:template title="Title Test">
    <jsp:attribute name="header">
        <link href="/css/common.css" rel="stylesheet" type="text/css" />
        <style type="text/css">
            .chart-container {
                display: flex;
                flex-wrap: wrap;
            }
            .chart-item {
                width: 100%;
            }
            @media (min-width: 1200px) {
                .chart-item {
                    width: 50%;
                }
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="javascriptSrc">
        <script src="/vendor/chartjs/Chart.min.js" type="text/javascript"></script>
        <script src="/js/utils/date.js"></script>
        <script src="/js/common.js"></script>
        <script type="text/javascript">

            /**
             * 비밀번호 변경 확인
             * */
	        checkLastPassword = () => {
		        const today = new Date();
		        let lastPwPlus3M = new Date($.cookie('LAST_PASSWORD').split('T')[0]);
		        lastPwPlus3M.setMonth(lastPwPlus3M.getMonth() + 3);

		        // 마지막 변경일로부터 3개월이 지나지 않았을 경우
		        if (today <= lastPwPlus3M) return;

		        window.location.replace('/change-pw');
	        };


            let _chart = {
	            currItems: null,
	            smsMonthToDateSpentUSD: null,
	            numberOfMessagesPublished: null
            };

            /**
             * call dashboard data
             * */
            callData = () => {
	            let startValue = document.getElementById('startId').value
		            , endValue = document.getElementById('endId').value
		            , periodValue = parseInt(document.getElementById('period').value);

	            if (!startValue || !endValue || !periodValue) return;

	            startValue = new Date(startValue).getTime();
	            endValue = new Date(endValue + ' 23:59:59').getTime();

	            common.ajaxCall({
		            url: '/cloudWatch/?start=' + startValue + '&end=' + endValue + '&period=' + periodValue,
		            type: 'GET',
		            callback: (result) => {

			            if (!result || result.getMetricDataResult.metricDataResults.length === 0) return;

			            const results = result.getMetricDataResult.metricDataResults;
			            for (let item of results) {
				            const ctx = document.getElementById(item.id).getContext('2d');

				            let timestamps = item.timestamps;
				            timestamps = timestamps.map((t) => {
					            return t.substr(0, t.length - 12);
				            });

				            if (_chart[item.id]) {
					            _chart[item.id].clear().destroy();
					            _chart[item.id] = null;
				            }
				            _chart[item.id] = new Chart(ctx, {
					            type: 'line',
					            data: {
						            labels: timestamps.reverse(),
						            datasets: [{
							            label: item.label,
							            data: item.values.reverse(),
							            backgroundColor: 'rgb(46, 78, 193, 0.5)',
							            borderColor: 'rgb(46, 78, 193, 1)',
							            borderWidth: 1
						            }]
					            }
				            });

			            }
		            }
	            })
            };

	        $(document).ready(() => {
		        checkLastPassword();

		        const today = new Date();
		        document.getElementById('endId').value = getDateMask(today, '-');
		        today.setMonth(today.getMonth() - 1);
		        document.getElementById('startId').value = getDateMask(today, '-');
		        callData();

		        $('#search').on('click', () => {
                  callData();
                });
	        })
        </script>
    </jsp:attribute>
    <jsp:body>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">대시보드 <span class="small">UTC 시간으로 표시됩니다.</span></h1>
                </div>
                <div class="form-inline form-control-static" style="text-align: right;">
                    <tComponent:period-date startId="startId" endId="endId" containerClassName="inline-block"/>
                    <input type="number" class="form-control" id="period" value="12000" placeholder="period"/>
                    <button type="button" class="btn btn-default" id="search">search</button>
                </div>
            </div>

            <div class="row well chart-container">
                <div class="chart-item no-padding">
                    <canvas id="currItems"></canvas>
                </div>

                <div class="chart-item no-padding">
                    <canvas id="smsMonthToDateSpentUSD"></canvas>
                </div>

                <div class="chart-item no-padding">
                    <canvas id="numberOfMessagesPublished"></canvas>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
