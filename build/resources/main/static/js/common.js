let common = {

    ajaxCall: (options, async=true) => {

        let settings = $.extend({}, options);
        let $ajax = $.ajax({
            type: settings.type,
            url: settings.url,
            data: settings.data,
            async: async,
            contentType: settings.contentType,
            dataType: settings.dataType,
            processData: settings.processData,
            beforeSend: () => {
                // TODO blockUI 등을 이용하여 이중 클릭 등을 막아준다.
            },
            success: (result) => {

                if (!async) return;

                if (settings.type === "GET") {

                    settings.callback(result);
                } else {

                    if (result.code === "OK") {

                        settings.callback(result);
                    } else {

                        alert(`[${result.code}] ${result.msg}`);

                        if (settings.callbackError) settings.callbackError(result);
                    }
                }
            },
            error: (xhr, option, error) => {

                if (xhr.status !== 0 && xhr.status !== 200) {

                    alert(xhr.status + '<-- error Code');
                }
            }
        });

        if (!async) {
            $ajax.done((result) => {
	            if (settings.done) settings.done(result);
            })
        }

    }

};

let Pagination = {
    prev: null,
    curr: null,
    next: null,

    init: function () {
        this.prev = null;
        this.curr = null;
        this.next = null;
    },

    setPrevEmptyArr: function () {
        this.prev = [];
    }
};

setDisabled = ($instance, disabled) => {
	$instance
		.prop('disabled', disabled)
		.addClass(disabled? 'disabled': '')
		.removeClass(disabled? '': 'disabled');
};

getStrRemovedNullLastKey = (tmpLastKey) => {
	let lastKey = {};
	for (let key in tmpLastKey) {
		lastKey[key] = {};
		let item = tmpLastKey[key];
		for (let subKey in item) {
			if (item[subKey] === null || !item[subKey]) continue;
			lastKey[key][subKey] = item[subKey];
		}
	}
	lastKey = JSON.stringify(lastKey);
	lastKey = lastKey.replace(/{/g, '%7B');
	lastKey = lastKey.replace(/}/g, '%7D');
	return lastKey;
};

showBootstrapNotify = ({title='', message='', type='info'}) => {
    $.notify({
        title,
        message
    }, {
        type,
        delay: 1000,
        placement: {
            from: "top",
            align: "center"
        },
        z_index: 9999
    });
};

copyToClipBoard = (value) => {
    let $temp = $("<input>");
    $("body").append($temp);
    $temp.val(value).select();
    document.execCommand("copy");
    $temp.remove();

    showBootstrapNotify({message: '<strong>Success</strong> 정상적으로 복사가 되었습니다.', type: 'success'});
};

copyToClipBoardById = (idToCopy) => {
    let $copy = $(`#${idToCopy}`);
    let value = $copy.text() || $copy.val();
    copyToClipBoard(value);
};

// go top
scrollFunction = () => {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        $('#goTop').show();
    } else {
        $('#goTop').hide();
    }
};

topFunction = () => {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
};
// /go top

randomColorString = (isHalf=false) => {
	let r = Math.floor(Math.random() * 255);
	let g = Math.floor(Math.random() * 255);
	let b = Math.floor(Math.random() * 255);
	return "rgb(" + r + "," + g + "," + b + ", "+ (isHalf? "0.5": "1")+")";
};

randomColor = () => {
  return {
	  r: Math.floor(Math.random() * 255),
	  g: Math.floor(Math.random() * 255),
	  b: Math.floor(Math.random() * 255)
  }
};

removeComma = (s) => {
  return s.replace(/\,/g, '');
};

setCsvQuote = (str) => {
	return `"${str}"`;
};

/**
 * td 안에 DIV 로 이루어진 테이블의 slice 생성
 */
getSliceDivTd = function (items) {
	let slice = '';

	Array.from(items).map((item, idx) => {
		// 테이블 헤더
		if (idx === 0 ) {
			Array.from(item.cells).map((cell) => {
				slice += cell.textContent + ',';
			});
			slice += '\r\n';
			return;
		}

		let divCells = [];
		Array.from(item.cells).map((cell, i) => {
			// 날짜
			if (i === 0) {
				slice += cell.textContent;

				if (!cell.colSpan || cell.colSpan === 1) return;

				for (let cs = 1; cs < cell.colSpan; cs++) {
					slice += ',';
				}
				return;
			}

			let inner = [];
			Array.from($(cell).find('div')).map((innerCell) =>  {
				inner.push(setCsvQuote(innerCell.textContent));
			});
			divCells.push(inner);
		});

		for (let col in divCells[0]) {
			slice +=',';
			for (let row in divCells) {
				slice += divCells[row][col] + ',';
			}
			slice += '\r\n';
		}
	});
	return slice;
};

/**
 * Browser OS 조회
 * @returns {string}
 */
getOSNams = () => {
	const appVersion = navigator.appVersion;

	if (appVersion.indexOf("Win") > 0) return OS_NAME.Windows;
	else if (appVersion.indexOf("Mac") > 0) return OS_NAME.Mac;
	else if (appVersion.indexOf("X11") > 0) return OS_NAME.UNIX;
	else if (appVersion.indexOf("Linux") > 0) return OS_NAME.Linux;

	return OS_NAME.unknown;
};

/**
 * url 타입 체킹
 * @param url
 * @returns {boolean}
 */
function isUrlValid(url) {
	return /^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(url);
}

/**
 * location parameter value by key
 * @param key
 * @returns {string}
 */
function getQueryStringValue (key) {
	return decodeURIComponent(window.location.search.replace(new RegExp("^(?:.*[&\\?]" + encodeURIComponent(key).replace(/[\.\+\*]/g, "\\$&") + "(?:\\=([^&]*))?)?.*$", "i"), "$1"));
}
isEmpty = (instance) => {
	if (!instance.value) {
		showBootstrapNotify({message: '<strong>Warning</strong> 필수값을 입력해주세요', type: 'warning'});
		instance.focus();
		return true;
	}
	return false;
};

$(document).ready(() => {
    $('.remove').on('click', (e) => {
        e.preventDefault();
    });

    $('a[href="#"]').on('click', (e) => {
        e.preventDefault();
    });
});