google.charts.load('current', {'packages' : [ 'corechart', 'bar' ]});
google.charts.setOnLoadCallback(drawChart);
var chart;
var chart2;

    	// 차트 스케줄러 실행
     function drawChart() {
      chart = new google.visualization.CandlestickChart(document.getElementById('chart_div'));
      chart2 = new google.visualization.BarChart(document.getElementById('chart_div2'));
      selectChartData(stockId)
      selectAskData(stockId)
      stockDetailInfo();
      marketprice();
      setInterval(function(){selectChartData(stockId)}, 5000);
      setInterval(function(){selectAskData(stockId)}, 5000);
      setInterval(function(){stockDetailInfo()}, 5000);
      setInterval(function(){marketprice()}, 5000);
    }

// 주가 그래프 DB 조회 + 차트 함수 실행
let selectChartData = function(stockId){
    let type = $('.chartVariable').val()
    $.ajax({
        type : 'post',           // 타입 (get, post, put 등등)
        url : '/influ/stockgraph',           // 요청할 서버url
        async : true,            // 비동기화 여부 (default : true)
        headers : {              // Http header
          "Content-Type" : "application/json",
        },
        dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
        data : JSON.stringify({
            "stockId" : stockId,
            "type": type
        }),
        success : function(result) { // 결과 성공 콜백함수
            if(result.length != 0){
            if(type == 'T'){
                stockChart(makeTodayChartData(result))
            } else {
                stockChart(makeChartData(result))
            }
        } else {
           $('#chart_div').html('<h2>거래 내역이 존재하지 않습니다.</h2>')
        }
           
        },
        error : function(request, status, error) { // 결과 에러 콜백함수
            console.log(error)
            return ;
        }
    })
}

// DB 정보 -> 구글 차트용 배열로 가공 (1일, 3일)
let makeChartData = function(array=[]){

    let exchangeDateAry = [];
    array.forEach(function(date, index){
            exchangeDateAry.push(date.settleDate);
    });

    exchangeDateAry = exchangeDateAry.filter(function(el, index, array){
      return array.indexOf(el) === index
    })

    var allChartData = [];
    exchangeDateAry.forEach(function(date, index){
        var chartData = [];
        var standard = date.substring(5,10)
        chartData.push(standard);
        var min = null;
        var max = null;
        var start = null;
        var end = null;
        array.forEach(function(vo, index){
            if(date === vo.settleDate){
                if(min == null){
                    min = vo.exchangePrice;
                } else if(min > vo.exchangePrice){
                    min = vo.exchangePrice;
                }

                if(max == null){
                    max = vo.exchangePrice;
                } else if(max < vo.exchangePrice){
                    max = vo.exchangePrice;
                }

                if(end == null){
                    end = vo.exchangePrice;
                }
                
                start = vo.exchangePrice;

            }
        })
        chartData.push(min);
        chartData.push(end);
        chartData.push(start);
        chartData.push(max);
        
        allChartData.push(chartData);
    })
    return allChartData;
}

// DB 정보 -> 구글 차트용 배열로 가공 (금일)
let makeTodayChartData = function(array=[]){

    let exchangeDateAry = [];
    array.forEach(function(date, index){
            exchangeDateAry.push(date.todaychart);
        
    });
    exchangeDateAry = exchangeDateAry.filter(function(el, index, array){
      return array.indexOf(el) === index
    })
    var allChartData = [];
    exchangeDateAry.forEach(function(date, index){
        var chartData = [];
        chartData.push(date);
        var min = null;
        var max = null;
        var start = null;
        var end = null;
        array.forEach(function(vo, index){
            if(date === vo.todaychart){
                if(min == null){
                    min = vo.exchangePrice;
                } else if(min > vo.exchangePrice){
                    min = vo.exchangePrice;
                }

                if(max == null){
                    max = vo.exchangePrice;
                } else if(max < vo.exchangePrice){
                    max = vo.exchangePrice;
                }

                if(end == null){
                    end = vo.exchangePrice;
                }
                
                start = vo.exchangePrice;

            }


        })
        chartData.push(min);
        chartData.push(end);
        chartData.push(start);
        chartData.push(max);
        allChartData.push(chartData);
    })
    return allChartData;
}

