let VersionHistory = Object.assign({}, Pagination);

/**
 * 앱 버전 조회
 * @param os: android/ios
 * @param type: version/url
 */
getVersion = (os, type) => {
	common.ajaxCall({
		url: `/version/${os}/${type}`,
		type: 'GET',
		callback(result) {
			$(`#${os}${type === 'version'? 'Version': 'Url'}`).val(result.data);
		}
	})
};

/**
 * 앱 최신 버전 수정
 * @param os: android/ios
 * @param type: version/url
 */
editLastVersion = (os, type) => {
	const lastVersion = $(`#${os}Version`).val()
		, lastUrl = $(`#${os}Url`).val()
		, isTypeVersion = type === 'version';

	bootbox.dialog({
		title: '최신 버전 입력',
		message: `
			<p>앱 내 설정-버전 정보의 최신 버전에 반영됩니다.</p>
			<div class="row">
				<label class="col-xs-3 control-label text-right">선택 OS</label>
				<div>${os}</div>
			</div>
			<div class="row">
				<label class="col-xs-3 control-label text-right">과거 최신 ${isTypeVersion? '버전': '주소'}</label>
				<div>${isTypeVersion? lastVersion: lastUrl}</div>
			</div>
			<div class="row">
				<label class="col-xs-3 control-label text-right">최신 ${isTypeVersion? '버전': '주소'}</label>
				<div>
					<input type="text" class="form-control" placeholder="변경 할 버전 값을 입력해주세요." style="display: inline-block; width: 55%;" id="newItem"/>
				</div>
			</div>
		`,
		buttons: {
			cancel: {
				label: 'cancel',
				className: 'btn-default'
			},
			ok: {
				label: 'ok',
				className: 'btn-success',
				callback() {
					const $newItem = $('#newItem');
					const newItem = $newItem.val();

					if (!newItem) {
						showBootstrapNotify({message: '<strong>Warning</strong>변경할 값을 입력해주세요.', type: 'warning'});
						$newItem.focus();
						return;
					}

					common.ajaxCall({
						url: '/version/' + os,
						type: 'POST',
						data: JSON.stringify({ version: isTypeVersion? newItem: lastVersion, url: isTypeVersion? lastUrl: newItem}),
						contentType: 'application/json',
						callback() {
							showBootstrapNotify({message: '<strong>Success</strong>성공적으로 저장되었습니다.', type: 'success'});

							$(`#${os}${isTypeVersion? 'Version': 'Url'}`).val(newItem);

							getVersionHistory({
								osType: $('#selectOs').val()
							});
						}
					})
				}
			}
		}
	});
};

/**
 * 버전 히스토리 조회
 */
getVersionHistory = ({osType, isPrev=false, isNext=false}) => {
	const startKey = isPrev? VersionHistory.prev.pop(): isNext? VersionHistory.next: null;
	common.ajaxCall({
		url: `/version/log?limit=10${startKey? '&startKey='+startKey: ''}${'&osType='+osType}`,
		type: 'GET',
		callback(result) {
			const { data } = result;

			// 페이지네이션
			if (VersionHistory.prev === null) VersionHistory.setPrevEmptyArr();
			if (isNext) {
				if (VersionHistory.curr) VersionHistory.prev.push(VersionHistory.curr);
				VersionHistory.curr = VersionHistory.next;
			} else if (isPrev) {
				VersionHistory.curr = startKey;
			}
			if (data.lastEvaluatedKey) {
				VersionHistory.next = getStrRemovedNullLastKey(
					JSON.parse(JSON.stringify(data.lastEvaluatedKey)));
			} else VersionHistory.next = null;

			if (VersionHistory.next) setDisabled($('#nextHistory'), false);
			else setDisabled($('#nextHistory'), true);
			if (VersionHistory.curr && VersionHistory.curr.length > 0) setDisabled($('#prevHistory'), false);
			else setDisabled($('#prevHistory'), true);

			// 버전 히스토리 데이터 세팅
			let trs = '';
			for (let item of data.results) {
				const date = new Date(item.date);
				trs += `
					<tr>
						<td>${item.os}</td>
						<td>${item.version}</td>
						<td>${item.url || ''}</td>
						<td>${getDateMask(date, '-')}</td>
						<td>${getTimeMask(date)}</td>
						<td>${item.cashId}</td>
					</tr>
				`;
			}
			$('#resultVersionHistory').empty().append(trs);
		}
	})
};

document.addEventListener("DOMContentLoaded", () => {
	/**
	 * iOS 버전 수정
	 */
	$('#editIosVersion').on('click', () => { editLastVersion('ios', 'version'); });

	/**
	 * android 버전 수정
	 */
	$('#editAndroidVersion').on('click', () => { editLastVersion('android', 'version'); });

	/**
	 * ios 클릭 호출 주소 수정
	 */
	$('#editIosUrl').on('click', () => { editLastVersion('ios', 'url'); });

	/**
	 * android 클릭 호출 주소 수정
	 */
	$('#editAndroidUrl').on('click', () => { editLastVersion('android', 'url'); });

	/**
	 * popup - 최신 버전 필드 핸들러
	 */
	$(document).on('change', '#newItem',  (e) => {
		let { value } = e.target;
		value = value.replace(/!/gi, '');
		value = value.replace(/@/gi, '');
		value = value.replace(/#/gi, '');
		e.target.value = value;
	});

	/**
	 * osType 변경
	 */
	$('#selectOs').on('change', (e) => {
		getVersionHistory({osType: e.target.value});
	});

	/**
	 * 버전 히스토리 - 이전 보기
	 */
	$('#prevHistory').on('click', (e) => {
		const { className } = e.currentTarget;
		if (className.includes('disabled')) return;

		getVersionHistory({
			osType: $('#selectOs').val(), isPrev: true
		});
	});

	/**
	 * 버전 히스토리 - 다음 보기
	 */
	$('#nextHistory').on('click', (e) => {
		const { className } = e.currentTarget;
		if (className.includes('disabled')) return;

		getVersionHistory({
			osType: $('#selectOs').val(), isNext: true
		});
	});
});

document.addEventListener("beforeunload", () => {
	VersionHistory = undefined;
});