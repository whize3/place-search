const search = {
        searchResult: {},
        init: function () {
            let _this = this;
            //로그인체크
            if (!localStorage.token) {
                alert("로그인 해주세요.")
                location.href = "/view/main";
            }
            ;
            $('#query').on('keyup', function (e) {
                if ($('#query').val().length > 0){
                    delay(function(){_this.query()}, 100);
                }
            });
            _this.getRank();

            const delay = (function () {
                let itcTimer = 0;
                return function (callback, ms) {
                    clearTimeout(itcTimer);
                    itcTimer = setTimeout(callback, ms);
                };
            })();
        },
        query: function (currentPage) {    //검색
            let _this = this;
            let data = {
                query: $('#query').val(),
                page: currentPage
            };
            $.ajax({
                type: 'GET',
                url: '/v1/search/keyword',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: data,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-AUTH-TOKEN", localStorage.token)
                }
            }).done(function (result) {
                if (result.code == -1003) {
                    alert("로그인 시간이 만료되었습니다.");
                    location.href = "/view/main";
                } else {
                    _this.searchResult = result.data.documents;
                    _this.setPage(result.data.meta.total_page, currentPage);
                    _this.showAddress(_this.searchResult);
                }
            }).fail(function (error) {
                if (typeof (error.responseJSON) == "undefined") {
                    console.log(error);
                } else {
                    console.log(error.responseJSON.msg);
                }
            });
        },
        detail: function (o) {   //상세보기 동작
            let _this = this;
            const id = o.dataset.id;
            const index = _this.searchResult.findIndex(x => x.id === id);
            _this.setModal(_this.searchResult[index]);
            $(".modal").modal('show');
        },
        showAddress: function (documents) { //검색결과세팅
            let addressHtml = '';
            for (const elements of documents) {
                const id = elements.id;
                addressHtml += "<li class='list-group-item list-group-item-action' onclick=search.detail(this) data-id=" + elements.id + ">" +
                    "<div class='d-flex w-100 justify-content-between'>" +
                    "<h5 class='mb-1'>" + elements.place_name + "</h5>" +
                    "<small class='text-muted'>" + elements.category_name + "</small>" +
                    "</div>" +
                    "<p class='mb-1'>" + elements.road_address_name + "</p>" +
                    "</li>";
            }
            $("#search-result").html(addressHtml);
        },
        setPage: function (total, currentPage) {  //페이징 세팅
            let pageHtml = '';
            currentPage = currentPage == undefined ? 1 : currentPage;
            for (let i = 0; i < total; i++) {
                let number = i + 1;
                pageHtml += "<li class='page-item' onclick=search.query(" + number + ") id=" + number + "><a class='page-link' href='#'>" + number + "</a></li>";
            }
            $(".pagination").html(pageHtml);
            $("#" + currentPage).addClass("active");
        },
        setModal: function (elements) {   //상세보기 내용 세팅
            $("#place-name").html(elements.place_name);
            $("#address-name").html(elements.address_name);
            $("#road-address-name").html(elements.road_address_name);
            $("#phone").html(elements.phone);
            const url = 'https://map.kakao.com/link/map/' + elements.id;
            $("#place-url").html('다음지도 바로가기').attr('href', url);
        },
        getRank: function () {
            $.ajax({
                type: 'GET',
                url: '/v1/search/keyword/rank',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-AUTH-TOKEN", localStorage.token)
                }
            }).done(function (result) {
                if (result.code == -1003) {
                    alert("로그인 시간이 만료되었습니다.");
                    location.href = "/view/main";
                } else {
                    search.setRank(result.list);
                    setTimeout(search.getRank, 5000);
                }
            }).fail(function (error) {
                if (typeof (error.responseJSON) == "undefined") {
                    console.log(error);
                } else {
                    console.log(error.responseJSON.msg);
                }
            })
        },
        setRank: function (list) {
            let rankHtml = '';
            for (const element of list) {
                rankHtml += "<li class='list-group-item list-group-item-action' >" +
                    "<div class='d-flex w-100 justify-content-between'>" +
                    "<h5 class='mb-1'>" + element.query + "</h5>" +
                    "<small class='text-muted'>" + element.hit + "</small>" +
                    "</div>" +
                    "<p class='mb-1'>아무거나</p>" +
                    "</li>";
            }
            $("#rank").html(rankHtml);
        }
    }
;

search.init();