// 구글 차트 함수
let stockChart = function(chartData){
        var data = google.visualization.arrayToDataTable(chartData, true);

        var options = {
            legend : 'none',
            height: 400,
            bar : {
                groupWidth : '100%'
            }, // Remove space between bars.
            candlestick : {
                fallingColor : {
                    strokeWidth : 0,
                    fill : '#0077ff'
                }, 
                risingColor : {
                    strokeWidth : 0,
                    fill : '#ff3333'
                }
            }
        };
    
        chart.draw(data, options);   
    }
    	

    // 호가 DB 조회 + 호가 차트 함수 실행
    let selectAskData = function(stockId){
        $.ajax({
            type : 'post',           // 타입 (get, post, put 등등)
            url : '/influ/askgraph',           // 요청할 서버url
            async : true,            // 비동기화 여부 (default : true)
            headers : {              // Http header
              "Content-Type" : "application/json",
            },
            dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
            data : JSON.stringify({
                "stockId" : stockId,
            }),
            success : function(result) { // 결과 성공 콜백함수
                if(result.askgraph.length != 0 || result.bidgraph.length != 0){
                askChart(makeAskgraphData(result.askgraph, result.bidgraph))
                } else {
                    $('#chart_div2').html('<h2>거래 내역이 존재하지 않습니다.</h2>')
               }
            },
            error : function(request, status, error) { // 결과 에러 콜백함수
                console.log(error)
                return ;
            }
        })
    }

    // 호가 데이터 가공
    let makeAskgraphData = function(array, array2){
        let allgraphArray = []
        allgraphArray.push([ '호가', '수량', {role: 'style', type: 'string' }, {role: 'annotation', type: 'string' }]);
        array.forEach((vo, index) => {
            allgraphArray.push([vo.exchangePrice, vo.exchangeAmount, '#0077ff', '매도'])
        })
        array2.forEach((vo, index) => {
			allgraphArray.push([vo.exchangePrice, vo.exchangeAmount, '#ff3333', '매수'])
        })
        return allgraphArray;
    }
   
    // 호가 차트 함수
    let askChart = function(array){

            var data = google.visualization.arrayToDataTable(array);

            var options = {
                title: "호가",
                width: 600,
                height: 400,
                bar: {groupWidth: "95%"},
                legend: { position: "none" },
                bars : 'horizontal'
              };

            chart2.draw(data, options);

            // 차트 이벤트 핸들러
            function selectHandler() {
                var selectedItem = chart2.getSelection()[0];
                if (selectedItem) {
                  var price = data.getValue(selectedItem.row, 0)
                  var amount = data.getValue(selectedItem.row, 1)
                  var type = data.getValue(selectedItem.row, 3)
                  $('#sub_amount').val(amount)
                  $('#sub_price').val(price);
                  if(type == '매수'){
                    $('.bidradio').prop("checked", true)
                  } else if(type == '매도'){
                    $('.askradio').prop("checked", true)
                  }
                }
              }
              
            google.visualization.events.addListener(chart2, 'select', selectHandler);
        }


    // 시장가 호출 함수
    function marketprice(){
        if(!$('.marketprice').is(':checked')){
            return;
        }
        $.ajax({
            type : 'post',           // 타입 (get, post, put 등등)
            url : '/influ/askgraph',           // 요청할 서버url
            async : true,            // 비동기화 여부 (default : true)
            headers : {              // Http header
              "Content-Type" : "application/json",
            },
            dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
            data : JSON.stringify({
                "stockId" : stockId,
            }),
            success : function(result) { // 결과 성공 콜백함수
                if($('input[name="type"]:checked').val() == 'S'){
                    $('#sub_price').val(result.bidgraph[0].exchangePrice);
                } else if ($('input[name="type"]:checked').val() == 'B'){
                    $('#sub_price').val(result.askgraph[3].exchangePrice);
                }
            },
            error : function(request, status, error) { // 결과 에러 콜백함수
                console.log(error)
            }
        })
    }

    // 거래 등록
 let insertExchange = function(){
    
        let submitPrice = $('#sub_price').val();
        let submitAmount = $('#sub_amount').val();
        let submitType = $('input[name="type"]:checked').val();
        let myPointstr = $('.myPoint').text().replace(",", "")
        let length = myPointstr.length
        let myPoint = Number(myPointstr.substr(0, length-1));
        if(submitType == 'S'){
            if(Number(submitAmount) > Number($('.myStockAmount').text())){
                
                swal('판매하려는 수량이 보유 수량보다 많습니다.')
                return;
            } 
        } else if(submitType == 'B'){
            if((submitAmount*submitPrice) > myPoint){
                swal('구매에 필요한 포인트가 부족합니다.');
                return;
            }
        }

         $.ajax({
            type : 'post',           // 타입 (get, post, put 등등)
            url : '/influ/insertexchange',           // 요청할 서버url
            async : true,            // 비동기화 여부 (default : true)
            headers : {              // Http header
              "Content-Type" : "application/json",
            },
            dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
            data : JSON.stringify({
                "stockId" : stockId,
                "exchangeType" : submitType,
                "exchangePrice" : submitPrice,
                "exchangeAmount" : submitAmount,
                "memberId" : memberId
            }),
            success : function(result) { // 결과 성공 콜백함수
                swal("결과", "등록 성공", "success");
                selectChartData(stockId)
                selectAskData(stockId)
                stockDetailInfo();
                myExchange()
            },
            error : function(error) { // 결과 에러 콜백함수
                console.log(error)
            }
        })
    }

    // 주식 상세 정보 조회
let stockDetailInfo = function(){
        $.ajax({
            type : 'post',           // 타입 (get, post, put 등등)
            url : '/influ/stockdetailinfo',           // 요청할 서버url
            async : true,            // 비동기화 여부 (default : true)
            headers : {              // Http header
              "Content-Type" : "application/json",
            },
            dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
            data : JSON.stringify({
                "stockId" : stockId,
                "memberId" : memberId
            }),
            success : function(result) { // 결과 성공 콜백함수
                console.log(result)
                insertDetailInfo(result);
            
            },
            error : function(request, status, error) { // 결과 에러 콜백함수
                console.log(error)
            }
        })
    }

let insertDetailInfo = function(result){
	let capitalization = (result.svo.marketValue)*(result.svo.stockQuantity);
	let myPoint = result.mvo.memberPoint
    
    let firstDate = new Date(result.firstDate);
    let lastDate = new Date(result.lastDate);
    console.log(lastDate)

    $('.capital').html(capitalization.toLocaleString() + 'p')
    $('.quantity').html(result.svo.stockQuantity)
    $('.price').html(result.svo.marketValue + 'p' + '<sub>/주당</sub>')
    $('.largeHolder').html('<i class="fas fa-circle"></i>' + ' 최대 주주 : ' + result.largeHolder)
    //$('.firstDate').html('<i class="fas fa-circle"></i>' + ' 첫 상장일 : ' + firstDate.toLocaleDateString())
    //$('.lastDate').html('<i class="fas fa-circle"></i>' + ' 최근 상장일 : ' + lastDate.toLocaleDateString())
    $('.allocation').html('<i class="fas fa-circle"></i>' + ' 지난 배당금 : ' + result.svo.allocation + 'p')
    $('.myPoint').html(myPoint.toLocaleString() + 'p');
    if(result.stockAmount != null){
        $('.myStockAmount').html(result.stockAmount)
    } else {
        $('.myStockAmount').html("0")
    }
   
	$('.myStockList').html("");
    for(let hvo of result.hvoList){
        let icon = document.createElement('i')
        icon.className = "bi bi-coin"
        let str = hvo.stockId + ' : ' + hvo.stockAmount + '주'
        let li = document.createElement('li')
        li.addEventListener('click', function(){
            location.href = "/influ/stockdetail?id=" + hvo.stockId
        })
        li.innerHTML = str;
        li.prepend(icon)
        li.style.cursor = "pointer";
        $('.myStockList').append(li)
    }
}

    // 거래 내역 조회 
let myExchange = function(){

    $.ajax({
        type : 'post',           // 타입 (get, post, put 등등)
        url : '/influ/myexchangeinfo',           // 요청할 서버url
        async : true,            // 비동기화 여부 (default : true)
        headers : {              // Http header
          "Content-Type" : "application/json",
        },
        dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
        data : JSON.stringify({
            "stockId" : stockId,
            "memberId" : memberId
        }),
        success : function(result) { // 결과 성공 콜백함수
            $('.tbody').empty();
            console.log(result)
            for(let info of result){
                let trTag = document.createElement('tr')
                for(let attr in info){
                    let tdTag = document.createElement('td')  
                    if(info[attr] != null){
                    tdTag.innerText = info[attr] ;
                    }
                    if(attr == "memberId"){
                        if(info.settleDate == null){
                        let buttonTag = document.createElement('button')  
                        buttonTag.className = "btn btn-primary btn-sm"
                        buttonTag.innerText = '거래취소'
                        buttonTag.addEventListener('click', function(){
                            let exchangeNo = $(this).parent().parent().find('td:eq(0)').html()
                            let exchangePrice = $(this).parent().parent().find('td:eq(1)').html()
                            let exchangeAmount = $(this).parent().parent().find('td:eq(2)').html()
                            let exchangeType = $(this).parent().parent().find('td:eq(3)').html()
                            cancelExchange(exchangeNo, exchangePrice, exchangeAmount, exchangeType);
                            deletewhat = $(this).parent().parent()
                        })
                        tdTag.append(buttonTag)
                        } else {
                            tdTag.innerText = '체결'
                        }
                    } 
                    trTag.append(tdTag);
                }
                $('.tbody').append(trTag)
            }
        
        },
        error : function(request, status, error) { // 결과 에러 콜백함수
            console.log(error)
        }
    })
}

//거래 취소
let cancelExchange = function(exchangeNo, exchangePrice, exchangeAmount, exchangeType){
    $.ajax({
        type : 'post',           // 타입 (get, post, put 등등)
        url : '/influ/cancelexchange',           // 요청할 서버url
        async : true,            // 비동기화 여부 (default : true)
        headers : {              // Http header
          "Content-Type" : "application/json",
        },
        dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)
        data : JSON.stringify({
            "exchangeNo" : exchangeNo,
            "exchangeAmount" : exchangeAmount,
            "exchangePrice" : exchangePrice,
            "exchangeType" : exchangeType,
            "stockId" : stockId
        }),
        success : function(result) { // 결과 성공 콜백함수
            if(result.result == "success"){
                myExchange()
            } else {
                swal("결과", "취소 실패", "warning");
            }
        
        },
        error : function(request, status, error) { // 결과 에러 콜백함수
            console.log(error)
        }
    })
}

    // 함수 실행 이벤트 등록
    $('.marketprice').on('change', function(){
            marketprice();
    })
    $('.submitBtn').on('click', insertExchange);
	
    //차트 변경 이벤트 등록
    $('.showtoday').on('click', function(){
        $('.chartVariable').val('T')
        selectChartData(stockId)
    })

    $('.showoneday').on('click', function(){
        $('.chartVariable').val('1')
        selectChartData(stockId)
    })

    $('.showthreeday').on('click', function(){
        $('.chartVariable').val('3')
        selectChartData(stockId)
    })
    
    myExchange();
